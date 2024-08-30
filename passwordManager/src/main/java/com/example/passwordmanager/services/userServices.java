package com.example.passwordmanager.services;

import com.example.passwordmanager.entity.User;
import com.example.passwordmanager.exceptions.user.UserAlreadyExistsException;
import com.example.passwordmanager.repository.UserDao;
import com.example.passwordmanager.utils.BcryptPasswordEncoderUtils;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        try {
            userDao.save(user);
        }catch (Exception e){
            throw new RuntimeException("Failed to save user: " + e.getMessage(), e);
        }
    }

}
