package com.example.back_end.service;

import com.example.back_end.entity.HouseEntity;
import com.example.back_end.repository.HouseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
@Service
public class HouseEntityService {
    @Autowired
    HouseRepository houseRepository;

    public Page<HouseEntity> getHouseListByPage(List<String> district,
                                                Integer price1, Integer price2, Pageable pageable){
        Specification<HouseEntity> specificationQuery = new Specification<HouseEntity>() {
            @Override
            public Predicate toPredicate(Root<HouseEntity> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicatesList = new ArrayList<>();

                if (price1 != null && price2 != null) {
                    predicatesList.add(
                            criteriaBuilder.and(
                                    criteriaBuilder.between(
                                            root.get("price"), price1, price2)));
                }
                if (district != null) {
                    CriteriaBuilder.In<String> inClause = criteriaBuilder.in(root.get("district"));
                    for (String x : district) {
                        inClause.value(x);
                    }
                    predicatesList.add(
                            criteriaBuilder.and(
                                    criteriaBuilder.and(inClause)));
                    //criteriaBuilder.in(inClause) 报错 目前未知
                }
                Predicate[] p = new Predicate[predicatesList.size()];
                return criteriaBuilder.and(predicatesList.toArray(p));
            }
        };
        return houseRepository.findAll(specificationQuery, pageable);
    }

    public List<HouseEntity> findByPriceBetween(int price1,int price2)
    {
        return houseRepository.findByPriceBetween(price1, price2);
    }
}
