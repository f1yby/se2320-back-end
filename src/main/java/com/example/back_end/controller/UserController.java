package com.example.back_end.controller;


import com.example.back_end.entity.FavoriteEntity;
import com.example.back_end.entity.HouseEntity;
import com.example.back_end.entity.UserEntity;
import com.example.back_end.service.UserEntityService;
import com.example.back_end.utility.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserEntityService userEntityService;

    @PostMapping("/login")
    public Result<UserEntity> login(@RequestParam(value = "name") String name,
                                    @RequestParam(value = "password") String password) {
        return userEntityService.login(name, password);
    }

    @PostMapping("/register")
    public Result<UserEntity> register(@RequestParam(value = "name") String name,
                               @RequestParam(value = "password") String password) {
        UserEntity newUser = new UserEntity(name, password);
        return userEntityService.register(newUser);
    }

    @PostMapping("/favor")
    public Result<UserEntity> favor(@RequestParam(value = "user_id") Integer uid,
                            @RequestParam(value = "house_id") String hid) {
       return userEntityService.favor(uid, hid);
    }

    @PostMapping("/unFavor")
    public Result<UserEntity> unFavor(@RequestParam(value = "user_id") Integer uid,
                              @RequestParam(value = "house_id") String hid) {
        return userEntityService.unFavor(uid, hid);
    }

    @PostMapping("/getFavorites")
    public List<HouseEntity> getFavorites(@RequestParam(value = "user_id") Integer uid) {
        return userEntityService.getFavorites(uid);
    }

}
