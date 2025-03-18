package dev.trendio_back.repository;

import dev.trendio_back.entity.RequestEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RequestRepository extends JpaRepository<RequestEntity, Long> {
    Page<RequestEntity> findAll(Pageable pageable);
    Page<RequestEntity> findByHeaderRequest(String headerRequest, Pageable pageable);
    Optional<RequestEntity> findById(Long id);
}
