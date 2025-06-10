package dev.trendio_back.controller;

import dev.trendio_back.dto.auth.JwtRequest;
import dev.trendio_back.dto.auth.JwtResponse;
import dev.trendio_back.dto.auth.SignInRequest;
import dev.trendio_back.dto.auth.SignInResponse;
import dev.trendio_back.service.auth.JwtUserDetailsService;
import dev.trendio_back.service.auth.TokenManager;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api")
@AllArgsConstructor
public class AuthenticationController {

    private final JwtUserDetailsService userDetailsService;
    private final AuthenticationManager authenticationManager;
    private final TokenManager tokenManager;

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> createToken(@RequestBody JwtRequest request) throws DisabledException, BadCredentialsException {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
            );
        }
        catch (DisabledException e) {
            throw new DisabledException(String.format("%s", e.getMessage()));
        }
        catch (BadCredentialsException e) {
            throw new BadCredentialsException(String.format("%s", e.getMessage()));
        }

        final UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());
        return new ResponseEntity<>(new JwtResponse(tokenManager.generateJwtToken(userDetails)), HttpStatus.CREATED);
    }

    @PostMapping("/register")
    public ResponseEntity<SignInResponse> register(@RequestBody SignInRequest request){
        return new ResponseEntity<>(userDetailsService.createUser(request), HttpStatus.CREATED);
    }
}
