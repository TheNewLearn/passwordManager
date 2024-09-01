package com.example.passwordmanager.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Password {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String username;
    private String encrypted_password;
    private String service_name;
    private String type;
    private String service_url;
    @ManyToOne(targetEntity = User.class,fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;


}
