package dev.trendio_back.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import dev.trendio_back.entity.LikeEntity;
import dev.trendio_back.entity.TagEntity;
import lombok.*;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@ToString
@Getter @Setter
public class RequestDto {
    private String username;

    private String address;
    private LocalDateTime createDate;

    private BigDecimal latitude;
    private BigDecimal longitude;

    private List<TagDto> tags;
    private List<LikeDto> likes;

    private String headerRequest;
    private String textRequest;


    private List<CommentDto> comments;
}