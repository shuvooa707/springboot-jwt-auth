package com.cruddb.jwtauththus.controller;

import com.cruddb.jwtauththus.request.LoginRequest;
import com.cruddb.jwtauththus.response.LoginFailedResponse;
import com.cruddb.jwtauththus.response.LoginResponse;
import com.cruddb.jwtauththus.response.LoginSuccessResponse;
import com.cruddb.jwtauththus.service.JwtService;
import org.hibernate.loader.ast.spi.Loader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    JwtService jwtService;

    @PostMapping({"login", "/login"})
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {

        System.out.println("\n\n\n\n\n");
        System.out.println( new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()) );
        System.out.println("\n\n\n\n\n");

        // authenticate
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
//
        if ( !authentication.isAuthenticated() ) {
            return ResponseEntity.ok(new LoginFailedResponse("failed", "Invalid Credentials"));
        }
//
//
        String bearerToken = jwtService.generateToken(loginRequest.getUsername());

        return ResponseEntity.ok(LoginSuccessResponse.builder( "success",  bearerToken ));
    }

    @PostMapping({"register", "/register"})
    public ResponseEntity<?> register(@RequestParam(value = "username", required = true) String username, @RequestParam(value = "password", required = true) String password) {
        return ResponseEntity.ok("Welcome " + username);
    }

    @PostMapping({"logout", "/logout"})
    public ResponseEntity<?> logout() {
        SecurityContextHolder.getContext().setAuthentication(null);
        return ResponseEntity.ok("success");
    }
}
