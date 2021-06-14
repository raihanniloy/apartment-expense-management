package com.dsi.starter.flutter_p_o_c.repos;

import com.dsi.starter.flutter_p_o_c.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);
}
