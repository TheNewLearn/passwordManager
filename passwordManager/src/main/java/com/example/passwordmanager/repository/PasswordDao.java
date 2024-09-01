package com.example.passwordmanager.repository;

import com.example.passwordmanager.entity.Password;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PasswordDao extends JpaRepository<Password,Integer> {
    @Query("SELECT p FROM Password p WHERE p.user.username = :name")
    List<Password> findAllByUserName(@Param("name") String userName);
    @Query("SELECT p FROM Password p WHERE p.user.username = :name and p.id = :id")
    Password findPasswordByIdAndUserName(@Param("name") String userName, @Param("id") int id);
}
