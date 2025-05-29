package user;

import dto.CreateUserResponse;
import dto.User;
import helpers.UserHelper;
import io.restassured.module.jsv.JsonSchemaValidator;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import services.UserService;
import java.time.Duration;

public class CreateNewUserTest {

  @Test
  @DisplayName("Create user and validate response json")
  void createUser() {

    String userName = "admin";
    int id = 15;
    UserService userService = new UserService();
    UserHelper userHelper = new UserHelper();

    User user = User
        .builder()
        .id(id)
        .username(userName)
        .firstName("Mike")
        .lastName("Anderson")
        .email("mike.anderson@mail.ru")
        .password("123456")
        .phone("+79999999999")
        .userStatus(1)
        .build();

    CreateUserResponse createUserResponse = userService.createUser(user)
        .statusCode(HttpStatus.SC_OK)
        .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schemas/createUserResponse.json"))
        .extract().as(CreateUserResponse.class);

    User createdUser = userHelper.waitForUserCreation(userName, Duration.ofSeconds(10));

    Assertions.assertAll("Check new user response body",
        () -> Assertions.assertEquals(200, createUserResponse.getCode(), "Incorrect code"),
        () -> Assertions.assertEquals("unknown", createUserResponse.getType(), "Incorrect type"),
        () -> Assertions.assertEquals(String.valueOf(id), createUserResponse.getMessage(), "Incorrect message"),
        () -> Assertions.assertEquals(createdUser, user, "Incorrect user")
    );

  }

  @Test
  @DisplayName("Verify user creation without email and phone")
  void createUserWithoutSomeParameters() {
    int id = 15;

    User user = User
        .builder()
        .id(id)
        .username("admin")
        .firstName("Mike")
        .lastName("Anderson")
        .password("123456")
        .userStatus(1)
        .build();

    new UserService().createUser(user)
        .statusCode(HttpStatus.SC_OK)
        .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schemas/createUserResponse.json"));
  }
}
