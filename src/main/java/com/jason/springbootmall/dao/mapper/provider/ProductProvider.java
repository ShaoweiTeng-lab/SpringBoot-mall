package com.jason.springbootmall.dao.mapper.provider;

import com.jason.springbootmall.dto.ProductQueryParams;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

public class ProductProvider {
    public String getProducts( ProductQueryParams productQueryParams) {
        return new SQL() {{
            SELECT("product_id, product_name, category, image_url, price, stock, description, created_date, last_modified_date");
            FROM("product");
            if (productQueryParams.getCategory() != null) {
                WHERE("category = #{productQueryParams.category}");
            }
            if (productQueryParams.getSearch() != null) {
                WHERE("product_name LIKE CONCAT('%', #{productQueryParams.search}, '%')");
            }
            ORDER_BY("${productQueryParams.orderBy} ${productQueryParams.sort}");
            LIMIT("#{productQueryParams.limit}");
            OFFSET("#{productQueryParams.offset}");
        }}.toString();
    }

    public  String countProduct(ProductQueryParams productQueryParams){
        return  new SQL(){{
            SELECT("count(*)");
            FROM("PRODUCT");
            if (productQueryParams.getCategory() != null) {
                WHERE("category = #{productQueryParams.category}");
            }
            if (productQueryParams.getSearch() != null) {
                WHERE("product_name LIKE CONCAT('%', #{productQueryParams.search}, '%')");
            }
        }}.toString();
    }
}

