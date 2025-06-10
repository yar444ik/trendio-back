package dev.trendio_back.dto.mapper;

import dev.trendio_back.dto.CommentDto;
import dev.trendio_back.entity.CommentEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CommentMapper {

    @Mapping(source = "username", target = "user.username")
    @Mapping(source = "userId", target = "user.id")
    @Mapping(source = "requestId", target = "request.id")
    CommentEntity dtoToEntity(CommentDto dto);

    @Mapping(source = "username", target = "user.username")
    @Mapping(source = "userId", target = "user.id")
    @Mapping(source = "requestId", target = "request.id")
    List<CommentEntity> listDtoToEntity(List<CommentDto> dtos);

    @Mapping(source = "user.username", target = "username")
    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "request.id", target = "requestId")
    CommentDto entityToDto(CommentEntity entity);

    @Mapping(source = "user.username", target = "username")
    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "request.id", target = "requestId")
    List<CommentDto> listEntityToDto(List<CommentEntity> entities);
}
