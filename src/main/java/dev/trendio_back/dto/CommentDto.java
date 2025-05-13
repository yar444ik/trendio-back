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
    //todo camel case everywhere
    private Long username_id;
    private Long request_id;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;
    private String comment;
}
