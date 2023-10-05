package com.jason.springbootmall.dao;

import com.jason.springbootmall.dto.UserLoginRequest;
import com.jason.springbootmall.dto.UserPasswordUpdateRequest;
import com.jason.springbootmall.dto.UserRegisterRequest;
import com.jason.springbootmall.model.Role;
import com.jason.springbootmall.model.User;
import com.jason.springbootmall.model.UserToken;

import java.util.List;

public interface UserDao {
    Integer createUser(UserRegisterRequest userRequest);
    User getUserById(int userId);
    User getUserByEmail(String email);

    void updatePassword(UserPasswordUpdateRequest userPasswordUpdateRequest);

    //Token
    UserToken getTokenByUserId(int userId);
    void createUserToken(int userId,String token);
    void updateUserToken(int userId,String token);
    void deleteUserTokenById(int userId);

    //Roles
    List<Role>getUserRolesByUserId(int userId);
    void createRolesByUserId(int userId);
}
