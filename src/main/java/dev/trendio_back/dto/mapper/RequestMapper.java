package dev.trendio_back.dto.mapper;

import dev.trendio_back.dto.RequestDto;
import dev.trendio_back.dto.UserDto;
import dev.trendio_back.entity.RequestEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Mapper(componentModel = "spring")
public interface RequestMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createDate", source = "createDate")
    @Mapping(target = "author", source = "username")
    @Mapping(target = "latitude", source = "latitude")
    @Mapping(target = "longtude", source = "longtude")
    RequestEntity dtoToEntity(RequestDto dto, LocalDateTime createDate, UserDto author, BigDecimal latitude, BigDecimal longitude);

    @Mapping(target = "author", source = "author.username")
    RequestDto entityToDto(RequestEntity entity);

}
