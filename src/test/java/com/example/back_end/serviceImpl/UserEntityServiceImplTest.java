package com.example.back_end.serviceImpl;

import com.example.back_end.controller.UserController;
import com.example.back_end.entity.UserEntity;
import com.example.back_end.service.UserEntityService;
import com.example.back_end.utility.JwtAuthenticationTokenFilter;
import com.example.back_end.utility.Result;
import com.jayway.jsonpath.JsonPath;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@WebAppConfiguration
@AutoConfigureMockMvc
//@AutoConfigureWebTestClient
class UserEntityServiceImplTest {

    @Autowired
    UserEntityService userEntityService;

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @Autowired
    private JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;

    @Autowired
    private UserController userController;
//    @Autowired
//    private WebTestClient webTestClient;

    @BeforeEach
    public void setupMockMvc() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).addFilter(jwtAuthenticationTokenFilter).build();
    }

    @Test
    void register() throws Exception {
        // this test should be reset everytime, since it's not delete form the database
        UserEntity user = new UserEntity("test0001", "123456");
        mockMvc.perform(MockMvcRequestBuilders.post("/user/register")
                        .param("name", "test0001")
                        .param("password", "123456"))
                .andExpect(MockMvcResultMatchers.jsonPath("msg").value("Success to login"))
                .andReturn();
        mockMvc.perform(MockMvcRequestBuilders.post("/user/register")
                        .param("name", "test0001")
                        .param("password", "123456"))
                .andExpect(MockMvcResultMatchers.jsonPath("msg").value("Username has been used!"))
                .andReturn();
    }

    @Test
    void login() throws Exception {
        UserEntity user = new UserEntity("test1001", "123456");
        userEntityService.register(user);
        mockMvc.perform(MockMvcRequestBuilders.post("/user/login")
                        .param("name", "test1001")
                        .param("password", "12345"))
                .andExpect(MockMvcResultMatchers.jsonPath("msg").value("Password error"))
                .andReturn();
        mockMvc.perform(MockMvcRequestBuilders.post("/user/login")
                        .param("name", "test1001")
                        .param("password", "123456"))
                .andExpect(MockMvcResultMatchers.jsonPath("msg").value("Success to login"))
                .andReturn();
    }

    @Test
    void favor() throws Exception {
        UserEntity user = new UserEntity("test1001", "123456");
        String hid = "003428aa-7411-40ae-a3c5-bfd394e24303";
        userEntityService.register(user);
        String token = userEntityService.login("test1001", "123456").getDetail();

        // no token
        mockMvc.perform(MockMvcRequestBuilders.post("/user/favor")
                        .param("house_id", hid))
                .andExpect(MockMvcResultMatchers.jsonPath("msg").value("No token"))
                .andReturn();
        // wrong token
        mockMvc.perform(MockMvcRequestBuilders.post("/user/favor")
                .param("house_id", hid)
                .header("Authorization", "qwq")
                ).andExpect(MockMvcResultMatchers.jsonPath("msg").value("No token"))
                        .andReturn();
        // success to favor
        mockMvc.perform(MockMvcRequestBuilders.post("/user/favor")
                        .param("house_id", hid)
                        .header("Authorization", token))
                .andExpect(MockMvcResultMatchers.jsonPath("msg").value("Success to favor"))
                .andReturn();
        // already favored
        mockMvc.perform(MockMvcRequestBuilders.post("/user/favor")
                        .param("house_id", hid)
                        .header("Authorization", token))
                .andExpect(MockMvcResultMatchers.jsonPath("msg").value("User had favored it"))
                .andReturn();
        // wrong hid
        mockMvc.perform(MockMvcRequestBuilders.post("/user/favor")
                        .param("house_id", "qwq")
                        .header("Authorization", token))
                .andExpect(MockMvcResultMatchers.jsonPath("msg").value("House doesn't exist"))
                .andReturn();
        // unfavor
        mockMvc.perform(MockMvcRequestBuilders.post("/user/unFavor")
                        .param("house_id", hid)
                        .header("Authorization", token))
                .andExpect(MockMvcResultMatchers.jsonPath("msg").value("Success to unfavor"))
                .andReturn();
    }

    @Test
    void unFavor() throws Exception {
        UserEntity user = new UserEntity("test1001", "123456");
        String hid = "003428aa-7411-40ae-a3c5-bfd394e24303";
        userEntityService.register(user);
        String token = userEntityService.login("test1001", "123456").getDetail();

        // 403
        mockMvc.perform(MockMvcRequestBuilders.post("/user/unFavor")
                        .param("house_id", hid))
                .andExpect(MockMvcResultMatchers.jsonPath("msg").value("No token"))
                .andReturn();
        // Favor doesn't exist
        mockMvc.perform(MockMvcRequestBuilders.post("/user/unFavor")
                        .param("house_id", hid)
                        .header("Authorization", token))
                .andExpect(MockMvcResultMatchers.jsonPath("msg").value("Favor doesn't exist"))
                .andReturn();
        // house not exist
        mockMvc.perform(MockMvcRequestBuilders.post("/user/unFavor")
                        .param("house_id", "qwq")
                        .header("Authorization", token))
                .andExpect(MockMvcResultMatchers.jsonPath("msg").value("House doesn't exist"))
                .andReturn();
        // success to unfavor
        mockMvc.perform(MockMvcRequestBuilders.post("/user/favor")
                        .param("house_id", hid)
                        .header("Authorization", token))
                .andExpect(MockMvcResultMatchers.jsonPath("msg").value("Success to favor"))
                .andReturn();
        mockMvc.perform(MockMvcRequestBuilders.post("/user/unFavor")
                        .param("house_id", hid)
                        .header("Authorization", token))
                .andExpect(MockMvcResultMatchers.jsonPath("msg").value("Success to unfavor"))
                .andReturn();
    }

    @Test
    void getFavorites() throws Exception {
        UserEntity user = new UserEntity("test1001", "123456");
        String hid = "003428aa-7411-40ae-a3c5-bfd394e24303";
        userEntityService.register(user);
        String token = userEntityService.login("test1001", "123456").getDetail();
        // 403
        mockMvc.perform(MockMvcRequestBuilders.post("/user/getFavorites"))
                                .andReturn();
        mockMvc.perform(MockMvcRequestBuilders.post("/user/favor")
                        .param("house_id", hid)
                        .header("Authorization", token))
                .andExpect(MockMvcResultMatchers.jsonPath("msg").value("Success to favor"))
                .andReturn();
        mockMvc.perform(MockMvcRequestBuilders.post("/user/getFavorites"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].id").value(hid))
                .andReturn();
        mockMvc.perform(MockMvcRequestBuilders.post("/user/unFavor")
                        .param("house_id", hid)
                        .header("Authorization", token))
                .andExpect(MockMvcResultMatchers.jsonPath("msg").value("Success to unfavor"))
                .andReturn();
        mockMvc.perform(MockMvcRequestBuilders.post("/user/getFavorites"))
                .andReturn();
    }

    @Test
    void checkFavorite() throws Exception {
        UserEntity user = new UserEntity("test1001", "123456");
        String hid = "003428aa-7411-40ae-a3c5-bfd394e24303";
        userEntityService.register(user);
        String token = userEntityService.login("test1001", "123456").getDetail();
        // 403
        mockMvc.perform(MockMvcRequestBuilders.post("/user/checkFavorite")
                .param("house_id", hid))
                .andExpect(MockMvcResultMatchers.jsonPath("msg").value("No token"))
                .andReturn();
        // favor
        mockMvc.perform(MockMvcRequestBuilders.post("/user/favor")
                        .param("house_id", hid)
                        .header("Authorization", token))
                .andReturn();
        // should favor
        mockMvc.perform(MockMvcRequestBuilders.post("/user/checkFavorite")
                        .param("house_id", hid)
                        .header("Authorization", token))
                .andExpect(MockMvcResultMatchers.jsonPath("msg").value("favor"))
                .andReturn();
        mockMvc.perform(MockMvcRequestBuilders.post("/user/unFavor")
                        .param("house_id", hid)
                        .header("Authorization", token))
                .andExpect(MockMvcResultMatchers.jsonPath("msg").value("Success to unfavor"))
                .andReturn();
        // should no favor
        mockMvc.perform(MockMvcRequestBuilders.post("/user/checkFavorite")
                        .param("house_id", hid)
                        .header("Authorization", token))
                .andExpect(MockMvcResultMatchers.jsonPath("msg").value("no favor"))
                .andReturn();
    }
}
