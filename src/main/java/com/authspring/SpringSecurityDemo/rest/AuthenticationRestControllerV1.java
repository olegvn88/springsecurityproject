package com.authspring.SpringSecurityDemo.rest;

import com.authspring.SpringSecurityDemo.dto.AuthenticationRequestDto;
import com.authspring.SpringSecurityDemo.model.User;
import com.authspring.SpringSecurityDemo.repository.UserRepo;
import com.authspring.SpringSecurityDemo.security.JwtTokenProvider;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthenticationRestControllerV1 {

    private final AuthenticationManager authenticationManager;
    private UserRepo userRepo;
    private JwtTokenProvider jwtTokenProvider;

    public AuthenticationRestControllerV1(AuthenticationManager authenticationManager, UserRepo userRepo,
        JwtTokenProvider jwtTokenProvider) {
        this.authenticationManager = authenticationManager;
        this.userRepo = userRepo;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticate(@RequestBody AuthenticationRequestDto requestDto) {
        try {
            authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(requestDto.getEmail(), requestDto.getPassword())); // authentication user based on email and password
            User user = userRepo.findByEmail(requestDto.getEmail()).orElseThrow(() -> new UsernameNotFoundException(
                "User doesn't exist")); //
            String token = jwtTokenProvider.createToken(requestDto.getEmail(), user.getRole().name()); // create token
            Map<Object, Object> response = new HashMap<>();
            response.put("email", requestDto.getEmail());
            response.put("token", token); // return email and token
            return ResponseEntity.ok(response);

        } catch (AuthenticationException e) {
            return new ResponseEntity<>("Invalid email/password combination", HttpStatus.FORBIDDEN);
        }
    }

    @PostMapping("/logout")
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        SecurityContextLogoutHandler securityContextLogoutHandler = new SecurityContextLogoutHandler();
        securityContextLogoutHandler.logout(request, response, null);
    }
}
