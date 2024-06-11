package com.prj.sns_today.global.configuration;

import com.prj.sns_today.domain.users.application.UserService;
import com.prj.sns_today.global.exception.UnAuthenticationEntryPoint;
import com.prj.sns_today.global.utils.filter.JwtTokenFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class AuthenticationConfig extends WebSecurityConfigurerAdapter {

  private final UserService userService;

  @Value("${jwt.secret-key}")
  private String key;

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.csrf().disable()
        .authorizeHttpRequests()
        .antMatchers("/users/**").permitAll()
        .antMatchers("/*").authenticated()
        .and()
        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
        .exceptionHandling()
        .authenticationEntryPoint(
            new UnAuthenticationEntryPoint()) // 필터에서 error가 발생하면 exceptionHandling을 통해 별도의 엔트리 포인트로 보냄
        .and()
        .addFilterBefore(new JwtTokenFilter(key, userService),
            UsernamePasswordAuthenticationFilter.class);
  }
}
