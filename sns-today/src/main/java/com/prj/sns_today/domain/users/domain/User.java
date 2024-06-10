package com.prj.sns_today.domain.users.domain;

import com.prj.sns_today.domain.users.model.Role;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String username;
  private String password;
  @Enumerated(EnumType.STRING)
  private Role role;

  public User(String username, String password) {
    this.username = username;
    this.password = password;
    this.role = Role.USER;
  }

  public static User of(String username, String password) {
    User user = new User();
    user.username = username;
    user.password = password;
    user.role = Role.USER;
    return user;
  }
}
