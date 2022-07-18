package com.example.back_end.repository;

import com.example.back_end.entity.HouseEntity;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

@Repository
public interface HouseRepository extends
        PagingAndSortingRepository<HouseEntity, String>,
        JpaSpecificationExecutor<HouseEntity> {
    //@Query("select h from HouseEntity h where h.price >= :price1 and h.price <= :price2")
    List<HouseEntity> findByPriceBetween(int price1, int price2);

    Optional<HouseEntity> findById(String id);

    @Query(value = "SELECT * FROM zlm.house WHERE MATCH(title, location, residential, layout) AGAINST (?1 IN BOOLEAN MODE)", nativeQuery = true)
    Page<HouseEntity> searchKeyword(String keyword, Pageable pageable);

}
