package com.jason.springbootmall.model;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class Order {
    private Integer orderId;
    private Integer userId;
    private Integer totalAmount;
    private Date createdDate;
    private Date lastModifiedDate;
    private List<OrderItem> orderItemList;
}
