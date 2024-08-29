package com.example.passwordmanager.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class userDto {
    private String username;
    private String password;
    private String confirmPassword;
    private String masterKey;
    private String confirmMasterKey;
    private String emailAddress;
}
