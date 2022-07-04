package com.example.back_end.repository;

import com.example.back_end.entity.HouseEntity;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface HouseRepository extends
        PagingAndSortingRepository<HouseEntity, String>,
        JpaSpecificationExecutor<HouseEntity> {
    //@Query("select h from HouseEntity h where h.price >= :price1 and h.price <= :price2")
    List<HouseEntity> findByPriceBetween(int price1, int price2);
}
