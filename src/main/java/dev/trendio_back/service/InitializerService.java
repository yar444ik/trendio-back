package dev.trendio_back.service;

import dev.trendio_back.dto.RequestDto;
import dev.trendio_back.dto.TagDto;
import dev.trendio_back.dto.auth.SignInRequest;
import dev.trendio_back.entity.auth.UserEntity;
import dev.trendio_back.repository.UserRepository;
import dev.trendio_back.service.auth.JwtUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class InitializerService {

    private final JwtUserDetailsService userDetailsService;
    private final RequestService requestService;
    private final TagService tagService;
    private final UserRepository userRepository;


    public void initial() {
        userDetailsService.createUser(SignInRequest.builder()
                .username("user1")
                .password("1234")
                .build());

        TagDto tag1 = tagService.create(TagDto.builder()
                .nameTag("test")
                .build());

        List<TagDto> tags = List.of(tag1);

        UserEntity user = userRepository.findByUsername("user1").orElseThrow();

        requestService.create(RequestDto.builder()
                .username(user.getUsername())
                .address("plehanovskaya 11")
                .tags(tags)
                .latitude(51.662856)
                .longitude(39.1979146)
                .build());
    }
}
