package dev.trendio_back.dto;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@Builder
@ToString
public class UserDto {
    private Long id;

    private String username;
    private String role;

    private String avatar;

    private List<LikeDto> likes;
    private List<CommentDto> comments;
    private List<RequestDto> requests;
}
