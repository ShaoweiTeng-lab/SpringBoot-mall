package com.jason.springbootmall.model;

import lombok.Data;

import java.util.List;

@Data
public class UserRoles {
    int userId;
    List<Role> userRoles;

}
