package qa_scooter.ru;

import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.*;


public class CourierLoginPositiveTest {

    public CourierMethods courierMethods;
    public int courierId;

    @Before
    public void setup() {
        courierMethods = new CourierMethods();
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
    public void createCourier(Courier courier) {

        // Создание курьера
        courierMethods.create(courier);
    }

    @Step("Get courier id from response")
    public void getCourierId(ValidatableResponse response) {

        // Запись id курьера для последующего удаления
        courierId = response.extract().path("id");;
    }


    @Test
    @DisplayName("Check login of courier: courier has successfully logged in")
    @Description("Login of courier")
    @Step("Login")
    public void testCourierLoginPositive() {

        Courier courier = Courier.getRandom();

        // Создание курьера
        createCourier(courier);

        // Авторизация курьера
        ValidatableResponse response = courierMethods.loginValidatableResponse(new CourierCredentials(courier.login, courier.password));

        // Проверка ответа
        response.assertThat().statusCode(200).and().body("id", allOf(notNullValue(), is(not(0))));

        // Запись id курьера для последующего удаления
        getCourierId(response);

    }

}
