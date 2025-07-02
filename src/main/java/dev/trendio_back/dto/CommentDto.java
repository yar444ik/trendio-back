package dev.trendio_back.dto;

import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CommentDto {
    private Long id;
    private Long userId;
    private Long requestId;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;
    private String username;
    private String text;
}
