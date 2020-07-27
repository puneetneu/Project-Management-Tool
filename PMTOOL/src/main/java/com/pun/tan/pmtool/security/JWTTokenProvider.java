package com.pun.tan.pmtool.security;

import com.pun.tan.pmtool.domain.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.pun.tan.pmtool.security.SecurityConstants.EXPIRATION_TIME;
import static com.pun.tan.pmtool.security.SecurityConstants.SECRET;

@Component
public class JWTTokenProvider {

    public String generateToke(Authentication authentication){
        User user = (User) authentication.getPrincipal();
        Date now = new Date(System.currentTimeMillis());

        Date expriryDate = new Date(now.getTime()+EXPIRATION_TIME);

        String userId = Long.toString(user.getId());

        Map<String, Object> claims = new HashMap<>();
        claims.put("id", Long.toString(user.getId()));
        claims.put("username", user.getUsername());
        claims.put("fullName", user.getFullName());

        return Jwts.builder()
                .setSubject(userId)
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(expriryDate)
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .compact();
    }
}
