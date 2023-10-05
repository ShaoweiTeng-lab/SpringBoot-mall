package com.jason.springbootmall.dao.impl;

import com.jason.springbootmall.dao.OrderDao;
import com.jason.springbootmall.dao.rowMapper.OrderItemRawMapper;
import com.jason.springbootmall.dao.rowMapper.OrderRowMapper;
import com.jason.springbootmall.dto.OrderQueryParams;
import com.jason.springbootmall.dto.ProductQueryParams;
import com.jason.springbootmall.model.Order;
import com.jason.springbootmall.model.OrderItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class OrderDaoImp  implements OrderDao {
    @Autowired
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public Integer createOrder(Integer userId, int totalAmount) {
        String sql = "INSERT INTO `order`(user_Id, total_amount , created_date, last_modified_date) " +
                "VALUES(:userId, :totalAmount, :createdDate, :lastModifiedDate) ";
        Map<String,Object> map=new HashMap<>();
        map.put("userId",userId);
        map.put("totalAmount",totalAmount);

        Date now =new Date();
        map.put("createdDate",now);
        map.put("lastModifiedDate",now);

        KeyHolder keyHolder=new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(sql,new MapSqlParameterSource(map),keyHolder);
        int orderId=keyHolder.getKey().intValue();



        return orderId;
    }

    @Override
    public void createOrderItems(Integer orderId, List<OrderItem> ordItemsList) {
        //使用for loop 效率低
//        for (OrderItem orderItem: ordItemsList) {
//            String sql = "INSERT INTO  order_item( order_id, product_id, quantity, amount )" +
//                    "VALUES (:orderId, :productId , :quantity, :amount)";
//            Map<String,Object> map=new HashMap<>();
//            map.put("orderId",orderId);
//            map.put("productId",orderItem.getProductId());
//            map.put("quantity",orderItem.getQuantity());
//            map.put("amount",orderItem.getAmount());
//            namedParameterJdbcTemplate.update(sql,map);
//        }

        //使用batchUpdate 效率高

        String sql = "INSERT INTO  order_item( order_id, product_id, quantity, amount )" +
                "VALUES (:orderId, :productId , :quantity, :amount)";

        MapSqlParameterSource[] parameterSources= new MapSqlParameterSource[ordItemsList.size()];
        for (int i = 0; i < ordItemsList.size(); i++) {
            OrderItem orderItem = ordItemsList.get(i);
            parameterSources[i]=new MapSqlParameterSource();
            parameterSources[i].addValue("orderId",orderId);
            parameterSources[i].addValue("productId",orderItem.getProductId());
            parameterSources[i].addValue("quantity",orderItem.getQuantity());
            parameterSources[i].addValue("amount",orderItem.getAmount());
        }
        namedParameterJdbcTemplate.batchUpdate(sql,parameterSources);
    }

    @Override
    public Order getOrderById(Integer orderId) {
        String sql = "SELECT " +
                "order_id, " +
                "user_id, " +
                "total_amount, " +
                "created_date, " +
                "last_modified_date from `order` " +
                "where " +
                "order_id = :orderId";

        Map<String, Object> map =new HashMap<>();
        map.put("orderId",orderId);
        List<Order> orderList=namedParameterJdbcTemplate.query(sql,map,new OrderRowMapper());

        if(orderList.size() > 0)
            return orderList.get(0);
        return null;
    }



    //取得該筆訂單的所有商品資訊
    @Override
    public List<OrderItem> getOrderItemsByOrderId(Integer orderId) {
        String  sql="SELECT " +
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
                " where oi.order_id =:orderId";

        Map<String ,Object> map=new HashMap<>();
        map.put("orderId",orderId);
        //一筆訂單包含多個 item ，故回傳list
        List<OrderItem> orderItemList=namedParameterJdbcTemplate.query(sql, map, new OrderItemRawMapper());

        return orderItemList;
    }

    @Override
    public Integer countOrder(OrderQueryParams orderQueryParams) {
        String sql = "SELECT COUNT(*) FROM `order` WHERE 1=1";

        Map<String,Object> map=new HashMap<>();

        sql =addFilteringSql(sql ,map,orderQueryParams);

        Integer count= namedParameterJdbcTemplate.queryForObject(sql,map, Integer.class);
        return count;
    }
    //得到使用者的全部訂單
    @Override
    public List<Order> getOrders(OrderQueryParams orderQueryParams) {
        String sql = "SELECT order_id, user_id, created_date, total_amount, last_modified_date FROM `order` WHERE 1=1";

        Map<String,Object> map =new HashMap<>();

        //查詢條件
        sql =addFilteringSql(sql,map,orderQueryParams);

        //排序(由新至舊)
        sql = sql +" ORDER BY created_date DESC ";

        //分頁
        sql = sql + " Limit  :limit OFFSET :offset";
        map.put("limit",orderQueryParams.getLimit());
        map.put("offset",orderQueryParams.getOffset());

        List<Order> orderList=namedParameterJdbcTemplate.query(sql, map, new OrderRowMapper());
        return orderList;
    }


    private String  addFilteringSql(String sql, Map<String, Object> map, OrderQueryParams oderQueryParams){

        if(oderQueryParams.getUserId() != null){
            sql += " AND user_id= :userId";
            map.put("userId",oderQueryParams.getUserId() );
        }

        return  sql;
    }

}
