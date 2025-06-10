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
    @Mapping(source = "userId", target = "user.id")
    @Mapping(source = "requestId", target = "request.id")
    @Mapping(target = "createDate", ignore = true)
    LikeEntity dtoToEntity(LikeDto dto);

    @Mapping(source = "userId", target = "user.id")
    @Mapping(source = "requestId", target = "request.id")
    @Mapping(target = "createDate", ignore = true)
    List<LikeEntity> listDtoToEntity(List<LikeDto> dtos);

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "request.id", target = "requestId")
    @Mapping(target = "createDate", source = "createDate")
    LikeDto entityToDto(LikeEntity entity);

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "request.id", target = "requestId")
    @Mapping(target = "createDate", source = "createDate")
    List<LikeDto> listEntityToDto(List<LikeEntity> entity);
}