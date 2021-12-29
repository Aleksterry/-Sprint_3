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

import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.not;

@RunWith(Parameterized.class)
public class OrderCreateParameterizedPositiveTest {

    public OrderMethods orderMethods;
    public OrderCredentails orderCredentails; // for order cancelling
    private int orderTrack;
    private final Order order;
    private final int statusCode;
    private final boolean message;

    public OrderCreateParameterizedPositiveTest(Order order, int statusCode, boolean message) {
        this.order = order;
        this.statusCode = statusCode;
        this.message = message;
    }


    @Before
    public void setup() {
        orderMethods = new OrderMethods();
    }

    @After
    @Step("Send PUT request to /api/v1/orders/cancel - to cancel order - do not working now")
    public void tearDown() {

        if (orderTrack != 0) {
            // метод отмены не работает
            //orderMethods.cancel(new OrderCredentails(orderTrack));
            System.out.println("order cancelling is not possible now");
        }
    }


    @Step("Get order track from response")
    public void getOrderTrack(ValidatableResponse response) {

        // Запись track номера заказа для последующей отмены
        orderTrack = response.extract().path("track");
    }


    @Parameterized.Parameters
    public static Object[][] getParams() {
        return new Object[][] {
                {Order.getParameters(List.of("BLACK")),201,true},
                {Order.getParameters(List.of("GREY")),201,true},
                {Order.getParameters(List.of("BLACK","GREY")),201,true},
                {Order.getParameters(List.of("")),201,true}
        };
    }

        @Test
        @DisplayName("Check creation of order: order was successfully created")
        @Description("It is checked that it is possible to create an order with or without color data")
        @Step("Create order")
        public void testGetResponse() {

            // Создание заказа
            ValidatableResponse response = orderMethods.createValidatableResponse(order);

            // Проверка ответа
            response.assertThat().statusCode(statusCode).and().body("track",allOf(notNullValue(), is(not(0))));

            // Запись id заказа для последующей отмены
            getOrderTrack(response);

        }

    }

