package com.jason.springbootmall.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
@Data
public class UserOrders {
    Integer userId;
    List<List<OrderItem>> OrderItemList =new ArrayList<>();
}
