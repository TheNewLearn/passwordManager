package com.example.passwordmanager.security;
import com.example.passwordmanager.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

public class userDetail implements UserDetails {
    private User user;

    public userDetail(User user){
        this.user = user;
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList();
    }

    @Override
    public String getPassword() {
        return user.getPasswordHash();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    public String getUserMasterKey(){
        return user.getMasterKeyHash();
    }

}
