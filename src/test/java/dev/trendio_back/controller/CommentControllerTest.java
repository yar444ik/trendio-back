package dev.trendio_back.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.trendio_back.dto.CommentDto;
import dev.trendio_back.service.CommentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Testcontainers
public class CommentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CommentService commentService;

    private CommentDto testComment;
    private final Long commentId = 1L;
    private final String authorizedUser = "user1";
    private final String unauthorizedUser = "unauthorizedUser";

    @BeforeEach
    void setUp() {
        testComment = CommentDto.builder()
                .id(commentId)
                .userId(1L)
                .requestId(1L)
                .createDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .username(authorizedUser)
                .text("Test comment")
                .build();

        when(commentService.findAllForRequest(anyLong()))
                .thenReturn(List.of(testComment));

        when(commentService.create(any(CommentDto.class), any(String.class)))
                .thenReturn(testComment);

        when(commentService.update(any(CommentDto.class), any(String.class), anyLong()))
                .thenAnswer(invocation -> {
                    CommentDto dto = invocation.getArgument(0);
                    return dto;
                });

        when(commentService.delete(anyLong()))
                .thenReturn(commentId);

        when(commentService.isCreator(authorizedUser, commentId))
                .thenReturn(true);

        when(commentService.isCreator(unauthorizedUser, commentId))
                .thenReturn(false);
    }

    @Test
    @WithMockUser(username = "user1")
    void updateComment_ShouldUpdateComment() throws Exception {
        String updatedText = "Updated comment";
        testComment.setText(updatedText);

        mockMvc.perform(put("/api/comments/{id}", commentId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testComment)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.text").value(updatedText))
                .andExpect(jsonPath("$.id").value(commentId));
    }

    @Test
    @WithMockUser(username = "user1")
    void getCommentsForRequest_ShouldReturnComments() throws Exception {
        mockMvc.perform(get("/api/comments/request/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].text").value("Test comment"));
    }

    @Test
    @WithMockUser(username = "user1")
    void addComment_ShouldCreateComment() throws Exception {
        mockMvc.perform(post("/api/comments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testComment)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.text").value("Test comment"));
    }

    @Test
    @WithMockUser(username = "user1")
    void deleteComment_ShouldDeleteComment() throws Exception {
        mockMvc.perform(delete("/api/comments/1"))
                .andExpect(status().isOk())
                .andExpect(content().string(commentId.toString()));
    }

    @Test
    @WithMockUser(username = "unauthorizedUser")
    void updateComment_WhenNotCreator_ShouldForbid() throws Exception {
        mockMvc.perform(put("/api/comments/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testComment)))
                .andExpect(status().isForbidden());
    }
}