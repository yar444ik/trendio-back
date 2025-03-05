package dev.trendio_back.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDateTime;

@Data
@Builder
@ToString
public class RequestDto {
    private Long id;

    private String author;

    private String adress;
    private LocalDateTime createDate;

    private BigDecimal latitude;
    private BigDecimal longitude;

    private String headerRequest;
    private String textRequest;

    //private List<CommentDto> comments;
}