package dev.trendio_back.dto.mapper;

import dev.trendio_back.dto.UserDto;
import dev.trendio_back.entity.auth.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "enabled", ignore = true)
    @Mapping(target = "passwordEntity", ignore = true)
    @Mapping(target = "avatar", ignore = true)
    UserEntity dtoToEntity(UserDto dto);

    UserDto entityToDto(UserEntity entity);

}
