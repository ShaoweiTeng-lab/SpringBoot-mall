package com.jason.springbootmall.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.apache.ibatis.annotations.Param;

@Data
public class UserPasswordUpdateRequest {
    @NotBlank
    @Email
    String email;
    @NotBlank
    String newPassword;
}
