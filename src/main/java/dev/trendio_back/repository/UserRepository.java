package dev.trendio_back.repository;

import dev.trendio_back.entity.auth.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    @Query("SELECT u FROM UserEntity u JOIN FETCH u.passwordEntity WHERE u.username = :name")
    Optional<UserEntity> findByUsername(String name);

    Optional<UserEntity> findById(Long id);
}
