package com.jason.springbootmall.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserLoginRequest {
    @NotBlank
    @Email
    String email;
    @NotBlank
    String password;
}
