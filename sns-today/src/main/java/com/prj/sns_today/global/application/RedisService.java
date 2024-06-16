package com.prj.sns_today.global.application;

import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class RedisService {

  private static final String TOKEN_KEY_FORMAT = "refreshToken:%s";

  private final RedisTemplate<String, String> redisStringTemplate;


  public String getRefreshToken(Long userId) {
    String key = String.format(TOKEN_KEY_FORMAT, userId);
    return redisStringTemplate.opsForValue().get(key);
  }

  public void setRefreshToken(Long userId, String refreshToken, Long timeoutMillis) {
    String key = String.format(TOKEN_KEY_FORMAT, userId);
    redisStringTemplate.opsForValue()
        .set(key, refreshToken, timeoutMillis, TimeUnit.MILLISECONDS);
  }

}
