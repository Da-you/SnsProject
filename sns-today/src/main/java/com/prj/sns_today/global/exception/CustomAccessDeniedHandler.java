package com.prj.sns_today.global.exception;

import com.prj.sns_today.global.response.ApiResponse;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

// 필요한 권한이 없는 상태에서 요청시 403 에러를 보내기 위한 handler
@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

  @Override
  public void handle(HttpServletRequest request, HttpServletResponse response,
      AccessDeniedException accessDeniedException) throws IOException, ServletException {
    response.setContentType("application/json");
    response.setStatus(ErrorCode.INVALID_PERMISSION.getStatus().value());
    response.getWriter()
        .write(ApiResponse.error(ErrorCode.INVALID_PERMISSION.getStatus().name()).toStream());
  }
}
