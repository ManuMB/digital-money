package com.manumb.digital_money_service.business.jwt;

import com.manumb.digital_money_service.business.users.User;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public interface JwtService {

    String extractUsername(String jwt);

    String generateEmailToken(UserDetails user);

    JsonWebToken generateToken(Map<String, Object> extraClaims, UserDetails user);

    void invalidateToken(String token);

    void verifyAuthorization(Long accountId);

    default Map<String, Object> generateExtraClaimsOfUser(User user) {

        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("authorities", user.getAuthorities().stream().map((GrantedAuthority::getAuthority)).collect(Collectors.toList()));

        return extraClaims;
    }

}
