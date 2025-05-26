package helpers;

import dto.User;
import services.UserService;
import java.util.Random;

public class UserHelper {

  public User createUser() {

    Random random = new Random();
    int randomId = random.nextInt(100) + 1;
    String randomUserName = generateRandomString();


    User user = User
        .builder()
        .id(randomId)
        .username(randomUserName)
        .build();

    new UserService().createUser(user);

    return user;
  }

  private String generateRandomString() {
    String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    Random random = new Random();
    StringBuilder stringBuilder = new StringBuilder();

    for (int i = 0; i < 6; i++) {
      int index = random.nextInt(characters.length());
      stringBuilder.append(characters.charAt(index));
    }

    return stringBuilder.toString();
  }

}
