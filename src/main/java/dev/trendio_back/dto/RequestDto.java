package dev.trendio_back.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@ToString
@Getter @Setter
public class RequestDto {
    private Long id;

    private Long userId;
    private String username;

    private String address;
    private LocalDateTime createDate;

    private double latitude;
    private double longitude;

    private List<TagDto> tags;
    private List<LikeDto> likes;

    private String headerRequest;
    private String textRequest;


    private List<CommentDto> comments;
}