package qa_scooter.ru;


import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.*;


public class CourierDeletePositiveTest {

    public CourierMethods courierMethods;
    public int courierId;

    @Before
    public void setup() {
        courierMethods = new CourierMethods();
    }


    @Step("Before test: send POST request to /api/v1/courier - to create courier")
    public void createCourier(Courier courier) {

        // Создание курьера
        courierMethods.create(courier).assertThat().statusCode(201);
    }

    @Step("Before test: send POST request to /api/v1/courier/login - to get courier id")
    public void getCourierId(Courier courier) {

        // Авторизация курьера с записью id курьера
        courierId = (courierMethods.login(new CourierCredentials(courier.login, courier.password))).assertThat().statusCode(200).extract().path("id");
    }


    @Test
    @DisplayName("Check deleting of courier: courier was successfully deleted")
    @Description("Deleting of courier")
    @Step("Delete courier")
    public void testCourierIsDeletedPositive() {

        Courier courier = Courier.getRandom();

        // Создание курьера
        createCourier(courier);

        // Авторизация курьера с записью id курьера
        getCourierId(courier);

        // Удаление курьера
        ValidatableResponse response = courierMethods.delete(courierId);

        // Проверка ответа
        response.assertThat().statusCode(200).and().body("ok", equalTo(true));

    }

}
