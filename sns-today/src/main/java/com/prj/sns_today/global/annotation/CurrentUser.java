package com.prj.sns_today.global.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

@Target(value = ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
/* 인증 객체 정보를 가지고 다니는 커스텀 어노테이션, CustomUserDetail 필드인 userId를 가져온다. */
@AuthenticationPrincipal(expression = "#this == 'anonymousUser' ? null : userId")
public @interface CurrentUser {

}
