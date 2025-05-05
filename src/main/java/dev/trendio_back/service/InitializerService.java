package dev.trendio_back.service;

import dev.trendio_back.dto.RequestDto;
import dev.trendio_back.dto.TagDto;
import dev.trendio_back.dto.auth.SignInRequest;
import dev.trendio_back.service.auth.JwtUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
@RequiredArgsConstructor
public class InitializerService {

    private final JwtUserDetailsService userDetailsService;
    private final RequestService requestService;
    private final TagService tagService;

    public void initial() {
        userDetailsService.createUser(SignInRequest.builder()
                .username("user1")
                .password("1234")
                .build());

        TagDto tag1 = tagService.create(TagDto.builder()
                .nameTag("test")
                .build());

        List<TagDto> tags = List.of(tag1);

        requestService.create(RequestDto.builder()
                .username("user1")
                .address("hui")
                .tags(tags)
                .latitude(BigDecimal.valueOf(51.662856))
                .longitude(BigDecimal.valueOf(39.1979146))
                .build());
    }
}
