package com.example.passwordmanager.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class passwordDto {
    private String username;
    private String password;
    private String service_name;
    private String type;
    private String service_url;
    private String master_key;
}
