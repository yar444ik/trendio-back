package dev.trendio_back.dto.mapper;

import dev.trendio_back.dto.CommentDto;
import dev.trendio_back.entity.CommentEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,uses = {RequestMapper.class,UserMapper.class})
public interface CommentMapper {

    @Mapping(source = "username", target = "username.id")
    @Mapping(source = "request", target = "request.id")
    CommentEntity dtoToEntity(CommentDto dto);

    @Mapping(source = "username", target = "username.id")
    @Mapping(source = "request", target = "request.id")
    List<CommentEntity> listDtoToEntity(List<CommentDto> dtos);

    @Mapping(source = "username.id", target = "username")
    @Mapping(source = "request.id", target = "request")
    CommentDto entityToDto(CommentEntity entity);

    @Mapping(source = "username.id", target = "username")
    @Mapping(source = "request.id", target = "request")
    List<CommentDto> listEntityToDto(List<CommentEntity> entities);
}
