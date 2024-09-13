package com.example.passwordmanager.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class passwordListDto {
    private Integer id;
    private String service_name;
    private String username;
    private String type;
}
