package dev.trendio_back.dto;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@ToString
public class LikeDto {
    private Long id;
    private LocalDateTime createDate;
    private Long requestId;
    private Long userId;
}
