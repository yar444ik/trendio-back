package dev.trendio_back.repository;

import dev.trendio_back.entity.RequestEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface RequestRepository extends JpaRepository<RequestEntity, Long> {
    Page<RequestEntity> findById(Long requestId, Pageable pageable);
}
