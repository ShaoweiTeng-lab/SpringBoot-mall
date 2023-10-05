package com.jason.springbootmall.controller;

import com.jason.springbootmall.dto.CreateOrderRequest;
import com.jason.springbootmall.dto.OrderQueryParams;
import com.jason.springbootmall.model.Order;
import com.jason.springbootmall.model.UserOrders;
import com.jason.springbootmall.model.UserToken;
import com.jason.springbootmall.service.OrderService;
import com.jason.springbootmall.util.Page;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class OrderController {
    @Autowired
    OrderService orderService;
    @PostMapping("/users/createOrder")
    public ResponseEntity<?>createOrder( @RequestAttribute String  userId,
                                        @RequestBody @Valid CreateOrderRequest createOrderRequest){
        Integer orderId= orderService.createOrder(Integer.parseInt(userId) ,createOrderRequest);//得到資料庫創建的orderId
        Order order = orderService.getOrderById(orderId);
        return ResponseEntity.status(HttpStatus.CREATED).body(order);//得到詳細資訊
    }


    @GetMapping("/users/orders")
    public ResponseEntity<Page<Order>>getOrders(
            @RequestAttribute String  userId,
            @RequestParam(defaultValue = "10") @Max(100) @Min(0)Integer limit,
            @RequestParam(defaultValue = "0") @Min(0)Integer offset
    ){
        OrderQueryParams orderQueryParams =new OrderQueryParams();
        orderQueryParams.setUserId(Integer.parseInt(userId));
        orderQueryParams.setLimit(limit);
        orderQueryParams.setOffset(offset);

        //取得order List
        List<Order> orderList =orderService.getOrder(orderQueryParams);

        //取得order 總數
        Integer count = orderService.countOrder(orderQueryParams);

        //分頁
        Page<Order> page =new Page<>();
        page.setOffset(offset);
        page.setLimit(limit);
        page.setTotal(count);
        page.setResults(orderList);

        return  ResponseEntity.ok(page);
    }
}
