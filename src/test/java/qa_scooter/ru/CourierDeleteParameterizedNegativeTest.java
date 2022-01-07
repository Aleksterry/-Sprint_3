package qa_scooter.ru;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.hamcrest.CoreMatchers.equalTo;

@RunWith(Parameterized.class)
public class CourierDeleteParameterizedNegativeTest {

    private CourierMethods courierMethods;
    private String courierId;
    private final int statusCode;
    private final String message;

    public CourierDeleteParameterizedNegativeTest(String courierId, int statusCode, String message) {
        this.courierId = courierId;
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
                {"",404,"Not Found."},
                {null,500,"invalid input syntax for type integer: \"null\""},
                {"0",404,"Курьера с таким id нет."}
        };
    }


    @Test
    @DisplayName("Check deleting of courier: courier was not deleted due to wrong id")
    @Description("It is checked that it is impossible to delete courier without id or with wrong id")
    public void testGetResponse() {

        // Удаление курьера
        ValidatableResponse response = courierMethods.delete(courierId);

        // Проверка ответа
        response.assertThat().statusCode(statusCode).and().body("code",equalTo(statusCode),"message",equalTo(message));

    }


}

