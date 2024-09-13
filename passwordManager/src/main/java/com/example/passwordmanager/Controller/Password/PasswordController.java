package com.example.passwordmanager.Controller.Password;

import com.example.passwordmanager.DTO.DecryptPasswordRequestDto;
import com.example.passwordmanager.DTO.passwordDto;
import com.example.passwordmanager.entity.Password;
import com.example.passwordmanager.services.PasswordService;
import com.example.passwordmanager.utils.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.passwordmanager.DTO.passwordListDto;

import java.util.ArrayList;
import java.util.List;


@Tag(name = "測試")
@RestController
@RequestMapping("/api/user/passwordManager")
public class PasswordController {
    @Autowired
    private PasswordService passwordService;


    @Operation(summary = "Add new Password Item")
    @SecurityRequirement(name = "Authorization")
    @PostMapping("/addItem")
    public ApiResponse savePassword(@RequestBody passwordDto dto){
        try {
            passwordService.savePassword(dto);
            return new ApiResponse<>(200,"success","Add Password Success",null);
        }catch (Exception e){
            return new ApiResponse<>(400,"fail",e.getMessage(),null);
        }
    }

    @Operation(summary = "Fetch All password info")
    @SecurityRequirement(name = "Authorization")
    @GetMapping("/passwordList")
    public ApiResponse getPasswordList(){
        try {
            return new ApiResponse<>(200,"success","fetch all",passwordService.fetchAllPassword());
        }catch (Exception e){
            return new ApiResponse<>(400,"fail",e.getMessage(),null);
        }
    }
    @Operation(summary = "Fetch Password")
    @SecurityRequirement(name = "Authorization")
    @GetMapping("/password/{id}")
    public ApiResponse getPassword(@PathVariable Integer id){
        try {
            return new ApiResponse<>(200,"success","fetch all",passwordService.getPasswordDao(id));
        }catch (Exception e){
            return new ApiResponse<>(400,"fail",e.getMessage(),null);
        }
    }
    @Operation(summary = "Decrypt Password")
    @SecurityRequirement(name = "Authorization")
    @PostMapping("/password/decrypt")
    public ApiResponse getDecryptPassword(@RequestBody DecryptPasswordRequestDto dto){
        try {
            return new ApiResponse<>(200,"success","Password Decrypted",passwordService.cipherPasswordDecrypt(dto));
        }catch (Exception e){
            return new ApiResponse<>(400,"fail",e.getMessage(),null);
        }
    }
}
