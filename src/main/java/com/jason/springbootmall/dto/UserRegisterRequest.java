package com.jason.springbootmall.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import net.bytebuddy.implementation.bind.annotation.Empty;

import java.util.Date;

@Data
public class UserRegisterRequest {
    private Integer userId;
    @NotBlank
    @Email(message = "信箱格式錯誤")
    private String email;
    @NotBlank
    private String password;
    private  Date createdDate;
    private Date lastModifiedDate;
}
