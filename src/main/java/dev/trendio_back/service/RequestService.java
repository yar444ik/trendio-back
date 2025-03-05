package dev.trendio_back.service;

import dev.trendio_back.dto.RequestDto;
import dev.trendio_back.dto.UserDto;
import dev.trendio_back.dto.mapper.RequestMapper;
import dev.trendio_back.dto.mapper.UserMapper;
import dev.trendio_back.entity.RequestEntity;
import dev.trendio_back.entity.auth.UserEntity;
import dev.trendio_back.repository.RequestRepository;
import dev.trendio_back.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class RequestService {
    private final RequestRepository requestRepository;
    private final UserRepository userRepository;

    private final RequestMapper requestMapper;
    private final UserMapper userMapper;

    public Page<RequestDto> findAll(int page, int size, String sortField, String sortDirection) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortField);
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<RequestEntity> requestEntities = requestRepository.findAll(pageable);
        return requestEntities.map(requestMapper::entityToDto);
    }

    public RequestDto create(RequestDto dto) {
        Optional<UserEntity> user = userRepository.findByUsername(dto.getAuthor());
        if (user.isEmpty()) {
            throw new EntityNotFoundException("User with ID " + dto.getAuthor() + " not found");
        }

        UserDto userDto = userMapper.entityToDto(user.get());

        RequestEntity entity = requestMapper.dtoToEntity(dto, LocalDateTime.now(), userDto, dto.getLatitude(), dto.getLongitude());

        return requestMapper.entityToDto(requestRepository.save(entity));
    }
}
