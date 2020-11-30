package com.bamboo.leaf.core.dao.impl;

import com.bamboo.leaf.core.dao.AbstractDAO;
import com.bamboo.leaf.core.dao.SegmentDAO;
import com.bamboo.leaf.core.exception.BambooLeafException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

/**
 * @description: TODO
 * @Author: Zhuzhi
 * @Date: 2020/11/30 下午1:10
 */
public class SegmentDAOImpl extends AbstractDAO implements SegmentDAO {
    private static final Logger logger = LoggerFactory.getLogger(SegmentDAOImpl.class);

    protected boolean insertSql(Connection conn, String name, long initVal, long step, long retryTimes) throws BambooLeafException {
        boolean val = false;
        PreparedStatement stmt = null;
        try {
            conn = dataSource.getConnection();
            stmt = conn.prepareStatement(getInsertSegmentSql());
            stmt.setString(1, name);
            stmt.setLong(2, initVal);
            stmt.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
            stmt.setLong(4, step);
            stmt.setLong(5, retryTimes);
            stmt.executeUpdate();
            val = true;

        } catch (SQLException e) {
            val = false;
            throw new BambooLeafException(e);
        } finally {
            closeStatement(stmt);
            stmt = null;
            closeConnection(conn);
            conn = null;
        }
        return val;
    }

}
