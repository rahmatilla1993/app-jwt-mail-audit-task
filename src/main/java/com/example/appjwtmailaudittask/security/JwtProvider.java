package com.example.appjwtmailaudittask.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtProvider {

    private final long expireTime = 36_000_000;
    private final Date expireDate = new Date(System.currentTimeMillis() + expireTime);
    private final String key = "ThisIsSecretKey";

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

    public boolean isValidateToken(String token){
        try {
            Jwts
                    .parser()
                    .setSigningKey(key)
                    .parseClaimsJws(token);
            return true;
        }
        catch (Exception e){
            return false;
        }
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
