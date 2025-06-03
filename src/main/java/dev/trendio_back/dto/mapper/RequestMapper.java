package dev.trendio_back.dto.mapper;

import dev.trendio_back.dto.RequestDto;
import dev.trendio_back.entity.RequestEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = {TagMapper.class,LikeMapper.class,CommentMapper.class})
public interface RequestMapper {

    @Mapping(target = "user.username", source = "username")
    @Mapping(target = "tags", ignore = true)
    RequestEntity dtoToEntity(RequestDto dto);

    @Mapping(target = "user.username", source = "username")
    @Mapping(target = "tags", ignore = true)
    List<RequestEntity> listDtoToEntity(List<RequestDto> dtos);

    @Mapping(target = "username", source = "user.username")
    @Mapping(target = "tags", ignore = true)
    RequestDto entityToDto(RequestEntity entity);

    @Mapping(target = "username", source = "user.username")
    @Mapping(target = "tags", ignore = true)
    List<RequestDto> listEntityToDto(List<RequestEntity> entities);
}
