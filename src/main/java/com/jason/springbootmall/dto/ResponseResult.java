package com.jason.springbootmall.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class ResponseResult<T> {
    int code;
    String msg;
    T body;
}
