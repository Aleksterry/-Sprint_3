package qa_scooter.ru;

import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;


public class OrderMethods extends RestAssured {

    public String ORDER_PATH = "/api/v1/orders/";

    Response response;

    public boolean create(Order order) {
        return given()
                .spec(getBaseSpec())
                .body(order)
                .when()
                .post(ORDER_PATH)
                .then()
                .log().all()
                .assertThat()
                .statusCode(201)
                .extract()
                .path("track");
    }

    public ValidatableResponse createValidatableResponse(Order order) {
        return given()
                .spec(getBaseSpec())
                .body(order)
                .when()
                .post(ORDER_PATH)
                .then()
                .log().all();
    }

    public ValidatableResponse orderList () {
        return given()
                .spec(getBaseSpec())
                .when()
                .get(ORDER_PATH)
                .then()
                .log().all()
                .assertThat()
                .statusCode(200);
    }

    // метод не работает
    public boolean cancel (OrderCredentials orderCredentials) {
        return given()
                .spec(getBaseSpec())
                .body(orderCredentials)
                .when()
                .put(ORDER_PATH + "cancel")
                .then()
                .log().all()
                .assertThat()
                .statusCode(200)
                .extract()
                .path("ok");
    }



}
