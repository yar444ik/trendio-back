package dev.trendio_back.dto.mapper;

import dev.trendio_back.dto.ImageDto;
import dev.trendio_back.entity.ImageEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ImageMapper {
    
    ImageEntity dtoToEntity(ImageDto dto);
    
    List<ImageEntity> listDtoToEntity(List<ImageDto> dtos);
    
    ImageDto entityToDto(ImageEntity entity);
    
    List<ImageDto> listEntityToDto(List<ImageEntity> entities);
}

