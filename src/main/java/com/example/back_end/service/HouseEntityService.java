package com.example.back_end.service;

import com.example.back_end.entity.HouseEntity;
import com.example.back_end.repository.HouseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

@Service
public class HouseEntityService {
    @Autowired
    HouseRepository houseRepository;

    public Page<HouseEntity> getHouseListByPage(List<String> district,
                                                Integer price1, Integer price2,
                                                List<Integer> rentType,
                                                List<Integer> rooms,
                                                Integer metro_line,
                                                List<String> metro_station,
                                                Pageable pageable) {
        Specification<HouseEntity> specificationQuery = (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicatesList = new ArrayList<>();

            if (price1 != null && price2 != null) {
                predicatesList.add(criteriaBuilder.between(root.get("price"), price1, price2));
            }
            if (district != null && district.size() > 0) {
                CriteriaBuilder.In<String> inClause = criteriaBuilder.in(root.get("district"));
                district.forEach(inClause::value);
                predicatesList.add(inClause);
            }
            if (rooms != null && rooms.size() > 0) {
                List<Predicate> predicatesRooms = new ArrayList<>();
                CriteriaBuilder.In<Integer> inClause = criteriaBuilder.in(root.get("shi"));
                rooms.forEach(inClause::value);
                predicatesRooms.add(inClause);
                if (rooms.contains(4)) {
                    //四室及以上
                    predicatesRooms.add(criteriaBuilder.greaterThan(root.get("shi"), 4));
                }
                predicatesList.add(criteriaBuilder.or(predicatesRooms.toArray(new Predicate[0])));
            }
            if (rentType != null && rentType.size() > 0) {
                CriteriaBuilder.In<Integer> inClause = criteriaBuilder.in(root.get("rentType"));
                rentType.forEach(inClause::value);
                predicatesList.add(inClause);
            }
            if (metro_line != null) {
                predicatesList.add(criteriaBuilder.equal(root.get("metroLine"), metro_line));
                if (metro_station != null && metro_station.size() > 0) {
                    CriteriaBuilder.In<String> inClause = criteriaBuilder.in(root.get("metroStation"));
                    metro_station.forEach(inClause::value);
                    predicatesList.add(inClause);
                }
            }
            // and,or 方法会把参数中的predicate组合起来,复杂条件可以互相嵌套组合
            return criteriaBuilder.and(predicatesList.toArray(new Predicate[0]));
        };
        return houseRepository.findAll(specificationQuery, pageable);
    }

    public List<HouseEntity> findByPriceBetween(int price1, int price2) {
        return houseRepository.findByPriceBetween(price1, price2);
    }
}
