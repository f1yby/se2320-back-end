package com.example.back_end.serviceImpl;

import com.example.back_end.entity.FavoriteEntity;
import com.example.back_end.entity.HouseEntity;
import com.example.back_end.entity.UserEntity;
import com.example.back_end.repository.FavoriteRepository;
import com.example.back_end.repository.HouseRepository;
import com.example.back_end.repository.UserRepository;
import com.example.back_end.service.UserEntityService;
import com.example.back_end.utility.Constants;
import com.example.back_end.utility.JwtTokenUtil;
import com.example.back_end.utility.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserEntityServiceImpl implements UserEntityService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FavoriteRepository favoriteRepository;

    @Autowired
    private HouseRepository houseRepository;

    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    public Result<String> login(String name, String password) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(name);

        System.out.println(userDetails.getUsername());
        System.out.println(userDetails.getPassword());
        System.out.println(userDetails.getAuthorities());

        if (!userDetails.getPassword().equals(password))
            return new Result<>(Constants.ERROR, "Password error");

        UsernamePasswordAuthenticationToken upToken = new UsernamePasswordAuthenticationToken(name, password);
        return new Result<>(Constants.SUCCESS, "Success to login", jwtTokenUtil.generateToken(userDetails));
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

    public Result<String> favor(String hid) {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();

        Optional<UserEntity> user = userRepository.findByName(name);
        if (!user.isPresent())
            return new Result<>(Constants.ERROR, "User doesn't exist");
        Optional<HouseEntity> house = houseRepository.findById(hid);
        if (!house.isPresent())
            return new Result<>(Constants.ERROR, "House doesn't exist");
        if(Objects.equals(checkFavorite(hid).getCode(), Constants.SUCCESS))
            return new Result<>(Constants.ERROR, "User had favored it");

        FavoriteEntity favorite = new FavoriteEntity();
        favorite.setUser(user.get());
        favorite.setHouseId(house.get().getId());
        user.get().getFavoriteHouses().add(favorite);
        userRepository.save(user.get());
        // generate new token
        UserDetails userDetails = userDetailsService.loadUserByUsername(name);
        UsernamePasswordAuthenticationToken upToken
                = new UsernamePasswordAuthenticationToken(name, user.get().getPassword());
        return new Result<>(Constants.SUCCESS, "Success to favor", jwtTokenUtil.generateToken(userDetails));
    }

    public Result<String> unFavor(String hid) {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<UserEntity> user = userRepository.findByName(name);
        if (!user.isPresent())
            return new Result<>(Constants.ERROR, "User doesn't exist");
        Optional<HouseEntity> house = houseRepository.findById(hid);
        if (!house.isPresent())
            return new Result<>(Constants.ERROR, "House doesn't exist");

        //UserEntity user1 = user.get();
        for (FavoriteEntity favorite1 : user.get().getFavoriteHouses()) {
            if (favorite1.getHouseId().equals(house.get().getId())) {
                user.get().getFavoriteHouses().remove(favorite1);
                favoriteRepository.deleteById(favorite1.getId());
                System.out.println("uid" + user.get().getId());
                userRepository.save(user.get());
                // generate new token
                UserDetails userDetails = userDetailsService.loadUserByUsername(name);
                UsernamePasswordAuthenticationToken upToken
                        = new UsernamePasswordAuthenticationToken(name, user.get().getPassword());
                return new Result<>(Constants.SUCCESS, "Success to unfavor", jwtTokenUtil.generateToken(userDetails));
            }
        }
        return new Result<>(Constants.ERROR, "Favor doesn't exist");
    }

    public List<HouseEntity> getFavorites() {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<UserEntity> user = userRepository.findByName(name);
        if (!user.isPresent()) return null;
        List<HouseEntity> res = new ArrayList<>();
        for (FavoriteEntity favorite : user.get().getFavoriteHouses()) {
            Optional<HouseEntity> house = houseRepository.findById(favorite.getHouseId());
            house.ifPresent(res::add);
        }
        return res;
    }


    public Result<String> checkFavorite(String hid) {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<UserEntity> user = userRepository.findByName(name);
        if (!user.isPresent()) return new Result<>(Constants.ERROR, "No login");
        boolean flag = false;
        for (FavoriteEntity favorite : user.get().getFavoriteHouses()) {
            if (favorite.getHouseId().equals(hid)) {
                flag = true;
                break;
            }
        }
        if (flag) return new Result<>(Constants.SUCCESS, "favor");
        return new Result<>(Constants.ERROR, "no favor");
    }

}
