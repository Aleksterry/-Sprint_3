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
    private int orderTrack;
    private final Order order;
    private final int statusCode;

    public OrderCreateParameterizedPositiveTest(Order order, int statusCode) {
        this.order = order;
        this.statusCode = statusCode;
    }


    @Before
    public void setup() {
        orderMethods = new OrderMethods();
    }

    @After
    @Step("After test: send PUT request to /api/v1/orders/cancel - to cancel order")
    public void tearDown() {

        if (orderTrack != 0) {
            // метод отмены не работает
            ValidatableResponse response = orderMethods.cancel(new OrderCredentials(orderTrack));
            if (response.extract().statusCode() == 200) {
                System.out.println("\norder is cancelled\n");
            } else {
                System.out.println("\norder was not cancelled\n");
            }
        }
    }


    @Step("After test: get order track from response")
    public void getOrderTrack(ValidatableResponse response) {

        // Запись track номера заказа для последующей отмены
        orderTrack = response.extract().path("track");
    }


    @Parameterized.Parameters
    public static Object[][] getParams() {
        return new Object[][] {
                {Order.getParameters(List.of("BLACK")),201},
                {Order.getParameters(List.of("GREY")),201},
                {Order.getParameters(List.of("BLACK","GREY")),201},
                {Order.getParameters(List.of("")),201}
        };
    }

        @Test
        @DisplayName("Check creation of order: order was successfully created")
        @Description("It is checked that it is possible to create an order with or without color data")
        @Step("Create order")
        public void testGetResponse() {

            // Создание заказа
            ValidatableResponse response = orderMethods.create(order);

            // Проверка ответа
            response.assertThat().statusCode(statusCode).and().body("track",allOf(notNullValue(), is(not(0))));

            // Запись id заказа для последующей отмены
            getOrderTrack(response);

        }

    }

