package qa_scooter.ru;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.hamcrest.CoreMatchers.equalTo;

import io.restassured.response.ValidatableResponse;


@RunWith(Parameterized.class)
public class CourierCreateParameterizedNegativeTest {

    public CourierMethods courierMethods;
    private final Courier courier;
    private final int statusCode;
    private final String message;

    public CourierCreateParameterizedNegativeTest(Courier courier, int statusCode, String message) {
        this.courier = courier;
        this.statusCode = statusCode;
        this.message = message;
    }

    @Before
    public void setup() {
        courierMethods = new CourierMethods();
    }

    @Parameterized.Parameters
    public static Object[][] getParams() {
        return new Object[][] {
                {Courier.getRandomWithoutLogin(null),400,"Недостаточно данных для создания учетной записи"},
                {Courier.getRandomWithoutLogin(""),400,"Недостаточно данных для создания учетной записи"},
                {Courier.getRandomWithoutPass(null),400,"Недостаточно данных для создания учетной записи"},
                {Courier.getRandomWithoutPass(""),400,"Недостаточно данных для создания учетной записи"}
        };
    }

        @Test
        @DisplayName("Check creation of courier: not enough data to create")
        @Description("It is checked that it is impossible to create a courier without login or password")
        public void testGetResponse() {

            // Создание курьера
            ValidatableResponse response = courierMethods.create(courier);

            // Проверка ответа
            response.assertThat().statusCode(statusCode).and().body("code",equalTo(statusCode),"message",equalTo(message));

        }

    }

