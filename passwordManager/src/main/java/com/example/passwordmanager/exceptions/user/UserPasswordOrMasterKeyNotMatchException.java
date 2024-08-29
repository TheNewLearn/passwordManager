package com.example.passwordmanager.exceptions.user;

public class UserPasswordOrMasterKeyNotMatchException extends Exception {
    public UserPasswordOrMasterKeyNotMatchException(String msg){
        super(msg);
    }
}
