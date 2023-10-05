package com.jason.springbootmall.dao.rowMapper;

import com.jason.springbootmall.model.OrderItem;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderItemRawMapper implements RowMapper<OrderItem> {
    @Override
    public OrderItem mapRow(ResultSet rs, int rowNum) throws SQLException {
        OrderItem orderItem = new OrderItem();
        orderItem.setOrderItemId(rs.getInt("order_item_id"));
        orderItem.setOrderId(rs.getInt("order_id"));
        orderItem.setProductId(rs.getInt("product_id"));
        orderItem.setQuantity(rs.getInt("quantity"));
        orderItem.setAmount(rs.getInt("amount"));

        //擴充
        orderItem.setProductName(rs.getString("product_name"));
        orderItem.setImageUrl(rs.getString("image_url"));
        return orderItem;
    }
}
