package com.jason.springbootmall.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jason.springbootmall.dto.ProductRequest;
import com.jason.springbootmall.model.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ProductControllerTest {
    @Autowired
    private MockMvc mockMvc;
    private ObjectMapper objectMapper=new ObjectMapper();

    // 查詢商品
   @Test
    public  void getProducts_Success_Test() throws Exception{
        RequestBuilder builder = MockMvcRequestBuilders
                .get("/products/{productId}",1);
        mockMvc.perform(builder)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productName", equalTo("蘋果（澳洲）")));
    }
    @Test
    public  void getProduct_NotFound_Test()  throws Exception{
        RequestBuilder builder=MockMvcRequestBuilders
                .get("/products/{productId}",-1);
        mockMvc.perform(builder)
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    // 創建商品
    @Test
    @Transactional
    public  void createProduct_Success_Test()throws Exception{
        ProductRequest productRequest=new ProductRequest();
        productRequest.setProductName("test Product");
        productRequest.setImageUrl("http://test.com");
        productRequest.setCategory(Product.ProductCategory.FOOD);
        productRequest.setPrice(10);
        productRequest.setStock(100);
        String  json=objectMapper.writeValueAsString(productRequest);

        RequestBuilder builder=MockMvcRequestBuilders
                .post("/products")
                .contentType(MediaType.APPLICATION_JSON)//務必設定此  contentType
                .content(json);
        mockMvc.perform(builder)
                .andDo(print())
                .andExpect(status().is(201))
                .andExpect(jsonPath("$.productName",equalTo("test Product")))
                .andExpect(jsonPath("$.category",equalTo("FOOD")))
                .andExpect(jsonPath("$.price", equalTo(10)))
                .andExpect(jsonPath("$.stock", equalTo(100)))
                .andExpect(jsonPath("$.description", nullValue()))
                .andExpect(jsonPath("$.createdDate", notNullValue()))
                .andExpect(jsonPath("$.lastModifiedDate", notNullValue()));
    }

    @Test
    @Transactional
    public  void createProduct_Failed_Test()throws Exception{
        ProductRequest productRequest=new ProductRequest();
        productRequest.setProductName("test Product");
        String  json=objectMapper.writeValueAsString(productRequest);

        RequestBuilder builder=MockMvcRequestBuilders
                .post("/products")
                .contentType(MediaType.APPLICATION_JSON)//務必設定此  contentType
                .content(json);
        mockMvc.perform(builder)
                .andDo(print())
                .andExpect(status().is(400));
    }

    // 更新商品
    @Test
    @Transactional
    public  void  UpdateProduct_Success_Test () throws  Exception {
        ProductRequest productRequest=new ProductRequest();
        productRequest.setProductName("test Update  Product");
        productRequest.setCategory(Product.ProductCategory.CAR);
        productRequest.setImageUrl("http://test.com");
        productRequest.setPrice(500);
        productRequest.setStock(101);
        String  json=objectMapper.writeValueAsString(productRequest);
        RequestBuilder builder=MockMvcRequestBuilders
                .put("/products/{productId}",5)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);
        mockMvc.perform(builder)
                .andDo(print())
                .andExpect(status().is(201))
                .andExpect(jsonPath("$.description", nullValue()))
                .andExpect(jsonPath("$.createdDate", notNullValue()))
                .andExpect(jsonPath("$.lastModifiedDate", notNullValue()));
    }

    @Test
    @Transactional
    public  void  UpdateProduct_Failed_Test () throws  Exception {
        ProductRequest productRequest=new ProductRequest();
        String  json=objectMapper.writeValueAsString(productRequest);
        RequestBuilder builder=MockMvcRequestBuilders
                .put("/products/{productId}",20000)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);
        mockMvc.perform(builder)
                .andDo(print())
                .andExpect(status().is(400));
    }

    // 刪除商品

    @Test
    @Transactional
    public void  deleteProduct_Success_Test() throws Exception {
        RequestBuilder builder=MockMvcRequestBuilders
                .delete("/products/{productId}",5);

        mockMvc.perform(builder)
                .andDo(print())
                .andExpect(status().is(200));
    }
    @Test
    @Transactional
    public void  deleteProduct_Failed_Test() throws Exception {
        RequestBuilder builder=MockMvcRequestBuilders
                .delete("/products/{productId}",-1);

        mockMvc.perform(builder)
                .andDo(print())
                .andExpect(status().is(400));
    }

    // 查詢商品列表
    @Test
    public  void  getProducts()throws Exception{
        RequestBuilder builder = MockMvcRequestBuilders.get("/products");

        mockMvc.perform(builder)
                .andDo(print())
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.limit",notNullValue()))
                .andExpect(jsonPath("$.offset",notNullValue()))
                .andExpect(jsonPath("$.total", notNullValue()))
                .andExpect(jsonPath("$.results", hasSize(5)));

    }

    @Test
    public  void getProducts_Filter()  throws Exception {
        RequestBuilder builder = MockMvcRequestBuilders
                .get("/products")
                .param("search","B")
                .param("category","CAR");
        mockMvc.perform(builder)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.limit", notNullValue()))
                .andExpect(jsonPath("$.offset", notNullValue()))
                .andExpect(jsonPath("$.total", notNullValue()));
    }

    @Test
    public  void getProductSorting() throws Exception{
       RequestBuilder builder =MockMvcRequestBuilders
               .get("/products")
               .param("category","FOOD")
               .param("sort", "ASC");

        mockMvc.perform(builder)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.limit", notNullValue()))
                .andExpect(jsonPath("$.offset", notNullValue()))
                .andExpect(jsonPath("$.total", notNullValue()))
                .andExpect(jsonPath("$.results[0].productId", equalTo(6)))
                .andExpect(jsonPath("$.results[1].productId", equalTo(5)))
                .andExpect(jsonPath("$.results[2].productId", equalTo(7)));
    }
    @Test
    public void getProducts_Pagination()throws Exception {
        RequestBuilder builder =MockMvcRequestBuilders
                .get("/products")
                .param("limit", "2")
                .param("offset", "1");

        mockMvc.perform(builder)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.limit", equalTo(2)))
                .andExpect(jsonPath("$.offset", equalTo(1)))
                .andExpect(jsonPath("$.total", notNullValue()))
                .andExpect(jsonPath("$.results", hasSize(2)))
                .andExpect(jsonPath("$.results[0].productId", equalTo(5)))
                .andExpect(jsonPath("$.results[1].productId", equalTo(10)));
    }
}