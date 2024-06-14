package com.prj.sns_today.domain.users.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Role {
ADMIN("ADMIN"),USER("USER");
private final String code;
}
