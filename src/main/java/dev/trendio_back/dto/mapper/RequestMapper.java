package dev.trendio_back.dto.mapper;

import dev.trendio_back.dto.RequestDto;
import dev.trendio_back.entity.RequestEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface RequestMapper {

    @Mapping(target = "id", ignore = true)
    RequestEntity dtoToEntity(RequestDto dto);

    RequestDto entityToDto(RequestEntity entity);
}
