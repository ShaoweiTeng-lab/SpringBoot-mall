package com.jason.springbootmall.dao;

import com.jason.springbootmall.dto.ProductQueryParams;
import com.jason.springbootmall.dto.ProductRequest;
import com.jason.springbootmall.model.Product;

import java.util.List;

public interface ProductDao {
    Product getById(Integer id);
    Integer create(ProductRequest dto);
    void  updateById(Integer id, ProductRequest dto);
    void deleteById(Integer id);



    List<Product> getProducts(ProductQueryParams productQueryParams);

    Integer countProduct(ProductQueryParams productQueryParams);

    void  updateStock(Integer productId,Integer stock);
}
