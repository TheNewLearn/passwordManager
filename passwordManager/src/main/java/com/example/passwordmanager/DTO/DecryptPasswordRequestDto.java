package com.example.passwordmanager.DTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class DecryptPasswordRequestDto {
    private String ciphertext;
    private String key;
}
