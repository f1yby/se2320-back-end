package com.example.back_end.controller;

import com.example.back_end.entity.HouseEntity;
import com.example.back_end.serviceImpl.HouseEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/house")
public class HouseController {

    @Autowired
    HouseEntityService houseEntityService;


    @PostMapping("/search/price")
    public List<HouseEntity> getHouseByPrice(@RequestParam("price1") int price1,
                                             @RequestParam("price2") int price2) {
        return houseEntityService.findByPriceBetween(price1, price2);
    }

    @PostMapping("/search")
    public Page<HouseEntity> getHouse(@RequestParam(required = false) List<String> district,
                                      Integer price1, Integer price2,
                                      @RequestParam(required = false) List<Integer> rentType,
                                      @RequestParam(required = false) List<Integer> rooms,
                                      Integer metro_line,
                                      @RequestParam(required = false) List<String> metro_station,
                                      @RequestParam(defaultValue = "5") Integer pageSize,
                                      @RequestParam(defaultValue = "0") Integer page) {
//        int page = 1;       //当前页，从 0 开始。
        Sort sort = Sort.by(Sort.Direction.DESC, "createTime"); //按创建时间排序
        Pageable pageable = PageRequest.of(page, pageSize, sort);

        System.out.println("district= " + district + "\tprice1,price1=[" + price1 + "," + price2 + "]" + "\trentType=" + rentType + "\trooms=" + rooms + "\tmetro_line=" + metro_line + "\tmetro_station= " + metro_station + "\tpage= " + page+ "\tpageSize= " + pageSize);

        return houseEntityService.
                getHouseListByPage(district, price1, price2, rentType,
                        rooms, metro_line, metro_station, pageable);
    }

    @PostMapping("/search/nearby")
    public Page<HouseEntity> getNearbyHouse(@RequestParam Double lat, @RequestParam Double lng,
                                      @RequestParam(defaultValue = "5") Integer pageSize,
                                      @RequestParam(defaultValue = "0") Integer page) {
//        int page = 1;       //当前页，从 0 开始。
        Sort sort = Sort.by(Sort.Direction.DESC, "createTime"); //按创建时间排序
        Pageable pageable = PageRequest.of(page, pageSize, sort);

        return houseEntityService.
                getNearbyHouseByPage(5,lng,lat,pageable);
    }

    @PostMapping("/search/all")
    public List<HouseEntity> getAllHouse(@RequestParam(required = false) List<String> district,
                                         Integer price1, Integer price2,
                                         @RequestParam(required = false) List<Integer> rentType,
                                         @RequestParam(required = false) List<Integer> rooms,
                                         Integer metro_line,
                                         @RequestParam(required = false) List<String> metro_station) {
        return houseEntityService.getAllHouse(district, price1, price2, rentType, rooms, metro_line, metro_station);
    }

    @PostMapping("/search/keyword")
    public Page<HouseEntity> searchKeyword(@RequestParam String keyword,
                                            @RequestParam(defaultValue = "5") Integer pageSize,
                                            @RequestParam(defaultValue = "0") Integer page) {

        Sort sort = Sort.by(Sort.Direction.DESC, "create_time"); //按创建时间排序
        Pageable pageable = PageRequest.of(page, pageSize, sort);

        return houseEntityService.searchKeyword(keyword+'*',pageable);
    }
}


