package com.jason.springbootmall.dao;

import com.jason.springbootmall.dto.OrderQueryParams;
import com.jason.springbootmall.model.Order;
import com.jason.springbootmall.model.OrderItem;
import io.swagger.models.auth.In;

import java.util.List;

public interface OrderDao {
    Integer createOrder(Integer userId,int totalAmount);

    void createOrderItems(Integer orderId, List<OrderItem> ordItemsList);

    Order getOrderById(Integer orderId);


    List<OrderItem>getOrderItemsByOrderId(Integer orderId);

    Integer countOrder(OrderQueryParams orderQueryParams);

    List<Order> getOrders(OrderQueryParams orderQueryParams) ;
}
