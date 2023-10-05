package com.jason.springbootmall.dao.rowMapper;

import com.jason.springbootmall.model.UserToken;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserTokenRowMapper  implements RowMapper<UserToken> {
    @Override
    public UserToken mapRow(ResultSet rs, int rowNum) throws SQLException {
        UserToken userToken=new UserToken();
        userToken.setTokenId(rs.getInt("token_Id"));
        userToken.setUserId(rs.getInt("user_Id"));
        userToken.setToken(rs.getString("token"));
        return userToken;
    }
}
