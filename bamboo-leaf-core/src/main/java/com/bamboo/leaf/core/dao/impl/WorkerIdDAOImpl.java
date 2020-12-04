package com.bamboo.leaf.core.dao.impl;

import com.bamboo.leaf.core.constant.ConfigConstant;
import com.bamboo.leaf.core.constant.SequenceConstant;
import com.bamboo.leaf.core.dao.AbstractDAO;
import com.bamboo.leaf.core.dao.WorkerIdDAO;
import com.bamboo.leaf.core.exception.BambooLeafException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.*;

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
    public synchronized int getWorkerId(String namespace, String hostIp) {
        int workerId = ConfigConstant.INIT_WORKERID;
        try {
            // 依赖DB插入或获取WorkerId
            workerId = queryWorkerId(namespace, hostIp);
        } catch (Exception e) {
            // 如果获取失败，则使用随机数备用;
            workerId = (int) (Math.random() * 300);
            logger.warn("getWorkerId is error, workerId is RANDOM,workerId={}", workerId);
        }
        return workerId;
    }

    private int queryWorkerId(String namespace, String hostIp) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        int workerId = ConfigConstant.INIT_WORKERID;
        try {
            conn = dataSource.getConnection();
            stmt = conn.prepareStatement(getSelectWorkerIdSql());
            stmt.setString(1, namespace);
            stmt.setString(2, hostIp);
            rs = stmt.executeQuery();
            if (rs == null || !rs.next()) {
                // 异常重试
                for (int i = 0; i < retryTimes + 1; ++i) {
                    try {
                        workerId = selectSqlWorkerIdMax(conn, stmt, namespace);
                        //在前最大的workerId+1
                        workerId++;
                        int val = insertSql(conn, stmt, namespace, hostIp, workerId);
                        if (val==1) {
                            break;
                        }
                    } catch (BambooLeafException e) {
                        logger.error(" select or insert error,retryTimes:{},msg:{}", i, e.getMessage());
                    }
                    // 随机sleep 1-300ms,retry
                    int sleep = (int) (Math.random() * 300);
                    Thread.sleep(sleep);
                }
            } else {
                workerId = rs.getInt(3);
            }
            if (workerId < ConfigConstant.INIT_WORKERID || workerId > ConfigConstant.MAX_WORKERID) {
                logger.error(" workerId is scope [{}-{}],workerId:{},retryTimes:{}!", ConfigConstant.INIT_WORKERID,
                        ConfigConstant.MAX_WORKERID, workerId, retryTimes);
                throw new BambooLeafException(
                        "workerId is scope [" + ConfigConstant.INIT_WORKERID + "-" + ConfigConstant.MAX_WORKERID + "]");
            }
        } catch (Exception e) {
            logger.error("DBWorkerIdResolver.getWorkerId is error,msg:{}", e);
            throw new BambooLeafException(e);
        } finally {
            closeConnection(conn);
            conn = null;
            closeStatement(stmt);
            stmt = null;
            closeResultSet(rs);
            rs = null;
        }
        return workerId;
    }

    private int selectSqlWorkerIdMax(Connection conn, PreparedStatement stmt, String nameSpace)
            throws BambooLeafException {
        int val = 0;
        ResultSet rs = null;
        try {
            conn = dataSource.getConnection();
            stmt = conn.prepareStatement(getSelectSqlWorkerIdMax());
            stmt.setString(1, nameSpace);
            rs = stmt.executeQuery();
            if (rs == null || !rs.next()) {
                val = 0;
            } else {
                val = rs.getInt(1);
            }
        } catch (SQLException e) {
            throw new BambooLeafException(e);
        } finally {
            closeConnection(conn);
            conn = null;
            closeStatement(stmt);
            stmt = null;
            closeResultSet(rs);
            rs = null;
        }
        return val;
    }

    private int insertSql(Connection conn, PreparedStatement stmt, String nameSpace, String hostIp, int workerId)
            throws BambooLeafException {
        int val = 0;
        try {
            conn = dataSource.getConnection();
            stmt = conn.prepareStatement(getInsertWorkerIdSql());
            stmt.setString(1, nameSpace);
            stmt.setString(2, hostIp);
            stmt.setInt(3, workerId);
            val = stmt.executeUpdate();

        } catch (SQLException e) {
            throw new BambooLeafException(e);
        } finally {
            closeConnection(conn);
            conn = null;
            closeStatement(stmt);
            stmt = null;
        }
        return val;
    }
}
