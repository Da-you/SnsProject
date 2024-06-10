package com.prj.sns_today.domain.users.api;

import com.prj.sns_today.domain.users.application.UserService;
import com.prj.sns_today.domain.users.dto.request.UserRequest.UserSaveRequest;
import com.prj.sns_today.domain.users.dto.response.UserResponse;
import com.prj.sns_today.global.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserApiController {

  private final UserService userService;


  @PostMapping("/join")
  public void saveUser(@RequestBody UserSaveRequest request) {
    userService.saveUser(request);
  }

  @PostMapping("/login")
  public ApiResponse<UserResponse> login(@RequestBody UserSaveRequest request){
    return ApiResponse.success(userService.login(request));
  }


}
