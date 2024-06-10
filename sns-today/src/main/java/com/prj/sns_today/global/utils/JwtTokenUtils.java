package com.prj.sns_today.global.utils;



import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

public class JwtTokenUtils {

  // 토큰에 username , key(암호화시 이용하는 키) , 유효시간을 넣는다. 추후 토큰을 열어 username을 통해 사용자 확인
  public static String generateToken(String username, String key, long expiredTimeMs) {
    Claims claims = (Claims) Jwts.claims();
    claims.put("username", username); // ?

    return Jwts.builder().claims(claims)
        .issuedAt(new Date(System.currentTimeMillis())) // 생성 시간
        .expiration(new Date(System.currentTimeMillis() + expiredTimeMs)) // 유효시간
        .signWith(getKey(key), SignatureAlgorithm.HS256)
        .compact();


  }

  private static Key getKey(String key) {
    byte[] keyBytes = key.getBytes(StandardCharsets.UTF_8); // 바이트로 변환한 키값을 가져 온다.
    return Keys.hmacShaKeyFor(keyBytes); // 변환된 키 값을 반환
  }

}
