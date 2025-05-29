package user;

import dto.User;
import helpers.UserHelper;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import services.UserService;

public class GetUserTest {

  @Test
  @DisplayName("Get user and validate response json")
  void getUser() {

    UserHelper userHelper = new UserHelper();
    String userName = userHelper.createUser().getUsername();

    User user = new UserService().getUser(userName)
          .statusCode(HttpStatus.SC_OK)
          .extract().as(User.class);

    Assertions.assertEquals(userName, user.getUsername(), "Incorrect username");
  }
}
