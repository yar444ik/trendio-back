package dev.trendio_back.dto.mapper;

import dev.trendio_back.dto.RequestDto;
import dev.trendio_back.dto.UserDto;
import dev.trendio_back.dto.auth.Role;
import dev.trendio_back.entity.RequestEntity;
import dev.trendio_back.entity.auth.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "username", source = "username")
    @Mapping(target = "role", source = "role")
    @Mapping(target = "enabled", ignore = true)
    @Mapping(target = "passwordEntity", ignore = true)
    @Mapping(target = "avatar", ignore = true)
    UserEntity dtoToEntity(UserDto dto, String username, String role);

    UserDto entityToDto(UserEntity entity);

}
