package com.activity.activitycalendar.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;

import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtTokenUtil implements Serializable {
    private static final long serialVersionUID = -2550185165626007488L;
    private static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60;

    @Value("${com.activity.jwtsecret}")
    private String jwtSecret;

    public String generateJwtToken(Map<String,Object> claims, String subject) {
        return Jwts.builder().claims(claims).subject(subject)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
                .signWith(SignatureAlgorithm.HS512,
                        jwtSecret.getBytes(StandardCharsets.UTF_8)).compact();
    }

    public String generateJwtToken(UserDetails userDetails)
    {
        Map<String, Object> claims = new HashMap<>();
        return generateJwtToken(claims, userDetails.getUsername());
    }

    private SecretKey getSignKey() {
       // byte[] keyBytes = Decoders.BASE64.decode("demoapplication");
        return Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
    }

    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().verifyWith(getSignKey()).build().parseSignedClaims(token).getPayload();
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsStringFunction) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsStringFunction.apply(claims);
    }

    public boolean validateToken(String token, UserDetails userDetails) {
       String userNameFromToken = getUserNameFromToken(token);
       return (userNameFromToken.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    public String getUserNameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    public boolean isTokenExpired(String token) {
        return getClaimFromToken(token, Claims::getExpiration).before(new Date());
    }
}
