package com.jason.springbootmall.security;

import com.jason.springbootmall.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class UserDetailsImp implements UserDetails {

    public UserDetailsImp(User user,List<String> permissionsList) {
        this.user = user;
        this.permissionsList =permissionsList;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    private User user;
    private List<String> permissionsList;

    private  List<SimpleGrantedAuthority> authorities;
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if(authorities!=null)//
            return  authorities;
        authorities =new ArrayList<>();//第一次執行
        for (String permission : permissionsList) {
            SimpleGrantedAuthority simpleAuthority =new SimpleGrantedAuthority(permission);
            authorities.add(simpleAuthority);
        }
        return authorities;
    }

    @Override
    public String getPassword() {
        if(user!=null)
            return  user.getPassword();
        return null;
    }

    @Override
    public String getUsername() {
        if(user!=null)
            return user.getEmail();
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
