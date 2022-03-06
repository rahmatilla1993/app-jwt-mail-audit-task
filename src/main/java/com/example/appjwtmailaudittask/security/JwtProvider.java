package com.example.appjwtmailaudittask.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtProvider {

    long expireTime = 36_000_000;
    Date expireDate = new Date(System.currentTimeMillis() + expireTime);
    String key = "ThisIsSecretKey";

    public String generateToken(String username) {
        String token = Jwts
                .builder()
                .setIssuedAt(new Date())
                .setExpiration(expireDate)
                .signWith(SignatureAlgorithm.HS512, key)
                .setSubject(username)
                .compact();
        return token;
    }

    public String getUsernameByToken(String token){
        String username = Jwts
                .parser()
                .setSigningKey(key)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
        return username;
    }
}
