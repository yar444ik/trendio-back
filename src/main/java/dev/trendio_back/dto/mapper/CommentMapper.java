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
    CommentEntity CommentDtoToEntity(CommentDto comment);

    List<CommentEntity> CommentDtoToEntity(List<CommentDto> comment);
}
