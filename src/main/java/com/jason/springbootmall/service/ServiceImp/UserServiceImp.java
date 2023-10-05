package com.jason.springbootmall.service.ServiceImp;

import com.jason.springbootmall.dao.UserDao;
import com.jason.springbootmall.dao.mapper.UserMapper;
import com.jason.springbootmall.dto.ResponseResult;
import com.jason.springbootmall.dto.UserLoginRequest;
import com.jason.springbootmall.dto.UserPasswordUpdateRequest;
import com.jason.springbootmall.dto.UserRegisterRequest;
import com.jason.springbootmall.model.User;
import com.jason.springbootmall.model.UserToken;
import com.jason.springbootmall.service.UserService;
import com.jason.springbootmall.security.UserDetailsImp;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import org.slf4j.Logger;
import utils.JwtUtil;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
public class UserServiceImp implements UserService {
    private  final  static Logger log= LoggerFactory.getLogger(UserServiceImp.class);
//    @Autowired
//    UserDao userDao;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserMapper userMapper;

    @Override
    @Transactional
    public int register(UserRegisterRequest userRegisterRequest) {
        BCryptPasswordEncoder bCryptPasswordEncoder=new BCryptPasswordEncoder();
        String encodePassword=bCryptPasswordEncoder.encode(userRegisterRequest.getPassword());
        userRegisterRequest.setPassword(encodePassword);
        Date now = new Date();
        userRegisterRequest.setCreatedDate(now);
        userRegisterRequest.setLastModifiedDate(now);
        userMapper.createUser(userRegisterRequest);
        int userId=userRegisterRequest.getUserId();
        userMapper.createRolesByUserId(userId);
        return userId;
    }

    @Override
    public User getUserById(int userId) {
        return userMapper.getUserById(userId);
    }

    @Override
    public User getUserByEmail(String email) {
        return userMapper.getUserByEmail(email);
    }

    @Override
    public ResponseResult login(UserLoginRequest userLoginRequest) {
        UsernamePasswordAuthenticationToken authenticationToken =new UsernamePasswordAuthenticationToken(userLoginRequest.getEmail(),userLoginRequest.getPassword());
        Authentication authentication= authenticationManager.authenticate(authenticationToken);//會自動調用 ProvideManager 然後調用 UserDetailsService進行驗證
        if(Objects.isNull(authentication))//傳回空值代表認證失敗
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);

        //通過 使用userId生成 jwt ，返回jwt
        UserDetailsImp userDetail = (UserDetailsImp) authentication.getPrincipal();
        String userId =String.valueOf( userDetail.getUser().getUserId());
        //
        //判斷資料庫內有無token
//        UserToken userToken = userDao.getTokenByUserId(Integer.parseInt(userId) );
//        if (!Objects.isNull(userToken)){
//            Map<String,String> map=new HashMap<>();
//            map.put("token",userToken.getToken());
//            return new ResponseResult(200,"登入成功",map);
//        }


        JwtUtil jwtUtil = new JwtUtil();

        //判斷是否過期
//        if(userToken!=null &&jwtUtil.validateToken(userToken.getToken())==null){
//            String jwt = jwtUtil.createJwt(userId);
//            Map<String,String> map=new HashMap<>();
//            map.put("token",jwt);
//            userDao.updateUserToken(Integer.parseInt(userId) ,jwt);
//            return new ResponseResult(200,"登入成功",map);
//        }
        //生成token 存入 db
        String jwt = jwtUtil.createJwt(userId);

        Map<String,String> map=new HashMap<>();
        map.put("token",jwt);
        //userDao.createUserToken(Integer.parseInt(userId) ,jwt);
        return new ResponseResult(200,"登入成功",map);
    }

    @Override
    public ResponseResult logout() {
        //獲取 SecurityContextHolder中的使用者 id
        UsernamePasswordAuthenticationToken authentication= (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        UserToken principal= (UserToken)authentication.getPrincipal();
        //刪除 資料庫中的token
        userMapper.deleteUserTokenById(principal.getUserId());
        return new ResponseResult(200,"登出成功",null);
    }

    @Override
    public void updatePassword(UserPasswordUpdateRequest userPasswordUpdateRequest) {
        User user=userMapper.getUserByEmail(userPasswordUpdateRequest.getEmail());
        if(user == null){
            log.warn("信箱: {}  尚未註冊",userPasswordUpdateRequest.getEmail());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);//尚未註冊
        }
        BCryptPasswordEncoder bCryptPasswordEncoder=new BCryptPasswordEncoder();
        String encodePassword=bCryptPasswordEncoder.encode(userPasswordUpdateRequest.getNewPassword());
        userPasswordUpdateRequest.setNewPassword(encodePassword);
        userMapper.updatePassword(userPasswordUpdateRequest);
    }
}
