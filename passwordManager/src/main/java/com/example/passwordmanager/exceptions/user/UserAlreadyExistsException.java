package com.example.passwordmanager.exceptions.user;

public class UserAlreadyExistsException extends Exception{
    public UserAlreadyExistsException(String msg){
        super(msg);
    }
}
