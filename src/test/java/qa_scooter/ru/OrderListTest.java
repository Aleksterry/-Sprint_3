package qa_scooter.ru;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Before;
import org.junit.Test;


import static org.hamcrest.Matchers.*;


public class OrderListTest {

    public OrderMethods orderMethods;

    @Before
    public void setup() {
        orderMethods = new OrderMethods();
    }


    @Test
    @DisplayName("Check fields in orders list")
    @Description("It is checked that the response of the list of orders has all the necessary fields")
    public void testOrderListPositive() {

        // Запрос списка заказов
        ValidatableResponse response = orderMethods.orderList();


        // Проверка ответа
        response.assertThat().statusCode(200).and()
                .body("orders", notNullValue(),
                        "orders.id",allOf(notNullValue(), is(not(0))),
                        "orders.courierId", is(not(0)),
                        "orders.firstName", notNullValue(),
                        "orders.lastName", notNullValue(),
                        "orders.address", notNullValue(),
                        "orders.metroStation", notNullValue(),
                        "orders.phone", notNullValue(),
                        "orders.rentTime", notNullValue(),
                        "deliveryDate", oneOf(notNullValue(), null),
                        "orders.track", allOf(notNullValue(), is(not(0))),
                        "orders.color",notNullValue(),
                        "orders.comment", notNullValue(),
                        "orders.createdAt", notNullValue(),
                        "orders.updatedAt", notNullValue(),
                        "orders.status",notNullValue(),
                        "pageInfo",notNullValue(),
                        "pageInfo.page",notNullValue(),
                        "pageInfo.total",notNullValue(),
                        "pageInfo.limit",notNullValue(),
                        "availableStations",notNullValue(),
                        "availableStations.name",notNullValue(),
                        "availableStations.number",notNullValue(),
                        "availableStations.color",notNullValue());

    }


}
