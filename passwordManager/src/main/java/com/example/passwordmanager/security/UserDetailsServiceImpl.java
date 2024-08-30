package com.example.passwordmanager.security;

import com.example.passwordmanager.repository.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserDao userDao;
    @Override
    public userDetail loadUserByUsername(String username) throws UsernameNotFoundException {
        return new userDetail(userDao.findUsersByUsername(username));
    }
}
