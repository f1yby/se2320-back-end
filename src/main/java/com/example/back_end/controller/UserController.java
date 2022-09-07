package com.example.back_end.controller;


import com.example.back_end.entity.HouseEntity;
import com.example.back_end.entity.UserEntity;
import com.example.back_end.service.UserEntityService;
import com.example.back_end.utility.Result;
import org.aspectj.weaver.ast.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    private static final Logger LOGGER = LoggerFactory.getLogger(Test.class);
    @Autowired
    private UserEntityService userEntityService;

    @PostMapping("/login")
    public Result<String> login(@RequestParam(value = "name") String name,
                                @RequestParam(value = "password") String password) {
        LOGGER.info("User login:   " +
                "{UserName} = " + name + "\t" +
                "{PassWord} = " + password);
        return userEntityService.login(name, password);
    }

    @PostMapping("/register")
    public Result<UserEntity> register(@RequestParam(value = "name") String name,
                                       @RequestParam(value = "password") String password) {
        UserEntity newUser = new UserEntity(name, password);
        LOGGER.info("User Register:   " +
                "{UserName} = " + name + "\t" +
                "{PassWord} = " + password);
        return userEntityService.register(newUser);
    }

    @PostMapping("/favor")
    public Result<String> favor(@RequestParam(value = "house_id") String hid) {
        return userEntityService.favor(hid);
    }

    @PostMapping("/unFavor")
    public Result<String> unFavor(@RequestParam(value = "house_id") String hid) {
        return userEntityService.unFavor(hid);
    }

    @PostMapping("/getFavorites")
    public List<HouseEntity> getFavorites() {
        return userEntityService.getFavorites();
    }

    @PostMapping("/checkFavorite")
    public Result<String> checkFavorite(@RequestParam(value = "house_id") String hid) {
        return userEntityService.checkFavorite(hid);
    }
}
