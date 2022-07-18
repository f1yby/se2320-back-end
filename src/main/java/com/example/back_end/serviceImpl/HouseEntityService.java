package com.example.back_end.serviceImpl;

import ch.hsr.geohash.GeoHash;
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
import java.util.stream.Stream;

@Service
public class HouseEntityService {
    @Autowired
    HouseRepository houseRepository;

    public Integer updateHouseGeocode(Integer len) {
        Iterable<HouseEntity> houses = houseRepository.findAll();
        for (HouseEntity house : houses) {
            GeoHash geoHash = GeoHash.withCharacterPrecision(Double.parseDouble(house.getLatitude()), Double.parseDouble(house.getLongitude()), len);
            System.out.println(geoHash.toBase32());
            house.setGeocode(geoHash.toBase32());

        }
        houseRepository.saveAll(houses);
        System.out.println("updateHouseGeocode finish");
        return 0;
    }

    public Page<HouseEntity> getHouseListByPage(List<String> district,
                                                Integer price1, Integer price2,
                                                List<Integer> rentType,
                                                List<Integer> rooms,
                                                Integer metro_line,
                                                List<String> metro_station,
                                                String keywords,
                                                Pageable pageable) {
        Specification<HouseEntity> specificationQuery = (root, criteriaQuery, criteriaBuilder) -> {
//            System.out.println("district=" + district);
//            System.out.println("price1,price1=[" + price1 + "," + price2 + "]");
//            System.out.println("rentType=" + rentType);
//            System.out.println("rooms=" + rooms);
//            System.out.println("metro_line=" + metro_line + "\t metro_station" + metro_station);
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
            if (keywords != null) {
                predicatesList.add(
                        criteriaBuilder.or(
                        criteriaBuilder.like(root.get("title"), "%" + keywords + "%"),
                        criteriaBuilder.like(root.get("location"), "%" + keywords + "%"),
                        criteriaBuilder.like(root.get("residential"), "%" + keywords + "%"),
                        criteriaBuilder.like(root.get("layout"), "%" + keywords + "%")
                        )
                );
            }
            // and,or 方法会把参数中的predicate组合起来,复杂条件可以互相嵌套组合
            return criteriaBuilder.and(predicatesList.toArray(new Predicate[0]));
        };
        return houseRepository.findAll(specificationQuery, pageable);
    }

    public Page<HouseEntity> getNearbyHouseByPage(int len, double userLng, double userLat, Pageable pageable) {

        // updateHouseGeocode(len);


        Specification<HouseEntity> specificationQuery = (root, criteriaQuery, criteriaBuilder) -> {

            List<Predicate> predicatesList = new ArrayList<>();

            //1.根据要求的范围，确定geoHash码的精度，获取到当前用户坐标的geoHash码
            GeoHash geoHash = GeoHash.withCharacterPrecision(userLat, userLng, len);
            //2.获取到用户周边8个方位的geoHash码
            GeoHash[] adjacent = geoHash.getAdjacent();

            System.out.println(geoHash.toBase32());

            predicatesList.add(criteriaBuilder.equal(root.get("geocode"), geoHash.toBase32()));
            Stream.of(adjacent).forEach(a ->
                    predicatesList.add(criteriaBuilder.equal(root.get("geocode"), a.toBase32())));


//            //4.过滤超出距离的
//            users = users.stream()
//                    .filter(a ->getDistance(a.getLongitude(),a.getLatitude(),userLng,userLat)<= distance)
//                    .collect(Collectors.toList());


            // and,or 方法会把参数中的predicate组合起来,复杂条件可以互相嵌套组合
            return criteriaBuilder.or(predicatesList.toArray(new Predicate[0]));
        };
        return houseRepository.findAll(specificationQuery, pageable);

    }

    public List<HouseEntity> getAllHouse(List<String> district,
                                         Integer price1, Integer price2,
                                         List<Integer> rentType,
                                         List<Integer> rooms,
                                         Integer metro_line,
                                         List<String> metro_station) {
        Specification<HouseEntity> specificationQuery = (root, criteriaQuery, criteriaBuilder) -> {
//            System.out.println("district=" + district);
//            System.out.println("price1,price1=[" + price1 + "," + price2 + "]");
//            System.out.println("rentType=" + rentType);
//            System.out.println("rooms=" + rooms);
//            System.out.println("metro_line=" + metro_line + "\t metro_station" + metro_station);
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
        return houseRepository.findAll(specificationQuery);
    }

    public List<HouseEntity> findByPriceBetween(int price1, int price2) {
        return houseRepository.findByPriceBetween(price1, price2);
    }

    public Page<HouseEntity> searchKeyword(String keyword, Pageable pageable) {
        return houseRepository.searchKeyword(keyword, pageable);
    }
}
