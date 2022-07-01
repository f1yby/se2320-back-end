package com.example.back_end.controller;

import com.example.back_end.entity.HouseEntity;
import com.example.back_end.repository.HouseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/house")
public class HouseController {

    @Autowired
    private HouseRepository houseRepository;


    @PostMapping("/search")
    public List<HouseEntity> getHouseByPrice(@RequestParam("price1") int price1, @RequestParam("price2") int price2) {
        return houseRepository.findByPriceBetween(price1, price2);
    }
}


