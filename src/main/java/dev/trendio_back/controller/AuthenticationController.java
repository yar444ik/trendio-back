package dev.trendio_back.controller;

import dev.trendio_back.dto.auth.JwtRequest;
import dev.trendio_back.dto.auth.JwtResponse;
import dev.trendio_back.dto.auth.SignInRequest;
import dev.trendio_back.service.auth.JwtUserDetailsService;
import dev.trendio_back.service.auth.TokenManager;
import lombok.AllArgsConstructor;
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
    private final JwtUserDetailsService jwtUserDetailsService;

    @PostMapping("/login")
    public JwtResponse createToken(@RequestBody JwtRequest request) throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
            );
        }
        catch (DisabledException e) {
            //todo controller advice, return 403 or 401
            throw new Exception("USER_DISABLED", e);
        }
        catch (BadCredentialsException e) {
            //todo the same
            throw new Exception("INVALID_CREDENTIALS", e);
        }

        //todo request to db two times, check it and handle
        final UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());
        return new JwtResponse(tokenManager.generateJwtToken(userDetails));
    }

    @PostMapping("/register")
    public void register(@RequestBody SignInRequest request){
        jwtUserDetailsService.createUser(request);
    }
}
