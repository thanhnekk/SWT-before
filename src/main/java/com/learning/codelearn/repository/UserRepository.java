package com.learning.codelearn.repository;

import com.learning.codelearn.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String userName);

    User findFirstByUsername(String username);
     User findByEmail(String email);
    User findByResetPasswordToken(String resetPasswordToken);
}
