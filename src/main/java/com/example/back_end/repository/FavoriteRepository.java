package com.example.back_end.repository;

import com.example.back_end.entity.FavoriteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FavoriteRepository extends JpaRepository<FavoriteEntity, Integer> {
    @Override
    void deleteById(Integer integer);
}
