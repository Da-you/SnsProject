package com.prj.sns_today.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
  DUPLICATED_USER_NAME(HttpStatus.CONFLICT, "40301", "User name is duplicated "),
  INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "50101", "Internal server error"),
  INVALID_PASSWORD(HttpStatus.UNAUTHORIZED, "40101", "Password is invalid"),
  USER_NOT_FOUND(HttpStatus.NOT_FOUND, "40401", "Username is not found"),
  INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "40102", "Token is invalid"),
  ARTICLE_NOT_FOUND(HttpStatus.NOT_FOUND, "40402", "Article is not found"),
  INVALID_PERMISSION(HttpStatus.UNAUTHORIZED, "40103", "Permission is invalid"),
  ARTICLE_LIKE_EXCEPTION(HttpStatus.BAD_REQUEST, "40001", "Article is already like or disLike ");

  // error code에 넣어줄 필드를 정의
  private HttpStatus status; // error의 http status 값
  private String resultCode; // error의 결과 코드
  private String message; // error의 결과 메세지
}
