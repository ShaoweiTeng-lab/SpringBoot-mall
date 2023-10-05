package com.jason.springbootmall.dto;

import com.jason.springbootmall.model.Product;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Date;


@Data

public class ProductRequest {
    private  Integer productId;
    @NotBlank
    private String  productName;
    @NotNull
    private Product.ProductCategory category;
    @NotBlank
    private String  imageUrl;
    @NotNull
    private Integer price;
    @NotNull
    private Integer stock;

    private String  description;
    private Date createDate;
    private  Date lastModifiedDate;
}
