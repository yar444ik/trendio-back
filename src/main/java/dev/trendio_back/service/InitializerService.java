package dev.trendio_back.service;

import dev.trendio_back.dto.auth.SignInRequest;
import dev.trendio_back.service.auth.JwtUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class InitializerService {

    private final JwtUserDetailsService userDetailsService;

    public void initial() {
        userDetailsService.createUser(SignInRequest.builder()
                        .username("user1")
                        .password("1234")
                .build());
    }
}
