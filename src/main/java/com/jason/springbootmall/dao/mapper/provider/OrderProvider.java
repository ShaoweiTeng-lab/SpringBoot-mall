package com.jason.springbootmall.dao.mapper.provider;

import com.jason.springbootmall.dto.OrderQueryParams;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

public class OrderProvider {
    public String countOrder(@Param("orderQueryParams") OrderQueryParams orderQueryParams) {
        return new SQL() {{
            SELECT("COUNT(*)");
            FROM("`ORDER`");
            if (orderQueryParams.getUserId() != null) {
                WHERE("user_id = #{orderQueryParams.userId}"); // 使用@Param傳遞參數
            }
        }}.toString();
    }

    public String getOrders(@Param("orderQueryParams") OrderQueryParams orderQueryParams){
        return  new SQL(){{
            SELECT("order_id, user_id, created_date, total_amount, last_modified_date");
            FROM("`ORDER`");
            if (orderQueryParams.getUserId() != null) {
                WHERE("user_id = #{orderQueryParams.userId}"); // 使用@Param 傳遞參數
            }
           ORDER_BY("created_date DESC");
           LIMIT("#{orderQueryParams.limit}");
           OFFSET("#{orderQueryParams.offset}");
        }}.toString();
    }
}
