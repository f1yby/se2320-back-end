package com.example.back_end;

import com.example.back_end.entity.HouseEntity;
import com.example.back_end.serviceImpl.HouseEntityService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class HouseServiceTest {

    @Autowired
    HouseEntityService houseEntityService;

    Integer dataLength = 5358;

    @Test
    public void test() {

        // Get all
        assertEquals(houseEntityService.getAllHouse(null, null, null, null, null, null, null).size(), dataLength);

        // Get specific district
        List<String> district = new ArrayList<>();
        district.add("浦东");
        district.add("闵行");
        List<HouseEntity> houseEntities = new ArrayList<>(houseEntityService.getAllHouse(district, null, null, null, null, null, null));
        for (HouseEntity house : houseEntities) {
            assertTrue(Objects.equals(house.getDistrict(), "浦东") || Objects.equals(house.getDistrict(), "闵行"));
        }

        // Get price
        houseEntities.clear();
        Integer price1 = 1000;
        Integer price2 = 4000;
        houseEntities.addAll(houseEntityService.getAllHouse(null, price1, price2, null, null, null, null));
        for (HouseEntity house : houseEntities) {
            Integer price = house.getPrice();
            assertTrue(price >= price1 && price <= price2);
        }

        // Get rentType
        houseEntities.clear();
        List<Integer> rentType = new ArrayList<>();
        rentType.add(0,2);
        houseEntities.addAll(houseEntityService.getAllHouse(null, null, null, rentType, null, null, null));
        for (HouseEntity house : houseEntities) {
            Integer rt = house.getRentType();
            assertTrue(rentType.contains(rt));
        }

    }

}
