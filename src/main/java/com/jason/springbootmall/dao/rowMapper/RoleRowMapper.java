package com.jason.springbootmall.dao.rowMapper;


import com.jason.springbootmall.model.Role;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RoleRowMapper  implements RowMapper<Role> {
    @Override
    public Role mapRow(ResultSet rs, int rowNum) throws SQLException {
        Role role = new Role();
        role.setRoleId(rs.getInt("role_Id"));
        role.setRoleName(rs.getString("role_Name"));
        return role;
    }
}
