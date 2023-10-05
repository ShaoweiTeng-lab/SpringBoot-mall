package com.jason.springbootmall;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import utils.JwtUtil;

@SpringBootTest
public class JWTTest {
    @Test
    public  void testPasswordEncode(){
        BCryptPasswordEncoder bCryptPasswordEncoder=new BCryptPasswordEncoder();
        String encodePassword=bCryptPasswordEncoder.encode("user1");
        System.out.println(encodePassword);
    }
    @Test
    public  void jwtValidate(){
        JwtUtil jwtUtil = new JwtUtil();

        jwtUtil.validateToken("eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI3IiwiZXhwIjoxNjg1NTAwMDY3fQ.7CoIi1y4eIBkNDZ8OvrD2WsPYaaFNAyaXdbKTDS6blk");
    }
}
