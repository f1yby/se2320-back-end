package com.example.back_end.service;

import com.example.back_end.entity.HouseEntity;
import com.example.back_end.entity.UserEntity;
import com.example.back_end.utility.Result;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;

public interface UserEntityService {
    public Result<String> login(String name, String password);
    public Result<UserEntity> register(UserEntity user);
    public Result<String> favor(String hid);
    public Result<String> unFavor(String hid);
    public List<HouseEntity> getFavorites();
    public Result<String> checkFavorite(String hid);
}
