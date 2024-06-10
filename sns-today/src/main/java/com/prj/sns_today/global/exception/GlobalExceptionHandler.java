package com.prj.sns_today.global.exception;

import com.prj.sns_today.global.response.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {


  @ExceptionHandler(ApplicationException.class)
  public ResponseEntity<?> applicationHandler(ApplicationException e) {
    log.info("error occurs {}", e.toString());
    return ResponseEntity.status(e.getErrorCode().getStatus())
        .body(ApiResponse.error(e.getErrorCode().name()));
  }

  // 좀 더 큰 범위의 에러
  @ExceptionHandler(RuntimeException.class)
  public ResponseEntity<?> applicationHandler(RuntimeException e) {
    log.info("error occurs {}", e.toString());
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body(ApiResponse.error(ErrorCode.INTERNAL_SERVER_ERROR.name()));
  }
}
