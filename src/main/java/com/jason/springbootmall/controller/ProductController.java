package com.jason.springbootmall.controller;

import com.jason.springbootmall.dto.ProductQueryParams;
import com.jason.springbootmall.dto.ProductRequest;
import com.jason.springbootmall.model.Product;
import com.jason.springbootmall.service.ProductService;
import com.jason.springbootmall.util.Page;
import io.swagger.annotations.Api;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


import java.util.List;
@Validated
@RestController
@Api(tags = "Products")
public class ProductController {
    @Autowired
    ProductService productService;
    @GetMapping("/products/{productId}")
    public ResponseEntity<Product> getProduct(@PathVariable(name = "productId") int productId) {

        Product rs= productService.getById(productId);
        if(rs == null)
            return  ResponseEntity.notFound().build();
        return  ResponseEntity.ok().body(rs);
    }
    @PreAuthorize("hasAnyAuthority('Manager')")//需要管理員層級才可新增
    @PostMapping("/products")
    public ResponseEntity<Product> createProduct(@RequestBody @Valid ProductRequest  request) {
        Integer productId= productService.create(request);
        Product rs=productService.getById(productId);
        return ResponseEntity.status(HttpStatus.CREATED).body(rs);
    }
    @PreAuthorize("hasAnyAuthority('Manager')")//需要管理員層級才可修改
    @PutMapping ("/products/{productId}")
    public ResponseEntity<Product> updateProduct(@PathVariable(name = "productId")Integer productId,
                                                 @RequestBody @Valid ProductRequest  request) {
        Product check=productService.getById(productId);
        if(check == null)//先檢查商品是否存在
            return ResponseEntity.badRequest().build();
        System.out.println("商品存在");
        productService.updateById(productId,request);
        Product rs=productService.getById(productId);
        return ResponseEntity.status(HttpStatus.CREATED).body(rs);
    }
    @PreAuthorize("hasAnyAuthority('Manager')")//需要管理員層級才可刪除
    @DeleteMapping ("/products/{productId}")
    public  ResponseEntity<?> deleteProduct(@PathVariable(name = "productId")Integer productId){
        Product check=productService.getById(productId);
        if(check == null)//先檢查商品是否存在
            return ResponseEntity.badRequest().build();
        productService.deleteById(productId);
        return ResponseEntity.status(200).build();
    }




    @GetMapping("/products")
    public  ResponseEntity<Page<Product>>getProduct(
            //查詢條件 Filtering
            @RequestParam(required = false)String search,
            @RequestParam(required = false) Product.ProductCategory category,
            //排序 Sorting
            @RequestParam(defaultValue ="last_modified_date") ProductQueryParams.OrderBy orderBy,
            @RequestParam(defaultValue ="DESC") ProductQueryParams.Sort sort,
            //分頁 Pagination
            @RequestParam(defaultValue="5")   @Max(100) @Min(0)  Integer limit,//取得幾筆
            @RequestParam(defaultValue = "0") @Min(0)   Integer offset//跳過幾筆
    ){
        ProductQueryParams productQueryParams= new ProductQueryParams();
        productQueryParams.setCategory(category);
        productQueryParams.setSearch(search);
        productQueryParams.setOrderBy(orderBy);
        productQueryParams.setSort(sort);
        productQueryParams.setLimit(limit);
        productQueryParams.setOffset(offset);
        List<Product> products;
        products= productService.getProducts(productQueryParams);
        //取得商品總筆 數
        Integer total =productService.countProduct(productQueryParams);
        //設定返回訊息
        Page<Product>  page=new Page<>();
        page.setLimit(limit);
        page.setTotal(total);//當前查詢總共有多少筆數據
        page.setOffset(offset);
        page.setResults(products);
        System.out.println(page.getResults().get(0).getProductId());
        return ResponseEntity.ok().body(page);
    }

}
