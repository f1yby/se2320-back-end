package com.example.back_end;

import ch.hsr.geohash.GeoHash;
import com.example.back_end.entity.HouseEntity;
import com.example.back_end.serviceImpl.HouseEntityService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class HouseServiceTest {

    @Autowired
    HouseEntityService houseEntityService;

    Integer dataLength = 5355;

    Integer page = 0;
    Integer pageSize = 100;
    Integer len = 5;

    @Test
    public void testGetNearbyHouses() {

        // Get nearby
        Sort sort = Sort.by(Sort.Direction.DESC, "createTime"); //按创建时间排序
        Pageable pageable = PageRequest.of(page, pageSize, sort);

        double lat = 31.23;
        double lng = 121.48;

        //1.根据要求的范围，确定 geoHash 码的精度，获取到当前用户坐标的 geoHash 码
        GeoHash geoHash = GeoHash.withCharacterPrecision(lat, lng, len);
        //2.获取到用户周边8个方位的 geoHash 码
        GeoHash[] adjacent = geoHash.getAdjacent();

        Page<HouseEntity> houses = houseEntityService.getNearbyHouseByPage(len, lng, lat, pageable);
        assertEquals(houses.getSize(), pageSize);
        for (HouseEntity h : houses) {
            boolean same = false;
            for (GeoHash a : adjacent) {
                same = same || Objects.equals(h.getGeocode(), a.toBase32());
            }
            assertTrue(same || Objects.equals(h.getGeocode(), geoHash.toBase32()));
        }
    }


    @Test
    public void testGetHousesByPage() {

        Sort sort = Sort.by(Sort.Direction.DESC, "createTime"); //按创建时间排序
        Pageable pageable = PageRequest.of(page, pageSize, sort);

        // Get all
        assertEquals(houseEntityService.getHouseListByPage(null, null, null, null, null, null, null, "", pageable).getSize(), pageSize);

        // Get specific district
        List<String> district = new ArrayList<>();
        district.add("普陀");
        district.add("闵行");
        houseEntityService.getHouseListByPage(district, null, null, null, null, null, null, "", pageable).forEach(house -> assertTrue(Objects.equals(house.getDistrict(), "普陀") || Objects.equals(house.getDistrict(), "闵行")));

        // Get price
        Integer price1 = 1600;
        Integer price2 = 4000;
        houseEntityService.getHouseListByPage(null, price1, price2, null, null, null, null, "", pageable).forEach(house -> {
            Integer price = house.getPrice();
            assertTrue(price >= price1 && price <= price2);
        });

        // Get rentType

        List<Integer> rentType = new ArrayList<>();
        rentType.add(0);
        rentType.add(2);
        houseEntityService.getHouseListByPage(null, null, null, rentType, null, null, null, "", pageable).forEach(houseEntity -> {
            Integer rt = houseEntity.getRentType();
            assertTrue(rentType.contains(rt));
        });


        // Get rooms
        List<Integer> rooms = new ArrayList<>();
        rooms.add(1);
        rooms.add(2);
        rooms.add(4);
        houseEntityService.getHouseListByPage(null, null, null, null, rooms, null, null, "", pageable).forEach(house -> {
            Integer shi = house.getShi();
            assertTrue(rooms.contains(shi) || shi >= 4);
        });

        // Metro line
        Integer metroLines = 3;
        List<String> metroStation = new ArrayList<>();
        metroStation.add("中潭路");
        metroStation.add("龙漕路");
        houseEntityService.getHouseListByPage(null, null, null, null, null, metroLines, metroStation, "", pageable).forEach(house -> {
            Integer ml = house.getMetroLine();
            assertEquals(ml, metroLines);
            assertTrue(metroStation.contains(house.getMetroStation()));
        });

    }

    @Test
    public void testGetAllHouses() {

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
        rentType.add(0);
        rentType.add(2);
        houseEntities.addAll(houseEntityService.getAllHouse(null, null, null, rentType, null, null, null));
        for (HouseEntity house : houseEntities) {
            Integer rt = house.getRentType();
            assertTrue(rentType.contains(rt));
        }

        // Get rooms
        houseEntities.clear();
        List<Integer> rooms = new ArrayList<>();
        rooms.add(1);
        rooms.add(2);
        rooms.add(4);
        houseEntities.addAll(houseEntityService.getAllHouse(null, null, null, null, rooms, null, null));
        for (HouseEntity house : houseEntities) {
            Integer shi = house.getShi();
            assertTrue(rooms.contains(shi) || shi >= 4);
        }

        // Metro line
        houseEntities.clear();
        Integer metroLines = 3;
        List<String> metroStation = new ArrayList<>();
        metroStation.add("中潭路");
        metroStation.add("龙漕路");
        houseEntities.addAll(houseEntityService.getAllHouse(null, null, null, null, null, metroLines, metroStation));
        for (HouseEntity house : houseEntities) {
            Integer ml = house.getMetroLine();
            assertEquals(ml, metroLines);
            assertTrue(metroStation.contains(house.getMetroStation()));
        }

    }

    @Test
    public void testPriceBetween() {
        Integer price1 = 1500;
        Integer price2 = 3600;
        List<HouseEntity> houses = houseEntityService.findByPriceBetween(price1, price2);
        houses.forEach(houseEntity -> {
            Integer price = houseEntity.getPrice();
            assertTrue(price >= price1 && price <= price2);
        });
    }

//    @Test
//    public void testUpdateGeocode() {
//        houseEntityService.updateHouseGeocode(len);
//    }

//    @Test
//    public void testKeyword() {
//        // Get nearby
//        Sort sort = Sort.by(Sort.Direction.DESC, "createTime"); //按创建时间排序
//        Pageable pageable = PageRequest.of(page, pageSize, sort);
//
//        String keyword = "中福花";
//        Page<HouseEntity> houses = houseEntityService.searchKeyword(keyword + '*', pageable);
//        houses.forEach(houseEntity -> {
//            String title = houseEntity.getTitle();
//            String location = houseEntity.getLocation();
//            String residential = houseEntity.getResidential();
//            String layout = houseEntity.getLayout();
//            assertTrue(title.contains(keyword) || location.contains(keyword) || residential.contains(keyword) || layout.contains(keyword));
//        });
//    }

}
