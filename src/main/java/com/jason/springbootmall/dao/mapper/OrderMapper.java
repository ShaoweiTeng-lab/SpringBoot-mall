package com.jason.springbootmall.dao.mapper;

import com.jason.springbootmall.dao.mapper.provider.OrderProvider;
import com.jason.springbootmall.dto.OrderQueryParams;
import com.jason.springbootmall.model.Order;
import com.jason.springbootmall.model.OrderItem;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface OrderMapper {

    @Insert("INSERT  INTO `order`(user_Id, total_amount , created_date, last_modified_date) " +
            "VALUES(#{userId}, #{totalAmount}, #{createdDate}, #{lastModifiedDate})")
    @Options(useGeneratedKeys = true, keyProperty = "orderId")
    void createOrder(Order order);
    @Results({
            @Result(property = "userId", column = "user_Id"),
            @Result(property = "orderId", column = "order_Id"),
            @Result(property = "totalAmount", column = "total_Amount"),
            @Result(property = "createdDate", column = "created_date"),
            @Result(property = "lastModifiedDate", column = "last_modified_date")
    })
    @Select("SELECT " +
            "order_id, " +
            "user_id, " +
            "total_amount, " +
            "created_date, " +
            "last_modified_date from `order` " +
            "where " +
            "order_id = #{orderId}")
    Order getOrderById(Integer orderId);
    @Insert({
            "<script>",
            "INSERT INTO order_item (order_id, product_id, quantity, amount) VALUES ",
            "<foreach collection='orderItems' item='orderItem' separator=','>",
            "(#{orderId}, #{orderItem.productId}, #{orderItem.quantity}, #{orderItem.amount})",
            "</foreach>",
            "</script>"
    })
    void createOrderItems(@Param("orderId") Integer orderId, @Param("orderItems") List<OrderItem> orderItems);
    @Select("SELECT " +
            " oi.order_item_id," +
            " oi.order_id, " +
            " oi.product_id, " +
            " oi.quantity, " +
            " oi.amount, " +
            " p.product_name ," +
            " p.image_url   " +
            " FROM order_item  as oi " +
            " Left JOIN " +
            " product  as p ON oi.product_id = p.product_id" +
            " where oi.order_id =#{orderId}")
    List<OrderItem> getOrderItemsByOrderId(Integer orderId);
    @SelectProvider(type = OrderProvider.class,method = "countOrder")
    Integer countOrder(@Param("orderQueryParams") OrderQueryParams orderQueryParams);
    @Results({
            @Result(property = "userId", column = "user_Id"),
            @Result(property = "orderId", column = "order_Id"),
            @Result(property = "totalAmount", column = "total_Amount"),
            @Result(property = "createdDate", column = "created_date"),
            @Result(property = "lastModifiedDate", column = "last_modified_date")
    })
    @SelectProvider(type = OrderProvider.class,method = "getOrders")
    List<Order> getOrders(@Param("orderQueryParams")OrderQueryParams orderQueryParams);
}