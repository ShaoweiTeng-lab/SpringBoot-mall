package com.jason.springbootmall.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jason.springbootmall.dao.UserDao;
import com.jason.springbootmall.dto.ProductRequest;
import com.jason.springbootmall.dto.UserLoginRequest;
import com.jason.springbootmall.dto.UserPasswordUpdateRequest;
import com.jason.springbootmall.dto.UserRegisterRequest;
import com.jason.springbootmall.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;
    private ObjectMapper objectMapper=new ObjectMapper();
    @Autowired
    private UserDao userDao;
    //註冊
    @Test
    @Transactional
    public void register_Success_Test() throws Exception{
        UserRegisterRequest userRegisterRequest=new UserRegisterRequest();
        userRegisterRequest.setEmail("Jason@gmail.com");
        userRegisterRequest.setPassword("JasonSayHi");
        String  json=objectMapper.writeValueAsString(userRegisterRequest);
        RequestBuilder builder=MockMvcRequestBuilders
                .post("/users/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);
        mockMvc.perform(builder)
                .andDo(print())
                .andExpect(status().is(201))
                .andExpect(jsonPath("$.userId", notNullValue()))
                .andExpect(jsonPath("$.email", equalTo("Jason@gmail.com")))
                .andExpect(jsonPath("$.createdDate", notNullValue()))
                .andExpect(jsonPath("$.lastModifiedDate", notNullValue()));

        // 檢查資料庫中的密碼不為明碼
        User user = userDao.getUserByEmail(userRegisterRequest.getEmail());
        assertNotEquals(userRegisterRequest.getPassword(), user.getPassword());
    }
    @Test
    @Transactional
    public void register_invalidEmail_Test() throws Exception{
        UserRegisterRequest userRegisterRequest=new UserRegisterRequest();
        userRegisterRequest.setEmail("Jason");
        userRegisterRequest.setPassword("JasonSayHi");
        String  json=objectMapper.writeValueAsString(userRegisterRequest);
        RequestBuilder builder=MockMvcRequestBuilders
                .post("/users/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);
        mockMvc.perform(builder)
                .andDo(print())
                .andExpect(status().is(400));
    }
    @Test
    @Transactional
    public void register_EmailAlreadyExits_Test() throws Exception{
        //先註冊一個帳號
        UserRegisterRequest userRegisterRequest =new UserRegisterRequest();
        userRegisterRequest.setEmail("test@gmail.com");
        userRegisterRequest.setPassword("password");

        String  json=objectMapper.writeValueAsString(userRegisterRequest);

        RequestBuilder builder=MockMvcRequestBuilders
                .post("/users/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        mockMvc.perform(builder)
                .andDo(print())
                .andExpect(status().is(201));
        // 再次使用同個 email 註冊
        mockMvc.perform(builder)
                .andDo(print())
                .andExpect(status().is(400));
    }
    //登入
    @Transactional
    @Test
    public  void login_Success()throws Exception{
        //先註冊一個帳號
        UserRegisterRequest userRegisterRequest =new UserRegisterRequest();
        userRegisterRequest.setEmail("test1@gmail.com");
        userRegisterRequest.setPassword("password");
        register(userRegisterRequest);

        //測試登入
        UserLoginRequest userLoginRequest=new UserLoginRequest();
        userLoginRequest.setEmail("test1@gmail.com");
        userLoginRequest.setPassword("password");
        String  json=objectMapper.writeValueAsString(userLoginRequest);

        RequestBuilder builder=MockMvcRequestBuilders
                .post("/users/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        mockMvc.perform(builder)
                .andDo(print())
                .andExpect(status().is(200))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.userId",notNullValue()))
                .andExpect(jsonPath("$.email",equalTo(userLoginRequest.getEmail())))
                .andExpect(jsonPath("$.createdDate",notNullValue()))
                .andExpect(jsonPath("$.lastModifiedDate",notNullValue()));
    }
    @Test
    public void  login_Wrong_Password() throws Exception{
        //先註冊一個帳號
        UserRegisterRequest userRegisterRequest =new UserRegisterRequest();
        userRegisterRequest.setEmail("test1@gmail.com");
        userRegisterRequest.setPassword("password");
        register(userRegisterRequest);

        //測試登入
        UserLoginRequest userLoginRequest=new UserLoginRequest();
        userLoginRequest.setEmail("test1@gmail.com");
        userLoginRequest.setPassword("wrong");
        String  json=objectMapper.writeValueAsString(userLoginRequest);

        RequestBuilder builder=MockMvcRequestBuilders
                .post("/users/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        mockMvc.perform(builder)
                .andDo(print())
                .andExpect(status().is(400));
    }
    @Test
    public  void login_emailNotExist()throws Exception{
        //先註冊一個帳號
        UserRegisterRequest userRegisterRequest =new UserRegisterRequest();
        userRegisterRequest.setEmail("test1@gmail.com");
        userRegisterRequest.setPassword("password");
        register(userRegisterRequest);

        //測試登入
        UserLoginRequest userLoginRequest=new UserLoginRequest();
        userLoginRequest.setEmail("123@gmail.com");
        userLoginRequest.setPassword("password");
        String  json=objectMapper.writeValueAsString(userLoginRequest);

        RequestBuilder builder=MockMvcRequestBuilders
                .post("/users/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        mockMvc.perform(builder)
                .andDo(print())
                .andExpect(status().is(400));
    }

    @Test
    public void login_invalidEmailFormat() throws Exception {
        //先註冊一個帳號
        UserRegisterRequest userRegisterRequest =new UserRegisterRequest();
        userRegisterRequest.setEmail("test1@gmail.com");
        userRegisterRequest.setPassword("password");
        register(userRegisterRequest);

        //測試登入
        UserLoginRequest userLoginRequest=new UserLoginRequest();
        userLoginRequest.setEmail("haha123");
        userLoginRequest.setPassword("password");
        String  json=objectMapper.writeValueAsString(userLoginRequest);

        RequestBuilder builder=MockMvcRequestBuilders
                .post("/users/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        mockMvc.perform(builder)
                .andDo(print())
                .andExpect(status().is(400));
    }

    @Test
    public void updatePassword_Success() throws Exception{
        //先註冊一個帳號
        UserRegisterRequest userRegisterRequest =new UserRegisterRequest();
        userRegisterRequest.setEmail("test1@gmail.com");
        userRegisterRequest.setPassword("password");
        register(userRegisterRequest);

        //修改密碼
        UserPasswordUpdateRequest userPasswordUpdateRequest=new UserPasswordUpdateRequest();
        userPasswordUpdateRequest.setEmail("test1@gmail.com");
        userPasswordUpdateRequest.setNewPassword("newPassword");
        String json=objectMapper.writeValueAsString(userPasswordUpdateRequest);

        RequestBuilder builder=MockMvcRequestBuilders
                .post("/users/updatePassword")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);
        mockMvc.perform(builder)
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void updatePassword_EmailNotFound() throws Exception{

        //先註冊一個帳號
        UserRegisterRequest userRegisterRequest =new UserRegisterRequest();
        userRegisterRequest.setEmail("test1@gmail.com");
        userRegisterRequest.setPassword("password");
        register(userRegisterRequest);

        //修改密碼
        UserPasswordUpdateRequest userPasswordUpdateRequest=new UserPasswordUpdateRequest();
        userPasswordUpdateRequest.setEmail("test23@gmail.com");
        userPasswordUpdateRequest.setNewPassword("newPassword");
        String json=objectMapper.writeValueAsString(userPasswordUpdateRequest);

        RequestBuilder builder=MockMvcRequestBuilders
                .post("/users/updatePassword")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);
        mockMvc.perform(builder)
                .andDo(print())
                .andExpect(status().is(400));
    }
    @Test
    public void updatePassword_Empty() throws Exception{
        //先註冊一個帳號
        UserRegisterRequest userRegisterRequest =new UserRegisterRequest();
        userRegisterRequest.setEmail("test1@gmail.com");
        userRegisterRequest.setPassword("password");
        register(userRegisterRequest);

        //修改密碼
        UserPasswordUpdateRequest userPasswordUpdateRequest=new UserPasswordUpdateRequest();
        userPasswordUpdateRequest.setEmail("");
        userPasswordUpdateRequest.setNewPassword(" ");
        String json=objectMapper.writeValueAsString(userPasswordUpdateRequest);

        RequestBuilder builder=MockMvcRequestBuilders
                .post("/users/updatePassword")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);
        mockMvc.perform(builder)
                .andDo(print())
                .andExpect(status().is(400));
    }

    private void register(UserRegisterRequest userRegisterRequest) throws Exception {
        String json = objectMapper.writeValueAsString(userRegisterRequest);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/users/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        mockMvc.perform(requestBuilder)
                .andExpect(status().is(201));
        System.out.println("註冊成功");
    }
}