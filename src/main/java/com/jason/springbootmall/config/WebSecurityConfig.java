package com.jason.springbootmall.config;

import com.jason.springbootmall.security.filter.JWTFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig{

    @Autowired
    JWTFilter jwtFilter;
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    //在HttpSecurity中註意使用了lambda寫法，使用這種寫法之後，每個設置都直接返回HttpSecurity對象
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                // 禁用basic明文驗證
                .httpBasic().disable()
                // 關閉csrf
                .csrf().disable()
                // 禁用默認登出页
                .logout().disable()
                // 前後端分離是無狀態的，不需要session了，直接禁用。
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorizeHttpRequests -> authorizeHttpRequests
                        // 允許所有OPTIONS請求
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/products/**").permitAll()
                        // 允許直接訪問登陸註冊路口
                        .requestMatchers(HttpMethod.POST,"/users/login").anonymous()//設置匿名訪問(代表攜帶token不能訪問)
                        .requestMatchers(HttpMethod.POST, "/users/register").anonymous()
                        .requestMatchers(HttpMethod.GET, "/products").permitAll()
                        // 允许 SpringMVC 的默認錯誤地址匿名訪問
                        .requestMatchers("/error").permitAll()
                        // 其他所有接口必须有Authority信息，Authority在登入成功后的UserDetailsImpl对象中默认设置“ROLE_USER”
                        //.requestMatchers("/**").hasAnyAuthority("ROLE_USER")
                        // 允许其他任意請求被已登入用戶訪問，不检 查Authority
                        .anyRequest().authenticated());

        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);//設定FILTER 出現在哪個CLASS 之前
        return http.build();
    }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
