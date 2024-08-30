package com.example.passwordmanager.security;

import com.example.passwordmanager.utils.BcryptPasswordEncoderUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class UserCustomAuthenticationProvider implements AuthenticationProvider {
    @Autowired
    private UserDetailsServiceImpl userDetailsService;
    @Autowired
    private BcryptPasswordEncoderUtils bcryptEncdoer;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();
        String masterKey = authentication.getDetails().toString();
        userDetail userDetails = userDetailsService.loadUserByUsername(username);
        if (userDetails == null) {
            throw new UsernameNotFoundException("User not found");
        }

        if (!bcryptEncdoer.validHash(password,userDetails.getPassword())) {
            throw new BadCredentialsException("Invalid password");
        }

        if (!bcryptEncdoer.validHash(masterKey,userDetails.getUserMasterKey())) {
            throw new BadCredentialsException("Invalid master key");
        }

        return new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
