package com.example.passwordmanager.repository;

import com.example.passwordmanager.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao extends JpaRepository<User,Integer> {
    User findUsersByUsername(String username);
}
