package user;

import dto.User;
import helpers.UserHelper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.time.Duration;

public class GetUserTest {

  @Test
  @DisplayName("Get user and validate response json")
  void getUser() {

    UserHelper userHelper = new UserHelper();
    String userName = userHelper.createUser().getUsername();

    User user = userHelper.waitForUserCreation(userName, Duration.ofSeconds(10));

    Assertions.assertEquals(userName, user.getUsername(), "Incorrect username");
  }

}
