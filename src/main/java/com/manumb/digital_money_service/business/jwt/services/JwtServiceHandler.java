package com.manumb.digital_money_service.business.jwt.services;

import com.manumb.digital_money_service.business.accounts.Account;
import com.manumb.digital_money_service.business.accounts.AccountService;
import com.manumb.digital_money_service.business.jwt.JsonWebToken;
import com.manumb.digital_money_service.business.jwt.JwtService;
import com.manumb.digital_money_service.business.security.exception.AuthorizationException;
import com.manumb.digital_money_service.business.users.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
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
    private final AccountService accountService;

    public JwtServiceHandler(AccountService accountService) {
        this.accountService = accountService;
    }

    @Value("${jwt.secret_key}")
    private String SECRET_KEY;
    @Value("${jwt.expiration_in_minutes}")
    private Long EXPIRATION_IN_MINUTES;
    private final Set<String> tokenBlacklist = new HashSet<>();

    private SecretKey generateKey() {
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
    public JsonWebToken generateToken(Map<String, Object> extraClaims, UserDetails user) {
        Date now = new Date(System.currentTimeMillis());
        var token = Jwts.builder()
                .header().type("JWT").and()
                .subject(user.getUsername())
                .signWith(generateKey(), Jwts.SIG.HS256)
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
                .signWith(generateKey(), Jwts.SIG.HS256)
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

    @Override
    public void verifyAuthorization(Long accountId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!isAuthorized(authentication, accountId)) {
            throw new AuthorizationException("User is not authorized to access this account");
        }
    }

    public boolean isAuthorized(Authentication authentication, Long accountId) {
        if (authentication != null && authentication.getPrincipal() instanceof User) {
            String email = ((User) authentication.getPrincipal()).getEmail();
            Account account = accountService.findById(accountId);
            return account.getUser().getEmail().equals(email);
        }
        return false;
    }

    public boolean isTokenInvalidated(String token) {
        return tokenBlacklist.contains(token);
    }
}
