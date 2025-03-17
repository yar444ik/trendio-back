package dev.trendio_back.dto.mapper;

import dev.trendio_back.dto.LikeDto;
import dev.trendio_back.dto.TagDto;
import dev.trendio_back.entity.LikeEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface LikeMapper {
    @Mapping(source = "username", target = "username.id")
    @Mapping(source = "request", target = "request.id")
    LikeEntity dtoToEntity(LikeDto dto);

    @Mapping(source = "username", target = "username.id")
    @Mapping(source = "request", target = "request.id")
    List<LikeEntity> listDtoToEntity(List<LikeDto> dtos);

    @Mapping(source = "username.id", target = "username")
    @Mapping(source = "request.id", target = "request")
    LikeDto entityToDto(LikeEntity entity);

    @Mapping(source = "username.id", target = "username")
    @Mapping(source = "request.id", target = "request")
    List<LikeDto> listEntityToDto(List<LikeEntity> entity);
}