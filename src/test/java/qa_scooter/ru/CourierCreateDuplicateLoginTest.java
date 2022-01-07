package qa_scooter.ru;


import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;


public class CourierCreateDuplicateLoginTest {

    public CourierMethods courierMethods;
    public int courierId;


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


    @Step("Before test: send POST request to /api/v1/courier - to create courier")
    public void createCourier(Courier courier) {

        // Создание курьера
        courierMethods.create(courier).assertThat().statusCode(201);
    }


    @Step("After test: send POST request to /api/v1/courier/login - to get courier id")
    public void getCourierId(Courier courier) {

        // Запись id курьера для последующего удаления
        courierId = (courierMethods.login(new CourierCredentials(courier.login, courier.password))).extract().path("id");
    }


    @Test
    @DisplayName("Check login of courier: this login is already in use")
    @Description("It is checked that it is impossible to create a courier with the same login")
    @Step("Create courier with the same credentials")
    public void testCourierDuplicateLoginNegative() {

        Courier courier = Courier.getRandom();

        // Создание курьера
        createCourier(courier);

        // Создание курьера с теми же данными
        ValidatableResponse response = courierMethods.create(courier);

        // Проверка ответа
        response.assertThat().statusCode(409)
                .and()
                .body("code", equalTo(409), "message", equalTo("Этот логин уже используется. Попробуйте другой."));

        // Запись id курьера для последующего удаления
        getCourierId(courier);
    }

}