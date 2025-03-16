package dev.trendio_back.dto;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@Builder
@ToString
public class TagDto {
    private Long id;
    private String nameTag;
    private String icon;
    private List<RequestDto> requests;
}
