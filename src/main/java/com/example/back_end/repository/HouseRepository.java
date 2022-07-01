package com.example.back_end.repository;

import com.example.back_end.entity.HouseEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface HouseRepository extends CrudRepository<HouseEntity, Integer> {
    @Query("select h from HouseEntity h where h.price >= :price1 and h.price <= :price2")
    List<HouseEntity> findByPrice(int price1, int price2);
}
