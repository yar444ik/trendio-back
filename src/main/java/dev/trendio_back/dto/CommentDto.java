package dev.trendio_back.dto;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;

@Data
@Builder
@ToString
public class CommentDto {
    private Long id;
    private String username;
    private String request;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;
    private String comment;
}
