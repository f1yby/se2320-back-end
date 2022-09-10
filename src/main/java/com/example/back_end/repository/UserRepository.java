package com.example.back_end.repository;

import com.example.back_end.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {
    @Override
    Optional<UserEntity> findById(Integer integer);

    Optional<UserEntity> findByName(String name);
}
