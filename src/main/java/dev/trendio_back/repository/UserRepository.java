package dev.trendio_back.repository;

import dev.trendio_back.entity.auth.UserEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<UserEntity, Long> {

    @Query("SELECT u FROM UserEntity u JOIN FETCH u.passwordEntity WHERE u.username = :name")
    Optional<UserEntity> findByUsername(String name);
}
