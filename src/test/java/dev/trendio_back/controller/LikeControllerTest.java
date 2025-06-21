package dev.trendio_back.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.trendio_back.dto.LikeDto;
import dev.trendio_back.dto.auth.AuthUser;
import dev.trendio_back.exception.NotFoundException;
import dev.trendio_back.service.LikeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Testcontainers
public class LikeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LikeService likeService;

    private LikeDto testLike;
    private final Long requestId = 1L;
    private final Long likeId = 1L;
    private final String username = "testUser";
    private AuthUser authUser;

    @BeforeEach
    void setUp() {
        authUser = AuthUser.builder()
                .username(username)
                .password("password")
                .enabled(true)
                .authorities(Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")))
                .build();

        testLike = LikeDto.builder()
                .id(likeId)
                .requestId(requestId)
                .userId(1L)
                .createDate(LocalDateTime.now())
                .build();

        when(likeService.likeRequest(eq(username), eq(requestId)))
                .thenReturn(testLike);

        when(likeService.getLikesForRequest(eq(requestId)))
                .thenReturn(List.of(testLike));

        doNothing().when(likeService).unlikeRequest(eq(username), eq(requestId));
    }

    @Test
    void likeRequest_ShouldCreateLike() throws Exception {
        mockMvc.perform(post("/api/likes/{requestId}", requestId)
                        .with(user(authUser)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(likeId))
                .andExpect(jsonPath("$.requestId").value(requestId));

        verify(likeService).likeRequest(username, requestId);
    }

    @Test
    void unlikeRequest_ShouldDeleteLike() throws Exception {
        mockMvc.perform(delete("/api/likes/{requestId}", requestId)
                        .with(user(authUser)))
                .andExpect(status().isOk());

        verify(likeService).unlikeRequest(username, requestId);
    }

    @Test
    void getLikesForRequest_ShouldReturnLikes() throws Exception {
        mockMvc.perform(get("/api/likes/request/{requestId}", requestId))
                //todo решить вопрос с AuthUser
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(likeId))
                .andExpect(jsonPath("$[0].requestId").value(requestId));

        verify(likeService).getLikesForRequest(requestId);
    }

    @Test
    void getLikesForRequest_ShouldReturnEmptyList_WhenNoLikes() throws Exception {
        when(likeService.getLikesForRequest(eq(requestId)))
                .thenReturn(List.of());

        mockMvc.perform(get("/api/likes/request/{requestId}", requestId)
                        .with(user(authUser)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    void unlikeRequest_ShouldReturnNotFound_WhenLikeNotExists() throws Exception {
        doThrow(new NotFoundException("Like not found"))
                .when(likeService).unlikeRequest(username, 999L);

        mockMvc.perform(delete("/api/likes/{requestId}", 999L)
                        .with(user(authUser)))
                .andExpect(status().isNotFound());
    }
}