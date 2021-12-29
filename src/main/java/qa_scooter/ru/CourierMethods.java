package qa_scooter.ru;

import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;


import static io.restassured.RestAssured.given;


public class CourierMethods extends RestAssured {

    public String COURIER_PATH = "/api/v1/courier/";

    Response response;

    public boolean create(Courier courier) {
        return given()
                .spec(getBaseSpec())
                .body(courier)
                .when()
                .post(COURIER_PATH)
                .then()
                .log().all()
                .assertThat()
                .statusCode(201)
                .extract()
                .path("ok");
    }

    public ValidatableResponse createValidatableResponse(Courier courier) {
        return given()
                .spec(getBaseSpec())
                .body(courier)
                .when()
                .post(COURIER_PATH)
                .then()
                .log().all();
    }

    public int login (CourierCredentials courierCredentials) {
        return given()
                .spec(getBaseSpec())
                .body(courierCredentials)
                .when()
                .post(COURIER_PATH + "login")
                .then()
                .log().all()
                .assertThat()
                .statusCode(200)
                .extract()
                .path("id");
    }

    public ValidatableResponse loginValidatableResponse (CourierCredentials courierCredentials) {
        return given()
                .spec(getBaseSpec())
                .body(courierCredentials)
                .when()
                .post(COURIER_PATH + "login")
                .then()
                .log().all();
    }

    public boolean delete (int courierId) {
        return given()
                .spec(getBaseSpec())
                .when()
                .delete(COURIER_PATH + courierId)
                .then()
                .log().all()
                .assertThat()
                .statusCode(200)
                .extract()
                .path("ok");
    }

    public ValidatableResponse deleteValidatableResponse (int courierId) {
        return given()
                .spec(getBaseSpec())
                .when()
                .delete(COURIER_PATH + courierId)
                .then()
                .log().all();
    }

    public ValidatableResponse deleteValidatableResponse (String courierId) {
        return given()
                .spec(getBaseSpec())
                .when()
                .delete(COURIER_PATH + courierId)
                .then()
                .log().all();
    }
}
