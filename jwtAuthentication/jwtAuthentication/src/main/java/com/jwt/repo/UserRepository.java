package com.jwt.repo;

import com.jwt.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository <User, Long>{
    // username, it will return the user from given name
    public User findByUsername(String username);

}
