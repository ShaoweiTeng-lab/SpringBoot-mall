package com.jason.springbootmall.dao.mapper;

import com.jason.springbootmall.dto.UserPasswordUpdateRequest;
import com.jason.springbootmall.dto.UserRegisterRequest;
import com.jason.springbootmall.model.Role;
import com.jason.springbootmall.model.User;
import com.jason.springbootmall.model.UserToken;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserMapper {
    @Results({
            @Result(property = "userId", column = "user_Id"),
            @Result(property = "createdDate", column = "created_date"),
            @Result(property = "lastModifiedDate", column = "last_modified_date")
    })
    @Select("SELECT user_Id, email, password, created_date, last_modified_date FROM `user` WHERE user_Id = #{userId}")
    User getUserById(int userId);
    @Insert("INSERT INTO `user`(email,password,created_date,last_modified_date) VALUES (#{email}, #{password}, #{createdDate},#{lastModifiedDate})")
    @Options(useGeneratedKeys = true, keyProperty = "userId")
    void createUser(UserRegisterRequest userRequest);
    @Select("Select user_Id ,email ,password ,created_date ,last_modified_date from `user` where email = #{email}")
    User getUserByEmail(@Param("email") String email);
    @Results({
            @Result(property = "userId", column = "user_Id"),
            @Result(property = "tokenId", column = "token_Id")
    })
    @Select("SELECT * FROM usertoken WHERE user_Id =#{userId}} ")
    UserToken getTokenByUserId(@Param("userId") int userId);
    @Update("Update usertoken set token =#{token} where user_Id = #{userId}}")
    void updateUserToken(@Param("userId") int userId, @Param("token")String token);

    @Delete("delete from usertoken where user_Id = #{userId}}")
    void  deleteUserTokenById(int userId);
    @Select("SELECT user_roles.role_Id ,roles.role_Name " +
            "FROM  `user` u " +
            "join user_roles " +
            "on " +
            "u.user_Id=user_roles.user_Id " +
            "join roles " +
            "on " +
            "roles.role_Id=user_roles.role_Id " +
            "where u.user_Id =:userId")
    @Results({
            @Result(property = "roleId", column = "role_Id"),
            @Result(property = "roleName", column = "role_Name")
    })
    List<Role> getUserRolesByUserId(int userId);
    @Insert("INSERT INTO user_roles(`user_Id`) VALUES( #{userId});")
    void createRolesByUserId(int userId);
    @Update("UPDATE `user` SET password= #{newPassword} WHERE email =#{email}")
    void updatePassword(UserPasswordUpdateRequest userPasswordUpdateRequest);
}
