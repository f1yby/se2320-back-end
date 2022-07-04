package com.example.back_end.controller;

import com.example.back_end.entity.HouseEntity;
import com.example.back_end.repository.HouseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(path = "/house")
public class HouseController {

    @Autowired
    private HouseRepository houseRepository;


    @PostMapping("/search/price")
    public List<HouseEntity> getHouseByPrice(@RequestParam("price1") int price1,
                                             @RequestParam("price2") int price2) {
        return houseRepository.findByPriceBetween(price1, price2);
    }

    @Resource
    private EntityManager entityManager;

    @PostMapping("/search")
    public List<HouseEntity> getHouse(@RequestParam(required = false) List<String> district,
                                      Integer price1, Integer price2) {
        System.out.println(district);
        System.out.println(price1);
        System.out.println(price2);
        //创建CriteriaBuilder安全查询工厂
        //CriteriaBuilder是一个工厂对象,安全查询的开始.用于构建JPA安全查询.
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        //创建CriteriaQuery安全查询主语句
        //CriteriaQuery对象必须在实体类型或嵌入式类型上的Criteria 查询上起作用。
        CriteriaQuery<HouseEntity> query = criteriaBuilder.createQuery(HouseEntity.class);
        //Root 定义查询的From子句中能出现的类型
        Root<HouseEntity> houseEntityRootRoot = query.from(HouseEntity.class);
        //Predicate 过滤条件 构建where字句可能的各种条件
        //这里用List存放多种查询条件,实现动态查询
        List<Predicate> predicatesList = new ArrayList<>();

        if (price1 != null && price2 != null) {
            predicatesList.add(
                    criteriaBuilder.and(
                            criteriaBuilder.between(
                                    houseEntityRootRoot.get("price"), price1, price2)));
        }
        if (district != null) {
            CriteriaBuilder.In<String> inClause = criteriaBuilder.in(houseEntityRootRoot.get("district"));
            for (String x : district) {
                inClause.value(x);
            }
            predicatesList.add(
                    criteriaBuilder.and(
                            criteriaBuilder.and(inClause)));
            //criteriaBuilder.in(inClause) 报错 目前未知
        }

        query.where(predicatesList.toArray(new Predicate[0]));
        TypedQuery<HouseEntity> typedQuery = entityManager.createQuery(query);
        return typedQuery.getResultList();
    }
}


