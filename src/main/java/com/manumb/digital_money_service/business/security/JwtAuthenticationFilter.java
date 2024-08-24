package com.manumb.digital_money_service.business.security;

import com.manumb.digital_money_service.business.jwt.services.JwtServiceHandler;
import com.manumb.digital_money_service.business.users.UserService;
import io.jsonwebtoken.lang.Strings;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final UserService userService;
    private final JwtServiceHandler jwtServiceHandler;
    @Value("${jwt.secret_word}")
    private String secretWord;

    public JwtAuthenticationFilter(UserService userService, JwtServiceHandler jwtServiceHandler) {
        this.userService = userService;
        this.jwtServiceHandler = jwtServiceHandler;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //extract header to http request and look for a 'Bearer token' into him
        //if don´t exist a 'Bearer token' or already there is an Authentication instance (already authenticated user) in the security context
        //continue the filter chain without valid
        String authorizationHeader = request.getHeader("Authorization");
        if(!Strings.hasText(authorizationHeader) || !authorizationHeader.startsWith(secretWord) || SecurityContextHolder.getContext().getAuthentication() != null){
            filterChain.doFilter(request,response);
            return;
        }

        //take out jwt from header
        String jwt = authorizationHeader.replace(secretWord, "");

        //check if the token is invalidated
        if (jwtServiceHandler.isTokenInvalidated(jwt)) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        String username = jwtServiceHandler.extractUsername(jwt);
        if(username == null){
            filterChain.doFilter(request,response);
            return;
        }

        //extract 'principal' from payload

        //get User from DB
        var user = userService.findByEmail(username);

        //create an Authentication object, set additional details from request and update security context with the new 'principal'
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user,null,user.getAuthorities());
        authenticationToken.setDetails(new WebAuthenticationDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        //continue filter chain
        filterChain.doFilter(request,response);
    }
}
