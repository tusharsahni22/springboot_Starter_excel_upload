package com.demo.demo.persistence.respository;

import com.demo.demo.persistence.entity.User;
import jakarta.persistence.Table;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
@ComponentScan
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}