package com.jason.springbootmall.security.filter;

import com.jason.springbootmall.dao.UserDao;
import com.jason.springbootmall.model.User;
import com.jason.springbootmall.model.UserToken;
import com.jason.springbootmall.security.UserDetailsServiceImp;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.server.ResponseStatusException;
import utils.JwtUtil;

import java.io.IOException;

@Component
public class JWTFilter extends OncePerRequestFilter {
    @Autowired
    UserDao userDao;
    @Autowired
    UserDetailsServiceImp userDetailsServiceImp;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = request.getHeader("Authorization");
        if(!StringUtils.hasText(token)) {
            filterChain.doFilter(request,response);
            return;
        }
        String  userId=null;
        JwtUtil jwt =new JwtUtil();

        Claims claims= jwt.validateToken(token);
        if(claims==null){
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().println("Token 異常");
            return;
        }
        userId=claims.getSubject();
        System.out.println("認證通過");
        UserToken userToken = userDao.getTokenByUserId(Integer.parseInt(userId));
//        if(userToken ==null){
//            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//            response.getWriter().println("已經被登出，請重新登入");
//            return;
//        }
        //已經被登出
//        if(!userToken.getToken().equals(token))//判斷token 與 db上相同
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        User user = userDao.getUserById(Integer.parseInt(userId));

        //存入SecurityContextHolder
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =new UsernamePasswordAuthenticationToken(userToken,null,userDetailsServiceImp.loadUserByUsername(user.getEmail()).getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        request.setAttribute("userId",user.getUserId());
        filterChain.doFilter(request,response);
    }
}