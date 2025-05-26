package user;

import static org.hamcrest.Matchers.equalTo;

import org.apache.http.HttpStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import services.UserService;

public class GetUserInvalidTest {

  @Test
  @DisplayName("Verify that we receive an appropriate message when making a request with an invalid userName")
  void getUserWithInvalidUserName() {

    new UserService().getUser("invalidUserName")
        .statusCode(HttpStatus.SC_NOT_FOUND)
        .body("code", equalTo(1),
            "type", equalTo("error"),
            "message", equalTo("User not found"));

  }
}
