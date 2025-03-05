package dev.trendio_back.dto;

import dev.trendio_back.entity.ImageEntity;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@Builder
@ToString
public class UserDto {
    private Long id;

    private String username;
    private String role;
}
