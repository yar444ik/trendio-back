package dev.trendio_back.dto;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@Builder
@ToString
public class ImageDto {
    private Long id;
    private String imageUrl;
}
