package com.prj.sns_today.domain.users.repository;

import com.prj.sns_today.domain.users.domain.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

  Boolean existsByUsername(String username);
  Optional<User> findByUsername(String username);
}
