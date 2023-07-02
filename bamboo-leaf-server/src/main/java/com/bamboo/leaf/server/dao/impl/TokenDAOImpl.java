package com.bamboo.leaf.server.dao.impl;

import com.bamboo.leaf.server.dao.TokenDAO;
import com.bamboo.leaf.server.dao.entity.TokenDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * @description: TODO
 * @Author: Zhuzhi
 * @Date: 2020/11/21 下午10:21
 */
@Repository
public class TokenDAOImpl implements TokenDAO {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<TokenDO> selectAll() {
        String sql = "select id, token, appId, remark, " +
                "create_time, update_time from bamboo_leaf_token";
        return jdbcTemplate.query(sql, new TokenDAOImpl.TokenRowMapper());
    }

    @Override
    public int insertToken(TokenDO tokenDO) {
        String sql = "insert bamboo_leaf_token(token,appId,remark) " +
                "values(?,?,?)";
        return jdbcTemplate.update(sql, tokenDO.getToken(), tokenDO.getAppId(), tokenDO.getRemark());
    }

    public static class TokenRowMapper implements RowMapper<TokenDO> {
        @Override
        public TokenDO mapRow(ResultSet resultSet, int i) throws SQLException {
            TokenDO token = new TokenDO();
            token.setId(resultSet.getInt("id"));
            token.setToken(resultSet.getString("token"));
            token.setAppId(resultSet.getString("appId"));
            token.setRemark(resultSet.getString("remark"));
            token.setCreateTime(resultSet.getDate("create_time"));
            token.setUpdateTime(resultSet.getDate("update_time"));
            return token;
        }
    }
  }
