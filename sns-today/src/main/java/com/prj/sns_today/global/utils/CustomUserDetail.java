package com.prj.sns_today.global.utils;

import com.prj.sns_today.domain.users.domain.User;
import java.util.Collection;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * 보안과 확장: User entity에는 로그인 및 인증에 필요한 정보 외에도 다양한 사용자 정보가 포함되어 있을 수 있습니다.
 * 이러한 경우 UserDetails 인터페이스를 구현한 별도의 커스텀 클래스를 만들면, 인증 및 권한 처리에 필요한 최소한의 정보만을 포함할 수 있습니다.
 * 이를 통해 보안성과 확장성을 높일 수 있습니다.
 * 분리와 독릭: User entity와 UserDetails 구현 클래스를 분리하면, 사용자 정보와 인증 정보를 독립적으로 관리할 수 있습니다.
 * 이는 향후 사용자 정보 모델의 변경이나 추가적인 인증 정보의 필요 등에 유연하게 대응할 수 있도록 해줍니다.
 */

@Getter
@AllArgsConstructor
public class CustomUserDetail implements UserDetails {
private final Long userId;
private User user;

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return List.of(new SimpleGrantedAuthority(user.getRole().toString()));
  }

  @Override
  public String getPassword() {
    return "";
  }

  @Override
  public String getUsername() {
    return "";
  }

  @Override
  public boolean isAccountNonExpired() {
    return false;
  }

  @Override
  public boolean isAccountNonLocked() {
    return false;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return false;
  }

  @Override
  public boolean isEnabled() {
    return false;
  }
}
