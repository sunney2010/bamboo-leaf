package com.bamboo.leaf.core.dao.impl;

import com.bamboo.leaf.core.constant.ConfigConstant;
import com.bamboo.leaf.core.constant.SequenceConstant;
import com.bamboo.leaf.core.dao.AbstractDAO;
import com.bamboo.leaf.core.dao.WorkerIdDAO;
import com.bamboo.leaf.core.exception.BambooLeafException;
import com.bamboo.leaf.core.util.PURL;
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
    public synchronized int getWorkerId(String namespace, String hostIp, PURL configURL) {
        int workerId = ConfigConstant.INIT_WORKERID;
        try {
            // 默认表名
            String workIdTableName = SequenceConstant.DEFAULT_WORKERID_TABLE_NAME;
            if (null != configURL) {
                // 获取定义表名
                workIdTableName = configURL.getParameter(ConfigConstant.HUBBLE_SEQ_WORKID_TABLENAME,
                        SequenceConstant.DEFAULT_WORKERID_TABLE_NAME);
                this.workIdTableName = workIdTableName;
            }
            // 依赖DB插入或获取WorkerId
            workerId = getWorkerId(namespace, hostIp);
        } catch (Exception e) {
            // 如果获取失败，则使用随机数备用;
            workerId= (int) (Math.random() * 300);
            logger.warn("WorkerIdResolver.resolveId is error, workerId is RANDOM,workerId={}", workerId);
        }
        return workerId;
    }

    public int getWorkerId(String namespace, String hostIp) {
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
                        workerId++;
                        boolean val = insertSql(conn, stmt, namespace, hostIp, workerId);
                        if (val) {
                            break;
                        }
                    } catch (BambooLeafException e) {
                        logger.error(" select or insert error,retryTimes:{},msg:", i, e.getMessage());
                    }
                    // 随机sleep 1-300ms
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

    protected int selectSqlWorkerIdMax(Connection conn, PreparedStatement stmt, String nameSpace)
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

    private boolean insertSql(Connection conn, PreparedStatement stmt, String nameSpace, String hostIp, int workerId)
            throws BambooLeafException {
        boolean val = false;
        try {
            conn = dataSource.getConnection();
            stmt = conn.prepareStatement(getInsertWorkerIdSql());
            stmt.setString(1, nameSpace);
            stmt.setString(2, hostIp);
            stmt.setInt(3, workerId);
            stmt.setTimestamp(4, new Timestamp(System.currentTimeMillis()));
            stmt.setTimestamp(5, new Timestamp(System.currentTimeMillis()));
            stmt.executeUpdate();
            val = true;
        } catch (SQLException e) {
            val = false;
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
