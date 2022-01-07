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

    @Step("After test: get courier id from response")
    public void getCourierId(ValidatableResponse response) {

        // Запись id курьера для последующего удаления
        courierId = response.extract().path("id");
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
        ValidatableResponse response = courierMethods.login(new CourierCredentials(courier.login, courier.password));

        // Проверка ответа
        response.assertThat().statusCode(200).and().body("id", allOf(notNullValue(), is(not(0))));

        // Запись id курьера для последующего удаления
        getCourierId(response);

    }

}
