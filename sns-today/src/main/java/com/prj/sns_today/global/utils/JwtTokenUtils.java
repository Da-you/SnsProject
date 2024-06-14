package com.prj.sns_today.global.utils;


import com.prj.sns_today.global.exception.ApplicationException;
import com.prj.sns_today.global.exception.ErrorCode;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import javax.crypto.SecretKey;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.stereotype.Component;

@Slf4j
@Component
public class JwtTokenUtils {

  @Value("${jwt.secret-key}")
  private String key;
  private final String ROLES = "roles";

  private final long ACCESS_TOKEN_EXPIRED_TIME = 7 * 24 * 60 * 60 * 1000L; // 7 days
//  private final long ACCESS_TOKEN_EXPIRED_TIME = 10L; // 7 days

  // 토큰에 sub(userId) , key(암호화시 이용하는 키)를 저장하며 sub를 통해 사용자 확인 ㅁ
  public String generateToken(Long sub, String role) {
    Claims claims = Jwts.claims();
    claims.setSubject(sub.toString());
    claims.put(ROLES, role);
    Date now = new Date();

    return Jwts.builder().setClaims(claims)
        .setIssuedAt(now)
        .setExpiration(new Date(now.getTime() + ACCESS_TOKEN_EXPIRED_TIME))
        .signWith(getKey(key), SignatureAlgorithm.HS256)
        //.signWith(getKey(key)) // 0.12.5 버전
        .compact();
  }


  private static SecretKey getKey(String key) {
    byte[] keyBytes = key.getBytes(StandardCharsets.UTF_8); // 바이트로 변환한 키값을 가져 온다.
    return Keys.hmacShaKeyFor(keyBytes); // 변환된 키 값을 반환
//    return Keys.hmacShaKeyFor(keyBytes);
  }

  // key를 이용해 토큰을 파싱하여 토큰의 페이로드(바디)값 확인
  private static Claims parseClaims(String token, String key) {
    return Jwts.parserBuilder()
        .setSigningKey(getKey(key))
        .build()
        .parseClaimsJws(token).getBody();
  }


  // 유효 시간 검증
  public static boolean isExpired(String token, String key) {
    Claims claims = parseClaims(token, key);
    Date expired = claims.getExpiration();
    return expired.before(new Date());
  }

  public static Long getSubject(String token, String key) {
    Claims claims = parseClaims(token, key);
    return Long.parseLong(claims.getSubject());
  }

  // 어디서 검증을 해주는 것인가.
  public boolean validateToken(String token, String key) {
    try {
      Claims claims = parseClaims(token, key);
      log.info(String.valueOf(claims.getNotBefore()));
      Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
//      Jwts.parser().verifyWith(key).build().parseSignedClaims(compactJws);
      return true;
    } catch (SecurityException | MalformedJwtException e) {
      log.error("잘못된 Jwt 서명입니다.");
    } catch (ExpiredJwtException e) {
      log.error("만료된 토큰입니다.");
      throw new ApplicationException(ErrorCode.INVALID_PERMISSION);
    } catch (UnsupportedJwtException e) {
      log.error("지원하지 않는 토큰입니다.");
    } catch (IllegalArgumentException e) {
      log.error("잘못된 토큰입니다.");
    }
    return false;
  }


}
