package com.prj.sns_today.domain.users.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class UserRequest {

  @Getter
  @NoArgsConstructor
  @AllArgsConstructor
  public static class UserSaveRequest {

    private String username;
    private String password;
  }
}
