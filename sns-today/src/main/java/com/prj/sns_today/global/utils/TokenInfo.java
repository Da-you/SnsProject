package com.prj.sns_today.global.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TokenInfo {

  private String accessToken;
  private String refreshToken;
  private Long accessTokenExpiredDate;
}
