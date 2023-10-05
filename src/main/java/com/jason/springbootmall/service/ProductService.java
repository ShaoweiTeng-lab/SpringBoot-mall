package com.jason.springbootmall.service;

import com.jason.springbootmall.dto.ProductQueryParams;
import com.jason.springbootmall.dto.ProductRequest;
import com.jason.springbootmall.model.Product;

import java.util.List;

public interface ProductService {
    Product getById(Integer id);
    int create(ProductRequest dto);

    void updateById(Integer id,ProductRequest dto);

    void deleteById(Integer id);

    List<Product> getProducts(ProductQueryParams productQueryParams);

    int countProduct(ProductQueryParams productQueryParams);

}
