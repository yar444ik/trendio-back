package dev.trendio_back.dto.mapper;

import dev.trendio_back.dto.CommentDto;
import dev.trendio_back.entity.CommentEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CommentMapper {

    @Mapping(target = "user.id", source = "usernameId")
    @Mapping(source = "requestId", target = "request.id")
    CommentEntity dtoToEntity(CommentDto dto);

    @Mapping(target = "user.id", source = "usernameId")
    @Mapping(source = "requestId", target = "request.id")
    List<CommentEntity> listDtoToEntity(List<CommentDto> dtos);

    @Mapping(target = "usernameId", source = "user.id")
    @Mapping(source = "request.id", target = "requestId")
    CommentDto entityToDto(CommentEntity entity);

    @Mapping(target = "usernameId", source = "user.id")
    @Mapping(source = "request.id", target = "requestId")
    List<CommentDto> listEntityToDto(List<CommentEntity> entities);
}
