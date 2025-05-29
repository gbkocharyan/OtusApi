package helpers;

import com.github.javafaker.Faker;
import dto.User;
import services.UserService;

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

}
