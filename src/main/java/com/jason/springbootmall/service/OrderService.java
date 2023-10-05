package com.jason.springbootmall.service;

import com.jason.springbootmall.dto.CreateOrderRequest;
import com.jason.springbootmall.dto.OrderQueryParams;
import com.jason.springbootmall.model.Order;
import com.jason.springbootmall.model.UserOrders;

import java.util.List;

public interface OrderService {
    Integer createOrder(Integer userId, CreateOrderRequest createOrderRequest);

    Order getOrderById(Integer orderId);



    List<Order>  getOrder(OrderQueryParams orderQueryParams);

    Integer  countOrder(OrderQueryParams orderQueryParams);
}
