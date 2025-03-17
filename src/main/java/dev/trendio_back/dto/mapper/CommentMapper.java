package dev.trendio_back.dto.mapper;

import dev.trendio_back.dto.CommentDto;
import dev.trendio_back.entity.CommentEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CommentMapper {

    @Mapping(target = "user.id", source = "username_id")
    @Mapping(source = "request_id", target = "request.id")
    CommentEntity dtoToEntity(CommentDto dto);

    @Mapping(target = "user.id", source = "username_id")
    @Mapping(source = "request_id", target = "request.id")
    List<CommentEntity> listDtoToEntity(List<CommentDto> dtos);

    @Mapping(target = "username_id", source = "user.id")
    @Mapping(source = "request.id", target = "request_id")
    CommentDto entityToDto(CommentEntity entity);

    @Mapping(target = "username_id", source = "user.id")
    @Mapping(source = "request.id", target = "request_id")
    List<CommentDto> listEntityToDto(List<CommentEntity> entities);
}
