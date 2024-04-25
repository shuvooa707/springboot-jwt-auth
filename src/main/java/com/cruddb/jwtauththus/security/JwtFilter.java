package com.cruddb.jwtauththus.security;

import com.cruddb.jwtauththus.service.CustomUserServiceDetails;
import com.cruddb.jwtauththus.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {
    @Autowired
    JwtService jwtService;
    @Autowired
    CustomUserServiceDetails customUserServiceDetails;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // retrieve the bearer token
        String authorizationHeader = request.getHeader("Authorization");


        String username = null;
        String token = null;

        // check if token exists
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            token = authorizationHeader.substring(7);
            username = jwtService.extractUsername(token);
        }

        // retrieve auth manager
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        System.out.println("\n\n\n\n--------------------------------");
        System.out.println(authentication);
        System.out.println("--------------------------------");

        // check if username or authentication is null
        if (username != null && authentication == null) {
            UserDetails userDetails = customUserServiceDetails.loadUserByUsername(username);
            if ( userDetails != null && jwtService.validateToken(token, userDetails) ) {
                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }

        filterChain.doFilter(request, response);
    }
}
