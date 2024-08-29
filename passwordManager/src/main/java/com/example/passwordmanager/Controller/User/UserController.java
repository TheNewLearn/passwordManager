package com.example.passwordmanager.Controller.User;

import com.example.passwordmanager.DTO.userDto;
import com.example.passwordmanager.entity.User;
import com.example.passwordmanager.exceptions.user.UserAlreadyExistsException;
import com.example.passwordmanager.exceptions.user.UserPasswordOrMasterKeyNotMatchException;
import com.example.passwordmanager.services.userServices;
import com.example.passwordmanager.utils.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private userServices  userServices;

    @PostMapping("/register")
    public ApiResponse register(@RequestBody userDto userdto){
        try{
            if(!userdto.getPassword().equals(userdto.getConfirmPassword()) || !userdto.getMasterKey().equals(userdto.getConfirmMasterKey())){
                throw new UserPasswordOrMasterKeyNotMatchException("Password or Master Key not match");
            }
            userServices.registerUser(userdto.getUsername(),userdto.getPassword(),userdto.getMasterKey(), userdto.getEmailAddress());
            return new ApiResponse(200,"Succes","Create Success");
        }catch (UserAlreadyExistsException existsException){
            return new ApiResponse(400,"Fail","User Already exist: "+userdto.getUsername());
        }catch (UserPasswordOrMasterKeyNotMatchException passwordOrMasterKeyNotMatchException){
            return new ApiResponse(400,"Fail",passwordOrMasterKeyNotMatchException.getMessage());
        }
        catch (Exception e) {
            return new ApiResponse(500, "Error", "An unexpected error occurred: " + e.getMessage());
        }
    }

}
