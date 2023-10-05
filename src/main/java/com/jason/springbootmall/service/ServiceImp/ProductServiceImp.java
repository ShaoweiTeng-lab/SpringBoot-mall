package com.jason.springbootmall.service.ServiceImp;

import com.jason.springbootmall.dao.ProductDao;
import com.jason.springbootmall.dao.mapper.ProductMapper;
import com.jason.springbootmall.dto.ProductQueryParams;
import com.jason.springbootmall.dto.ProductRequest;
import com.jason.springbootmall.model.Product;
import com.jason.springbootmall.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ProductServiceImp implements ProductService {
    @Autowired
    ProductDao dao;
    @Autowired
    ProductMapper productMapper;
    @Override
    public Product getById(Integer productId) {
        return productMapper.getById(productId);
    }

    @Override
    public int create(ProductRequest dto) {
        Date now = new Date();
        dto.setCreateDate(now);
        dto.setLastModifiedDate(now);
        productMapper.create(dto);
        return dto.getProductId();
    }

    @Override
    public void updateById(Integer id, ProductRequest productRequest) {
        Date now = new Date();
        productRequest.setLastModifiedDate(now);
        productMapper.updateById(id,productRequest);
    }

    @Override
    public void deleteById(Integer productId) {
        productMapper.deleteById(productId);
    }
    @Override
    public List<Product> getProducts(ProductQueryParams productQueryParams) {
       return productMapper.getProducts(productQueryParams);
    }

    @Override
    public int countProduct(ProductQueryParams productQueryParams) {
        return productMapper.countProduct(productQueryParams);
    }

}
