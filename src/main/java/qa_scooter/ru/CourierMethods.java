package qa_scooter.ru;

import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;


public class CourierMethods extends RestAssured {

    public String COURIER_PATH = "/api/v1/courier/";


    public ValidatableResponse create(Courier courier) {
        return given()
                .spec(getBaseSpec())
                .body(courier)
                .when()
                .post(COURIER_PATH)
                .then()
                .log().all();
    }


    public ValidatableResponse login (CourierCredentials courierCredentials) {
        return given()
                .spec(getBaseSpec())
                .body(courierCredentials)
                .when()
                .post(COURIER_PATH + "login")
                .then()
                .log().all();
    }


    public ValidatableResponse delete (int courierId) {
        return given()
                .spec(getBaseSpec())
                .when()
                .delete(COURIER_PATH + courierId)
                .then()
                .log().all();
    }

    public ValidatableResponse delete (String courierId) {
        return given()
                .spec(getBaseSpec())
                .when()
                .delete(COURIER_PATH + courierId)
                .then()
                .log().all();
    }
}
