package com.example.passwordmanager.utils;

import com.example.passwordmanager.security.userDetail;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class SecurityUtils {
    public static userDetail getCurrentUserDetails() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof userDetail) {
            return (userDetail) authentication.getPrincipal();
        }
        return null;
    }
}
