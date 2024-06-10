package com.prj.sns_today.domain.users.application;

import com.prj.sns_today.domain.users.domain.User;
import com.prj.sns_today.domain.users.dto.request.UserRequest.UserSaveRequest;
import com.prj.sns_today.domain.users.dto.response.UserResponse;
import com.prj.sns_today.domain.users.repository.UserRepository;
import com.prj.sns_today.global.exception.ApplicationException;
import com.prj.sns_today.global.exception.ErrorCode;
import com.prj.sns_today.global.utils.JwtTokenUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

  private final UserRepository userRepo;
  private final BCryptPasswordEncoder encoder;

  @Value("${jwt.secret-key}")
  private String secretKey;

  @Value("${jwt.token.expired-time-ms}")
  private Long expireTimedMs;

  @Transactional
  public void saveUser(UserSaveRequest request) {
    userRepo.findByUsername(request.getUsername())
        .orElseThrow(() -> new ApplicationException(
            ErrorCode.DUPLICATED_USER_NAME));
    userRepo.save(User.of(request.getUsername(), encoder.encode(request.getPassword())));
  }

  public UserResponse login(UserSaveRequest request) {
    User user = userRepo.findByUsername(request.getUsername())
        .orElseThrow(() -> new ApplicationException(ErrorCode.USER_NOT_FOUND));

    if (!encoder.matches(request.getPassword(), user.getPassword())) {
      throw new ApplicationException(ErrorCode.INVALID_PASSWORD);
    }
    // 토큰 생성
    String token = JwtTokenUtils.generateToken(user.getUsername(), secretKey, expireTimedMs);
    return new UserResponse(token);
  }
}