package services;

import dto.User;
import io.restassured.response.ValidatableResponse;

public class UserService extends BaseService {

  public ValidatableResponse createUser(User user) {
    return post(user, getUrl());
  }

  public ValidatableResponse getUser(String userName) {
    return get(userName, getUrl());
  }

  private String getUrl() {
    return "/user";
  }
}
