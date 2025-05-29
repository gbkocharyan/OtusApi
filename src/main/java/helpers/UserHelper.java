package helpers;

import com.github.javafaker.Faker;
import dto.User;
import org.apache.http.HttpStatus;
import services.UserService;
import java.time.Duration;
import java.util.concurrent.TimeUnit;

public class UserHelper {

  private final Faker faker = new Faker();

  public User createUser() {

    int randomId = faker.number().numberBetween(1, 101);
    String randomUserName = faker.name().username();

    User user = User
        .builder()
        .id(randomId)
        .username(randomUserName)
        .build();

    new UserService().createUser(user);

    return user;
  }

  public User waitForUserCreation(String userName, Duration timeout) {
    UserService userService = new UserService();
    long startTime = System.currentTimeMillis();
    long timeoutMillis = timeout.toMillis();

    while (System.currentTimeMillis() - startTime < timeoutMillis) {
      try {
        User user = userService.getUser(userName)
            .statusCode(HttpStatus.SC_OK)
            .extract().as(User.class);

        if (user != null && userName.equals(user.getUsername())) {
          return user;
        }
      } catch (Exception e) {
        //ignore error
      }

      try {
        TimeUnit.MILLISECONDS.sleep(1000);
      } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
        throw new RuntimeException("Thread interrupted while waiting for user creation", e);
      }
    }
    throw new RuntimeException("User creation timed out after " + timeout.getSeconds() + " seconds");
  }

}
