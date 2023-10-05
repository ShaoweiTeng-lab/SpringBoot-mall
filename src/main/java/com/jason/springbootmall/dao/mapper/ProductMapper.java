package com.jason.springbootmall.dao.mapper;

import com.jason.springbootmall.dao.mapper.provider.ProductProvider;
import com.jason.springbootmall.dto.ProductQueryParams;
import com.jason.springbootmall.dto.ProductRequest;
import com.jason.springbootmall.model.Product;
import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.*;

import java.util.Date;
import java.util.List;

@Mapper
public interface ProductMapper {
    @Results({
            @Result(property = "productId", column = "product_Id"),
            @Result(property = "productName", column = "product_Name"),
            @Result(property = "category", column = "category"),
            @Result(property = "imageUrl", column = "image_Url"),
            @Result(property = "createdDate", column = "created_Date"),
            @Result(property = "lastModifiedDate", column = "last_Modified_Date")
    }
    )
    @Select("select  " +
            "product_Id," +
            "product_name, " +
            "category, " +
            "image_url, " +
            "price, stock, " +
            "description, " +
            "created_date, " +
            "last_modified_date " +
            "from  product " +
            "where product_id=#{productId}")
    Product getById(Integer productId);

    @Insert("INSERT  INTO product(PRODUCT_NAME, " +
            "CATEGORY, " +
            "IMAGE_URL," +
            " PRICE, " +
            "STOCK, " +
            "DESCRIPTION, " +
            "CREATED_DATE, " +
            "LAST_MODIFIED_DATE) " +
            "VALUES(" +
            "#{productName}," +
            "#{category}," +
            "#{imageUrl}," +
            "#{price}," +
            "#{stock}," +
            "#{description}," +
            "#{createDate}," +
            "#{lastModifiedDate}" +
            ")")
    @Options(useGeneratedKeys = true, keyProperty = "productId")
    void create(ProductRequest productRequest);

    @Update("update  product set " +
            "product_name=#{productRequest.productName}," +
            "category= #{productRequest.category} ," +
            "IMAGE_URL= #{productRequest.imageUrl} ," +
            "PRICE= #{productRequest.price}," +
            "STOCK= #{productRequest.stock} ," +
            "description= #{productRequest.description}," +
            "LAST_MODIFIED_DATE= #{productRequest.lastModifiedDate}" +
            " where " +
            "product_id= #{productId};")
    void updateById(@Param("productId") Integer productId, @Param("productRequest") ProductRequest productRequest);

    @Delete("delete  from product where product_id=#{productId}")
    void deleteById(Integer productId);

    @Results({
            @Result(property = "productId", column = "product_Id"),
            @Result(property = "productName", column = "product_Name"),
            @Result(property = "category", column = "category"),
            @Result(property = "imageUrl", column = "image_Url"),
            @Result(property = "createdDate", column = "created_Date"),
            @Result(property = "lastModifiedDate", column = "last_Modified_Date")
    }
    )
    @SelectProvider(type = ProductProvider.class,method = "getProducts")
    List<Product> getProducts(@Param("productQueryParams")ProductQueryParams productQueryParams);
    @SelectProvider(type = ProductProvider.class,method = "countProduct")
    Integer countProduct(@Param("productQueryParams") ProductQueryParams productQueryParams);
    @Update("UPDATE  product set " +
            "stock =#{stock}," +
            "last_modified_date =#{lastModifiedDate} " +
            "where product_id =#{productId}")
    void updateStock(@Param("productId") Integer productId,@Param("stock") Integer stock,@Param("lastModifiedDate") Date lastModifiedDate);
}
