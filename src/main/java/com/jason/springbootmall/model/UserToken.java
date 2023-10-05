package com.jason.springbootmall.model;

import lombok.Data;

@Data
public class UserToken {
    int tokenId;
    int userId;
    String token;
}
