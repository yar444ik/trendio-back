package dev.trendio_back.dto.mapper;

import dev.trendio_back.dto.RequestDto;
import dev.trendio_back.entity.RequestEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = {TagMapper.class,LikeMapper.class,CommentMapper.class})
public interface RequestMapper {

    @Mapping(target = "user.username", source = "username")
    RequestEntity dtoToEntity(RequestDto dto);

    @Mapping(target = "user.username", source = "username")
    List<RequestEntity> listEntityToDto(List<RequestDto> dtos);

    @Mapping(target = "username", source = "user.username")
    RequestDto entityToDto(RequestEntity entity);

    @Mapping(target = "username", source = "user.username")
    List<RequestDto> entityToDto(List<RequestEntity> entities);
}
