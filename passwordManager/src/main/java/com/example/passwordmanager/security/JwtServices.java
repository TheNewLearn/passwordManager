package com.example.passwordmanager.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtServices {
    private Long expire_time = (long)60*60*1000;
    @Value("${jwt.secret.key}")
    private String jwtSecret_key;

    public String generateToken(Authentication authentication){
        userDetail userDetail = (userDetail) authentication.getPrincipal();
        return generateToken(new HashMap<>(),userDetail);
    }

    private String generateToken(Map<String, Object> extractClaims,userDetail userDetail){
        return Jwts
                .builder()
                .setClaims(extractClaims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expire_time))
                .setSubject(userDetail.getUsername())
                .signWith(getSignInKey())
                .compact();
    }

    public boolean isTokenValid(String token, userDetail userDetail){
        final String username = extractClaim(token, Claims::getSubject);
        return username != null && username.equals(userDetail.getUsername()) && !isTokenExpired(token);
    }
    private boolean isTokenExpired(String token){
        final Date expireDate = extractClaim(token, Claims::getExpiration);
        return expireDate == null || expireDate.before(new Date());
    }

    private Claims extractAllClamis(String token){
        return Jwts.
                parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClamis(token);
        return claimsResolver.apply(claims);
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtSecret_key);
        return Keys.hmacShaKeyFor(keyBytes);
    }

}
