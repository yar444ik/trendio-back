package dev.trendio_back.service;

import dev.trendio_back.dto.RequestDto;
import dev.trendio_back.dto.auth.SignInRequest;
import dev.trendio_back.service.auth.JwtUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
public class InitializerService {

    private final JwtUserDetailsService userDetailsService;
    private final RequestService requestService;

    public void initial() {
        userDetailsService.createUser(SignInRequest.builder()
                        .username("user1")
                        .password("1234")
                .build());

        requestService.create(RequestDto.builder()
                .username("user1")
                .address("hui")
                .latitude(BigDecimal.valueOf(51.662856))
                .longitude(BigDecimal.valueOf(39.1979146))
                .build());
    }
}
