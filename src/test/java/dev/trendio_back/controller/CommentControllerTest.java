package dev.trendio_back.controller;

import dev.trendio_back.dto.CommentDto;
import dev.trendio_back.dto.auth.AuthUser;
import dev.trendio_back.service.OldCommentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class CommentControllerTest {

    @Mock
    private OldCommentService oldCommentService;

    @InjectMocks
    private CommentController commentController;

    private MockMvc mockMvc;

    private AuthUser authUser;
    private CommentDto commentDto;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(commentController).build();

        authUser = AuthUser.builder()
                .id(1L)
                .username("testUser")
                .password("password")
                .enabled(true)
                .authorities(Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")))
                .build();

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                authUser, null, authUser.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        commentDto = new CommentDto();
        commentDto.setId(1L);
        commentDto.setText("Test comment");
        commentDto.setUserId(1L);
        commentDto.setUsername("testUser");
        commentDto.setCreateDate(LocalDateTime.now());
    }

    @Test
    void getCommentsForRequest_ShouldReturnComments() throws Exception {
        List<CommentDto> comments = Collections.singletonList(commentDto);
        when(oldCommentService.findAllForRequest(anyLong())).thenReturn(comments);

        mockMvc.perform(get("/api/comments/request/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].text").value("Test comment"));

        verify(oldCommentService).findAllForRequest(1L);
    }

    @Test
    void addComment_ShouldReturnCreatedComment() throws Exception {
        when(oldCommentService.create(any(CommentDto.class), any(AuthUser.class))).thenReturn(commentDto);

        mockMvc.perform(post("/api/comments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"text\":\"Test comment\",\"requestId\":1}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.text").value("Test comment"));

        verify(oldCommentService).create(any(CommentDto.class), any(AuthUser.class));
    }

    @Test
    void updateComment_ShouldReturnUpdatedComment() throws Exception {
        when(oldCommentService.update(any(CommentDto.class), anyLong(), anyLong())).thenReturn(commentDto);

        mockMvc.perform(put("/api/comments/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"text\":\"Updated comment\",\"requestId\":1}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.text").value("Test comment"));

        verify(oldCommentService).update(any(CommentDto.class), anyLong(), eq(1L));
    }

    @Test
    void deleteComment_ShouldReturnDeletedCommentId() throws Exception {
        when(oldCommentService.delete(anyLong())).thenReturn(1L);

        mockMvc.perform(delete("/api/comments/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("1"));

        verify(oldCommentService).delete(1L);
    }
}