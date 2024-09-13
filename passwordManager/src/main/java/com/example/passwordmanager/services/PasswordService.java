package com.example.passwordmanager.services;


import com.example.passwordmanager.DTO.DecryptPasswordRequestDto;
import com.example.passwordmanager.DTO.passwordDto;
import com.example.passwordmanager.DTO.passwordListDto;
import com.example.passwordmanager.entity.Password;
import com.example.passwordmanager.entity.User;
import com.example.passwordmanager.repository.PasswordDao;
import com.example.passwordmanager.utils.AesCryptoUtil;
import com.example.passwordmanager.utils.SecurityUtils;
import com.example.passwordmanager.utils.ShamirSecretSharingUtil;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PasswordService {
    @Getter
    @Autowired
    private PasswordDao passwordDao;
    @Autowired
    private userServices userServices;
    @Autowired
    private ShamirSecretSharingUtil secretSharingUtil;
    public void savePassword(passwordDto dto){
        try {
            Password password = getPassword(dto, getMasterKey(dto.getKey()));
            passwordDao.save(password);
        }catch (Exception e){
            throw new RuntimeException(e.getMessage(),e);
        }
    }

    public List<passwordListDto> fetchAllPassword(){
        List<Password> passwordList = passwordDao.findAllByUserName(userServices.getUser().getUsername());
        List<passwordListDto> dtoList = passwordList.stream()
                .map(password -> {
                    passwordListDto dto = new passwordListDto();
                    dto.setId(password.getId());
                    dto.setType(password.getType());
                    dto.setService_name(password.getService_name());
                    dto.setUsername(password.getUsername());
                    return dto;
                })
                .collect(Collectors.toList());
        return dtoList;
    }

    public passwordDto getPasswordDao(Integer id) {
        Password password = passwordDao.findPasswordByIdAndUserName(userServices.getUser().getUsername(),id);
        passwordDto dto = new passwordDto(password.getUsername(), password.getEncrypted_password(),password.getService_name(),password.getType(), password.getService_url(), null);
        return dto;
    }

    public String cipherPasswordDecrypt(DecryptPasswordRequestDto dto) throws InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, IOException, NoSuchAlgorithmException, BadPaddingException, InvalidKeySpecException, InvalidKeyException, NoSuchProviderException {
        AesCryptoUtil aesCryptoUtil = getAesUtil(getMasterKey(dto.getKey()));
        return aesCryptoUtil.decrypt(dto.getCiphertext());
    }

    private Password getPassword(passwordDto dto, String masterkey) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, IOException, InvalidKeySpecException, NoSuchProviderException {
        User user = userServices.getUser();
        AesCryptoUtil cryptoUtil = getAesUtil(masterkey);
        Password password = new Password();
        password.setUser(user);
        password.setUsername(dto.getUsername());
        password.setEncrypted_password(cryptoUtil.encrypt(dto.getPassword()));
        password.setService_name(dto.getService_name());
        password.setService_url(dto.getService_url());
        password.setType(dto.getType());
        return password;
    }

    private List<String> getFullKeyShare(String key){
        List<String> shares = new ArrayList<>();
        shares.add(key);
        shares.add(SecurityUtils.getDetails());
        return shares;
    }

    private String getMasterKey(String key){
        return secretSharingUtil.secretDecode(getFullKeyShare(key));
    }

    private AesCryptoUtil getAesUtil(String masterkey){
        return new AesCryptoUtil(masterkey,userServices.getUser().getSalt());
    }
}
