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
public class CourierCreateParameterizedPositiveTest {

    public CourierMethods courierMethods;
    public int courierId;
    private final Courier courier;
    private final int statusCode;
    private final boolean message;

    public CourierCreateParameterizedPositiveTest(Courier courier, int statusCode, boolean message) {
        this.courier = courier;
        this.statusCode = statusCode;
        this.message = message;
    }

    @Before
    public void setup() {
        courierMethods = new CourierMethods();
    }

    @After
    @Step("After test: send DELETE request to /api/v1/courier/courierId - to delete courier")
    public void tearDown() {
        if (courierId != 0) {
            ValidatableResponse response = courierMethods.delete(courierId);
            if (response.extract().statusCode() == 200) {
                System.out.println("\ncourier is deleted\n");
            } else {
                System.out.println("\ncourier was not deleted\n");
            }
        }
    }

    @Step("After test: send POST request to /api/v1/courier/login - to get courier id")
    public void getCourierId(Courier courier) {

        // Запись id курьера для последующего удаления
        courierId = (courierMethods.login(new CourierCredentials(courier.login, courier.password))).extract().path("id");
    }


    @Parameterized.Parameters
    public static Object[][] getParams() {
        return new Object[][] {
                {Courier.getRandom(),201,true},
                {Courier.getRandomWithoutName(null),201,true},
                {Courier.getRandomWithoutName(""),201,true},
        };
    }

        @Test
        @DisplayName("Check creation of courier: courier was successfully created")
        @Description("It is checked that it is possible to create a courier with all credentials or without first name")
        @Step("Create courier")
        public void testGetResponse() {

            // Создание курьера
            ValidatableResponse response = courierMethods.create(courier);

            // Проверка ответа
            response.assertThat().statusCode(statusCode).and().body("ok",equalTo(message));

            // Запись id курьера для последующего удаления
            getCourierId(courier);
        }

    }

