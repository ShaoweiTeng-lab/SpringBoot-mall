package com.jason.springbootmall.model;

import lombok.Data;

@Data
public class OrderItem {
    private Integer orderItemId;
    private Integer orderId;
    private Integer productId;
    private Integer quantity;
    private Integer amount;
    private String ProductName;
    private String imageUrl;

}
