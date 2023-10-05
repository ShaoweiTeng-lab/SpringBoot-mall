package com.jason.springbootmall.controller;

import com.jason.springbootmall.dto.ResponseResult;
import com.jason.springbootmall.dto.UserLoginRequest;
import com.jason.springbootmall.dto.UserPasswordUpdateRequest;
import com.jason.springbootmall.dto.UserRegisterRequest;
import com.jason.springbootmall.model.User;
import com.jason.springbootmall.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {
    @Autowired
    UserService userService;
    //註冊
    @PostMapping("/users/register")
    public ResponseEntity<User> register(@RequestBody @Valid UserRegisterRequest userRegisterRequest){

        Integer userId= userService.register(userRegisterRequest);
        User user =userService.getUserById(userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }
    //登入
    @PostMapping("/users/login")
    public  ResponseEntity<?> login(@RequestBody @Valid UserLoginRequest userLoginRequest){
        ResponseResult rs =userService.login(userLoginRequest);
        return ResponseEntity.status(HttpStatus.OK).body(rs);
    }
    @GetMapping("/users/logout")
    //登出
    public ResponseEntity<?> logout(){
        ResponseResult rs =userService.logout();
        return ResponseEntity.status(HttpStatus.OK).body(rs);
    }
    @PostMapping("/users/updatePassword")
    //更改密碼
    public  ResponseEntity<?> updatePassword(@RequestBody @Valid UserPasswordUpdateRequest userPasswordUpdateRequest){
        userService.updatePassword(userPasswordUpdateRequest);
        return ResponseEntity.status(HttpStatus.OK).body("更改完成");
    }

    @GetMapping("/user/getUserById/{id}")
    public  ResponseResult<User> getUserById(@PathVariable Integer id){
        User user=userService.getUserById(id);
        ResponseResult rs =new ResponseResult(200,"success",user);
        return  rs;
    }
}
