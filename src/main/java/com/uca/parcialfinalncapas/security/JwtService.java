package com.uca.parcialfinalncapas.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class JwtService {
    private final CustomUserDetailsService customUserDetailsService;
    private final String JwtSecret;
    private final Long JwtExpirationMillis;

    public JwtService(CustomUserDetailsService customUserDetailsService,
                      @Value("${spring.jwt.secret}") String jwtSecret,
                      @Value("${spring.jwt.expiration}") Long jwtExpirationMillis) {
        this.customUserDetailsService = customUserDetailsService;
        this.JwtSecret = jwtSecret;
        this.JwtExpirationMillis = jwtExpirationMillis;
    }

    public String generateToken(String username) {
        UserDetails user = customUserDetailsService.loadUserByUsername(username);
        return Jwts.builder()
                   .subject(user.getUsername())
                   .issuedAt(new Date())
                   .expiration(new Date(System.currentTimeMillis() + JwtExpirationMillis))
                   .signWith(getSigningKey())
                   .compact();
    }

    public String extractUsername(String token) {
        return Jwts.parser()
                   .verifyWith(getSigningKey())
                   .build()
                   .parseSignedClaims(token)
                   .getPayload()
                   .getSubject();
    }

    private SecretKey getSigningKey() {
        byte[] base64DecodedSecret = Decoders.BASE64.decode(JwtSecret);
        return Keys.hmacShaKeyFor(base64DecodedSecret);
    }
}
