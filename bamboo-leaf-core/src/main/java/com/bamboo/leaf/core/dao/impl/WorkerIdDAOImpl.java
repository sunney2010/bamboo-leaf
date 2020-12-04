package com.bamboo.leaf.core.dao.impl;

import com.bamboo.leaf.core.dao.AbstractDAO;
import com.bamboo.leaf.core.dao.WorkerIdDAO;
import com.bamboo.leaf.core.exception.BambooLeafException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @description: TODO
 * @Author: Zhuzhi
 * @Date: 2020/11/29 下午11:32
 */
public class WorkerIdDAOImpl extends AbstractDAO implements WorkerIdDAO {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public WorkerIdDAOImpl() {

    }

    public WorkerIdDAOImpl(DataSource dataSource) {

        super.dataSource = dataSource;
    }


    @Override
    public int insertWorkerId(String namespace, String hostIp, int workerId) {
        int val = 0;
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = dataSource.getConnection();
            stmt = conn.prepareStatement(getInsertWorkerIdSql());
            stmt.setString(1, namespace);
            stmt.setString(2, hostIp);
            stmt.setInt(3, workerId);
            val = stmt.executeUpdate();
        } catch (SQLException e) {
            logger.error("insertWorkerId is error,msg:", e);
        } finally {
            closeStatement(stmt);
            stmt = null;
            closeConnection(conn);
            conn = null;
        }
        return val;
    }

    @Override
    public int queryWorkerId(String namespace, String hostIp) {
        int val = 0;
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            conn = dataSource.getConnection();
            stmt = conn.prepareStatement(getSelectWorkerIdSql());
            stmt.setString(1, namespace);
            stmt.setString(2, hostIp);
            rs = stmt.executeQuery();
            if (rs == null || !rs.next()) {
                val = 0;
            } else {
                val = rs.getInt(3);
            }
        } catch (SQLException e) {
            logger.error("queryWorkerId is error,msb", e);
            throw new BambooLeafException(e);
        } finally {
            closeStatement(stmt);
            stmt = null;
            closeConnection(conn);
            conn = null;
        }
        return val;
    }

    @Override
    public int queryMaxWorkerId(String namespace, String hostIp) {
        int val = 0;
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            conn = dataSource.getConnection();
            stmt = conn.prepareStatement(getSelectMaxWorkerIdSql());
            stmt.setString(1, namespace);
            rs = stmt.executeQuery();
            if (rs == null || !rs.next()) {
                val = 0;
            } else {
                val = rs.getInt(1);
            }
        } catch (SQLException e) {
            logger.error("queryMaxWorkerId is error,msb", e);
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
