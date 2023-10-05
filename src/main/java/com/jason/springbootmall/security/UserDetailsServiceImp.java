package com.jason.springbootmall.security;

import com.jason.springbootmall.dao.UserDao;
import com.jason.springbootmall.model.Role;
import com.jason.springbootmall.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserDetailsServiceImp  implements UserDetailsService {
    @Autowired
    UserDao userDao;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user =userDao.getUserByEmail(username);
        List<Role> roles=userDao.getUserRolesByUserId(user.getUserId());
        //查詢對應權限
        List<String> permissionsList=new ArrayList();
        for (Role role: roles) {
            permissionsList.add(role.getRoleName());
        }
        return new UserDetailsImp(user,permissionsList);
    }

}
