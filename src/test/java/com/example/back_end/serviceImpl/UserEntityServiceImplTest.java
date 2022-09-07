package com.example.back_end.serviceImpl;

import com.example.back_end.entity.UserEntity;
import com.example.back_end.service.UserEntityService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserEntityServiceImplTest {

    @Autowired
    UserEntityService userEntityService;

    @Test
    void register() {
        // this test should be reset everytime, since it's not delete form the database
        UserEntity user = new UserEntity("test0001", "123456");
        assertEquals("Success to login", userEntityService.register(user).getMsg());
        assertEquals("Username has been used!", userEntityService.register(user).getMsg());

    }

    @Test
    void login() {
        UserEntity user = new UserEntity("test1001", "123456");
        userEntityService.register(user);
        assertEquals("Password error", userEntityService.login("test1001", "12345").getMsg());
        assertEquals("Success to login", userEntityService.login("test1001", "123456").getMsg());
    }

    @Test
    void favor() {
    }

    @Test
    void unFavor() {
    }

    @Test
    void getFavorites() {
    }

    @Test
    void checkFavorite() {
    }
}
