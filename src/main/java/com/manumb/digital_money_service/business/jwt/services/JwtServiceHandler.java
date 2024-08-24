package com.manumb.digital_money_service.business.jwt.services;

import com.manumb.digital_money_service.business.jwt.JsonWebToken;
import com.manumb.digital_money_service.business.jwt.JwtService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Service
public class JwtServiceHandler implements JwtService {

    @Value("${jwt.secret_key}")
    private String SECRET_KEY;
    @Value("${jwt.expiration_in_minutes}")
    private Long EXPIRATION_IN_MINUTES;
    private final Set<String> tokenBlacklist = new HashSet<>();

    private SecretKey generateKey(){
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }

    @Override
    public String extractUsername(String jwt) {
        return Jwts.parser()
                .verifyWith(generateKey())
                .build()
                .parseSignedClaims(jwt)
                .getPayload()
                .getSubject();
    }

    @Override
    public JsonWebToken generateToken(Map<String, Object> extraClaims, UserDetails user){
        Date now = new Date(System.currentTimeMillis());
        var token = Jwts.builder()
                .header().type("JWT").and()
                .subject(user.getUsername())
                .signWith(generateKey(),Jwts.SIG.HS256)
                .issuedAt(now)
                .expiration(new Date(now.getTime() + (1000L * 60 * EXPIRATION_IN_MINUTES)))
                .claims(extraClaims)
                .compact();
        return new JsonWebToken(token);
    }

    @Override
    public String generateEmailToken(UserDetails user) {
        Date now = new Date(System.currentTimeMillis());
        var token = Jwts.builder()
                .header().type("JWT").and()
                .subject(user.getUsername())
                .signWith(generateKey(),Jwts.SIG.HS256)
                .issuedAt(now)
                .expiration(new Date(now.getTime() + (1000L * 60 * EXPIRATION_IN_MINUTES)))
                .compact();
        return String.valueOf(token);
    }

    // Invalidate the token by adding it to the blacklist
    @Override
    public void invalidateToken(String token) {
        tokenBlacklist.add(token);
        SecurityContextHolder.clearContext();
    }

    // Check if the token is invalidated
    public boolean isTokenInvalidated(String token) {
        return tokenBlacklist.contains(token);
    }
}
