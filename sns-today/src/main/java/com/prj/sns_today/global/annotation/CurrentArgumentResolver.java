package com.prj.sns_today.global.annotation;

import com.prj.sns_today.global.utils.CustomUserDetail;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Slf4j
public class CurrentArgumentResolver implements HandlerMethodArgumentResolver {

  @Override
  public boolean supportsParameter(MethodParameter parameter) {

    boolean hasAnnotation = parameter.hasParameterAnnotation(CurrentUser.class);
    boolean isUserType = CustomUserDetail.class.isAssignableFrom(parameter.getParameterType());
    return hasAnnotation && isUserType;
  }

  @Override
  public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
      NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication(); // 사용자의 정보
    if (authentication == null) return null;
    return authentication.getPrincipal();
  }
}
