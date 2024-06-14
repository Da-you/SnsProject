package com.prj.sns_today.global.exception;

import com.prj.sns_today.global.response.ApiResponse;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

// 유효햔 자격증명을 제공하지 않고 접근시 401 에러를 보내기 위한 엔트리 포인트
@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

  @Override
  public void commence(HttpServletRequest request, HttpServletResponse response,
      AuthenticationException authException) throws IOException, ServletException {
    response.setContentType("application/json");
    response.setStatus(ErrorCode.INVALID_TOKEN.getStatus().value());
    response.getWriter()
        .write(ApiResponse.error(ErrorCode.INVALID_TOKEN.getStatus().name()).toStream());
  }
}
