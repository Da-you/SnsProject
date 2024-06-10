package com.prj.sns_today.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ApplicationException extends RuntimeException {

  private ErrorCode errorCode;


  @Override
  public String getMessage() {
    return errorCode.getMessage();
  }
}
