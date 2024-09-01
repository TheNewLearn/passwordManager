package com.example.passwordmanager.services;

import com.example.passwordmanager.entity.User;
import com.example.passwordmanager.exceptions.user.UserAlreadyExistsException;
import com.example.passwordmanager.repository.UserDao;
import com.example.passwordmanager.utils.AesCryptoUtil;
import com.example.passwordmanager.utils.BcryptPasswordEncoderUtils;
import com.example.passwordmanager.utils.SecurityUtils;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class userServices {
    @Autowired
    private BcryptPasswordEncoderUtils passwordEncoderUtils;
    @Autowired
    private UserDao userDao;

    @Transactional
    public void registerUser(String username,String password, String masterKey, String emailAddress) throws UserAlreadyExistsException {
        if(userDao.findUsersByUsername(username) != null){
            throw new UserAlreadyExistsException("Username already exists: " + username);
        }
        User user = new User();
        user.setUsername(username);
        user.setPasswordHash(passwordEncoderUtils.encode(password));
        user.setMasterKeyHash(passwordEncoderUtils.encode(masterKey));
        user.setEmailAddress(emailAddress);
        user.setSalt(AesCryptoUtil.generateSalt());
        try {
            userDao.save(user);
        }catch (Exception e){
            throw new RuntimeException("Failed to save user: " + e.getMessage(), e);
        }
    }

    public User getUser(){
        String username = SecurityUtils.getCurrentUserDetails().getUsername();
        if(!StringUtils.hasText(username)){
            throw new UsernameNotFoundException("not find user");
        }
        return userDao.findUsersByUsername(username);
    }

}
