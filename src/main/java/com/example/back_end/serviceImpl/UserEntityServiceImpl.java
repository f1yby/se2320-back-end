package com.example.back_end.serviceImpl;

import com.example.back_end.entity.FavoriteEntity;
import com.example.back_end.entity.HouseEntity;
import com.example.back_end.entity.UserEntity;
import com.example.back_end.repository.HouseRepository;
import com.example.back_end.repository.UserRepository;
import com.example.back_end.service.UserEntityService;
import com.example.back_end.utility.Constants;
import com.example.back_end.utility.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserEntityServiceImpl implements UserEntityService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private HouseRepository houseRepository;

    public Result<UserEntity> login(String name, String password) {
        Optional<UserEntity> user1 = userRepository.findByName(name);
        if (user1.isPresent()) {
            if (user1.get().getPassword().equals(password)) {
                return new Result<>(Constants.SUCCESS,
                        "Success to login", user1.get());
            }
            return new Result<>(Constants.ERROR, "Password error");
        }
        return new Result<>(Constants.ERROR, "User doesn't exist");
    }

    public Result<UserEntity> register(UserEntity user) {
        Optional<UserEntity> user1 = userRepository.findByName(user.getName());
        if (user1.isPresent())
            return new Result<>(Constants.ERROR, "Username has been used!");
        userRepository.save(user);
        user1 = userRepository.findByName(user.getName());
        if (user1.isPresent()) {
            return new Result<>(Constants.SUCCESS, "Success to login", user1.get());
        }
        return new Result<>(Constants.ERROR, "Register error");
    }

    public Result<UserEntity> favor(Integer uid, String hid) {
        Optional<UserEntity> user = userRepository.findById(uid);
        if (!user.isPresent())
            return new Result<>(Constants.ERROR, "User doesn't exist");
        Optional<HouseEntity> house = houseRepository.findById(hid);
        if (!house.isPresent())
            return new Result<>(Constants.ERROR, "House doesn't exist");

        FavoriteEntity favorite = new FavoriteEntity();
        favorite.setUser(user.get());
        favorite.setHouseId(house.get().getId());
        user.get().getFavoriteHouses().add(favorite);
        userRepository.save(user.get());
        return new Result<>(Constants.SUCCESS, "Success to favor", user.get());
    }

    public Result<UserEntity> unFavor(Integer uid, String hid) {
        Optional<UserEntity> user = userRepository.findById(uid);
        if (!user.isPresent())
            return new Result<>(Constants.ERROR, "User doesn't exist");
        Optional<HouseEntity> house = houseRepository.findById(hid);
        if (!house.isPresent())
            return new Result<>(Constants.ERROR, "House doesn't exist");

        for (FavoriteEntity favorite1 : user.get().getFavoriteHouses()) {
            if (favorite1.getHouseId().equals(house.get().getId())) {
                user.get().getFavoriteHouses().remove(favorite1);
                userRepository.save(user.get());
                return new Result<>(Constants.SUCCESS, "Success to unfavor", user.get());
            }
        }
        return new Result<>(Constants.ERROR, "Favor doesn't exist");
    }

    public List<HouseEntity> getFavorites(Integer uid) {
        Optional<UserEntity> user = userRepository.findById(uid);
        if (!user.isPresent()) return null;
        List<HouseEntity> res = new ArrayList<>();
        for (FavoriteEntity favorite : user.get().getFavoriteHouses()) {
            Optional<HouseEntity> house = houseRepository.findById(favorite.getHouseId());
            house.ifPresent(res::add);
        }
        return res;
    }

}
