package com.example.passwordmanager.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class BcryptPasswordEncoderUtils {
    private BCryptPasswordEncoder passwordEncoder;

    public BcryptPasswordEncoderUtils(){
        passwordEncoder = new BCryptPasswordEncoder();
    }

    public String encode(String value){
        return passwordEncoder.encode(value);
    }

    public Boolean validHash(String password,String hash){
        return passwordEncoder.matches(password,hash);
    }
}
