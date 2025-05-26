package services;

import static io.restassured.RestAssured.given;

import dto.User;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;

public abstract class BaseService {

  private final String baseUrl = System.getProperty("baseUrl", "https://petstore.swagger.io/v2");

  private final RequestSpecification spec = RestAssured
      .given()
      .baseUri(baseUrl)
      .contentType(ContentType.JSON);

  private RequestSpecification setupRequest() {
    return given(spec);
  }

  protected ValidatableResponse post(User user, String endpoint) {
    return setupRequest()
        .body(user)
        .log().all()
        .when()
        .post(endpoint)
        .then()
        .log().all();
  }

  protected ValidatableResponse get(String userName, String endpoint) {
    return setupRequest()
        .log().all()
        .when()
        .get(endpoint + "/" + userName)
        .then()
        .log().all();
  }
}
