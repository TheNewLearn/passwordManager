package com.example.passwordmanager.security.filter;

import com.example.passwordmanager.security.JwtServices;
import com.example.passwordmanager.security.UserDetailsServiceImpl;
import com.example.passwordmanager.security.userDetail;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;

@Component
public class JwtTokenFilter extends OncePerRequestFilter {
    @Autowired
    private JwtServices jwtServices;
    @Autowired
    private UserDetailsServiceImpl userDetailsService;
    private final String tokenheader = "Authorization";
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader(tokenheader);
        if(StringUtils.hasText(authHeader)){
            String token = authHeader.replace("Bearer ","");
            String username = jwtServices.extractClaim(token, Claims::getSubject);
            if(username != null && SecurityContextHolder.getContext().getAuthentication() == null){
                userDetail userDetail = userDetailsService.loadUserByUsername(username);
                if(jwtServices.isTokenValid(token,userDetail)){
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetail,null,userDetail.getAuthorities());
                    authToken.setDetails(jwtServices.extractClaim(token,claims -> claims.get("s2")));
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }
        }
        filterChain.doFilter(request,response);
    }
}
