package com.example.passwordmanager.Controller.Password;

import com.example.passwordmanager.DTO.passwordDto;
import com.example.passwordmanager.entity.Password;
import com.example.passwordmanager.entity.User;
import com.example.passwordmanager.repository.PasswordDao;
import com.example.passwordmanager.services.userServices;
import com.example.passwordmanager.utils.AesCryptoUtil;
import com.example.passwordmanager.utils.ApiResponse;
import com.example.passwordmanager.utils.SecurityUtils;
import com.example.passwordmanager.utils.ShamirSecretSharingUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Tag(name = "測試")
@RestController
@RequestMapping("/api/user/passwordManager")
public class PasswordController {
    @Autowired
    private PasswordDao password_dao;
    @Autowired
    private userServices userservices;
    @Autowired
    private ShamirSecretSharingUtil secretSharingUtil;

    @Operation(summary = "测试2")
    @SecurityRequirement(name = "Authorization")
    @PostMapping("/addItem")
    public ApiResponse savePassword(@RequestBody passwordDto dto){
        try {
            User user = userservices.getUser();
            AesCryptoUtil cryptoUtil = new AesCryptoUtil(user.getMasterKeyHash(),user.getSalt());
            Password password = new Password();
            password.setUser(user);
            password.setUsername(dto.getUsername());
            password.setEncrypted_password(cryptoUtil.encrypt(dto.getPassword()));
            password.setService_name(dto.getService_name());
            password.setService_url(dto.getService_url());
            password.setType(dto.getType());
            password_dao.save(password);
            return new ApiResponse<>(200,"success","Add Password Success",null);

        }catch (Exception e){
            return new ApiResponse<>(400,"fail",e.getMessage(),null);
        }
    }
    @Operation(summary = "测试3")
    @SecurityRequirement(name = "Authorization")
    @GetMapping("/passwordList")
    public ApiResponse getPasswordList(){
        try {
            List<Password> passwordList = password_dao.findAllByUserName(SecurityUtils.getCurrentUserDetails().getUsername());
            return new ApiResponse<>(200,"success","fetch all",passwordList);
        }catch (Exception e){
            return new ApiResponse<>(400,"fail",e.getMessage(),null);
        }
    }
    @Operation(summary = "测试3")
    @SecurityRequirement(name = "Authorization")
    @GetMapping("/decrypt/{id}")
    public ApiResponse getDecryptPassword(@PathVariable int id){
        try {
            User user = userservices.getUser();
            Password password = password_dao.findPasswordByIdAndUserName(user.getUsername(), id);
            AesCryptoUtil cryptoUtil = new AesCryptoUtil(user.getMasterKeyHash(),user.getSalt());
            String decrypted = cryptoUtil.decrypt(password.getEncrypted_password());
            return new ApiResponse<>(200,"success","fetch all",decrypted);
        }catch (Exception e){
            return new ApiResponse<>(400,"fail",e.getMessage(),null);
        }
    }

    @Operation(summary = "testshare")
    @SecurityRequirement(name = "Authorization")
    @PostMapping("/test/share")
    public ApiResponse testShare(@RequestParam(name = "key") String key){
        List<String> res = secretSharingUtil.splitSecret(key);
        return new ApiResponse<>(200,"success","fetch all",res.get(0));
    }

    @Operation(summary = "testdecode")
    @SecurityRequirement(name = "Authorization")
    @PostMapping("/test/decode")
    public ApiResponse testSharedecode(@RequestParam(name = "key") String key){
        List<String> shares = new ArrayList<>();
        shares.add(key);
        shares.add(SecurityUtils.getDetails());
       String test = secretSharingUtil.secretDecode(shares);
        return new ApiResponse<>(200,"success","fetch all",test);
    }

}
