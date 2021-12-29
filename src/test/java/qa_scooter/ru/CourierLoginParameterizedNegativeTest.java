package qa_scooter.ru;

import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.hamcrest.CoreMatchers.equalTo;

@RunWith(Parameterized.class)
public class CourierLoginParameterizedNegativeTest {

    private CourierMethods courierMethods;
    private int courierId;
    private final  CourierCredentials courierCredentials;
    private final int statusCode;
    private final String message;

    public CourierLoginParameterizedNegativeTest(CourierCredentials courierCredentials, int statusCode, String message) {
        this.courierCredentials = courierCredentials;
        this.statusCode = statusCode;
        this.message = message;
    }


    @Before
    public void setup() {
        courierMethods = new CourierMethods();

        // Создание курьера с заданными логин и паролем для последующей авторизации
        createCourier();

        // Запись id курьера для последующего удаления
        getCourierId();
    }

    @After
    @Step("Send DELETE request to /api/v1/courier/courierId - to delete courier after test")
    public void tearDown() {
        if (courierId != 0) {
            courierMethods.delete(courierId);
            System.out.println("courier is deleted");
        }
    }


    @Step("Send POST request to /api/v1/courier - to create courier")
    public void createCourier() {

        // Создание курьера с заданными логин и паролем для последующей авторизации
        courierMethods.create(Courier.getRandomWithoutLoginAndPass("admin", "1234567890"));
    }

    @Step("Send POST request to /api/v1/courier/login - to get courier id")
    public void getCourierId() {

        // Запись id курьера для последующего удаления
        courierId = courierMethods.login(CourierCredentials.getCourierCredentials("admin", "1234567890"));
    }


    @Parameterized.Parameters
    public static Object[][] getParams() {
        return new Object[][] {
                {CourierCredentials.getCourierCredentials("admin", ""),400,"Недостаточно данных для входа"},
                {CourierCredentials.getCourierCredentials("", "1234567890"),400,"Недостаточно данных для входа"},
                {CourierCredentials.getCourierCredentials(null, "1234567890"),400,"Недостаточно данных для входа"},
                //{CourierCredentials.getCourierCredentials("admin", null),400,"Недостаточно данных для входа"}, - ошибка 504
                {CourierCredentials.getCourierCredentials("admin", "123456"),404,"Учетная запись не найдена"},
        };
    }

        @Test
        @DisplayName("Check login of courier: courier has not logged in due to wrong credentials")
        @Description("It is checked that it is impossible to courier to login without login or password or with wrong password")
        @Step("Login with wrong login or password")
        public void testGetResponse() {

            // Авторизация курьера
            ValidatableResponse response = courierMethods.loginValidatableResponse(courierCredentials);

            // Проверка ответа
            response.assertThat().statusCode(statusCode).and().body("code",equalTo(statusCode),"message",equalTo(message));

        }

    }

