package com.example.passwordmanager.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ApiResponse {
    private int  statusCode;
    private String rspStatus;
    private String rspMsg;
}
