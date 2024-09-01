package com.example.passwordmanager.Controller.User;

import com.example.passwordmanager.DTO.userDto;
import com.example.passwordmanager.exceptions.user.UserAlreadyExistsException;
import com.example.passwordmanager.exceptions.user.UserPasswordOrMasterKeyNotMatchException;
import com.example.passwordmanager.security.JwtServices;
import com.example.passwordmanager.security.userDetail;
import com.example.passwordmanager.services.userServices;
import com.example.passwordmanager.utils.ApiResponse;
import com.example.passwordmanager.utils.ShamirSecretSharingUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Tag(name = "test")
@RestController
@RequestMapping("/api/user/auth")
public class UserController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private userServices  userServices;
    @Autowired
    private JwtServices jwtServices;
    @Autowired
    ShamirSecretSharingUtil secretSharingUtil;

    @Operation(summary = "测试")
    @PostMapping("/register")
    public ApiResponse register(@RequestBody userDto userdto){
        try{
            if(!userdto.getPassword().equals(userdto.getConfirmPassword()) || !userdto.getMasterKey().equals(userdto.getConfirmMasterKey())){
                throw new UserPasswordOrMasterKeyNotMatchException("Password or Master Key not match");
            }
            userServices.registerUser(userdto.getUsername(),userdto.getPassword(),userdto.getMasterKey(), userdto.getEmailAddress());
            return new ApiResponse<>(200,"Succes","Create Success",null);
        }catch (UserAlreadyExistsException existsException){
            return new ApiResponse<>(400,"Fail","User Already exist: "+userdto.getUsername(),null);
        }catch (UserPasswordOrMasterKeyNotMatchException passwordOrMasterKeyNotMatchException){
            return new ApiResponse<>(400,"Fail",passwordOrMasterKeyNotMatchException.getMessage(),null);
        }
        catch (Exception e) {
            return new ApiResponse<>(500, "Error", "An unexpected error occurred: " + e.getMessage(),null);
        }
    }
    @Operation(summary = "测试2")
    @PostMapping("/login")
    public ApiResponse login(@RequestBody userDto dto){
        try{
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(dto.getUsername(),dto.getPassword());
            authToken.setDetails(dto.getMasterKey());
            Authentication authentication = authenticationManager.authenticate(authToken);
            List<String> secretSplit = secretSharingUtil.splitSecret(dto.getMasterKey());
            String jwtToken = jwtServices.generateToken(authentication,secretSplit.get(1));
            Map<String,String> respData = new HashMap<>();
            respData.put("jwtToken",jwtToken);
            respData.put("s1",secretSplit.get(0));
            return new ApiResponse<>(200,"Success","login success",respData);
        }catch (UsernameNotFoundException usernameNotFoundException){
            return new ApiResponse<>(403, "Error", usernameNotFoundException.getMessage(),null);
        }
        catch (BadCredentialsException badCredentialsException){
            return new ApiResponse<>(403, "Error", badCredentialsException.getMessage(),null);
        }
        catch (Exception e){
            return new ApiResponse<>(500, "Error", "An unexpected error occurred: " + e.getMessage(),null);
        }
    }

    @Operation(summary = "test3")
    @SecurityRequirement(name = "Authorization")
    @GetMapping("/key")
    public ApiResponse fetchMasterKey(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication != null){
            userDetail detail = (userDetail) authentication.getPrincipal();
           return new ApiResponse<>(200,"success","key success",detail.getUserMasterKey());
        }
        return new ApiResponse<>(403,"fail","key fail",null);
    }

}
