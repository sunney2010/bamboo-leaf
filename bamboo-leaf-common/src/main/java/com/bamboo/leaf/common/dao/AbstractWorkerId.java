package com.bamboo.leaf.common.dao;

import com.bamboo.leaf.common.constant.SequenceConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @description: TODO
 * @Author: Zhuzhi
 * @Date: 2020/11/30 上午12:04
 */
public abstract class AbstractWorkerId {

    private static final Logger logger = LoggerFactory.getLogger(AbstractWorkerId.class);
    /**
     * 重试次数
     */
    protected int retryTimes = SequenceConstant.DEFAULT_RETRY_TIMES;
    /**
     * workId数据库表名，并设置默认值
     */
    protected String workIdTableName = SequenceConstant.DEFAULT_WORKERID_TABLE_NAME;
    /**
     * 数据源
     */
    protected DataSource dataSource;

    /**
     * SQL拼接
     */
    private volatile String selectSql;
    private volatile String selectSqlMax;
    private volatile String insertSql;

    /**
     * 获取当前namespace下最大的WorkerId
     */
    protected String getSelectSqlWorkerIdMax() {
        if (selectSqlMax == null) {
            synchronized (this) {
                if (selectSqlMax == null) {
                    StringBuilder buffer = new StringBuilder();
                    buffer.append("select ");
                    buffer.append(" max( ").append(SequenceConstant.DEFAULT_WORKERID_COLUMN_NAME).append(" )");
                    buffer.append(" from ").append(workIdTableName);
                    buffer.append(" where ");
                    buffer.append(SequenceConstant.DEFAULT_WORKERID_NAMESPACE_COLUMN_NAME).append(" = ?  ");
                    selectSqlMax = buffer.toString();
                }
            }
        }
        return selectSqlMax;
    }
    /**
     * 通过namespace查询对象
     */
    protected String getSelectSql() {
        if (selectSql == null) {
            synchronized (this) {
                if (selectSql == null) {
                    StringBuilder buffer = new StringBuilder();
                    buffer.append("select ");
                    buffer.append(SequenceConstant.DEFAULT_WORKERID_NAMESPACE_COLUMN_NAME).append(",");
                    buffer.append(SequenceConstant.DEFAULT_WORKERID_IP_COLUMN_NAME).append(",");
                    buffer.append(SequenceConstant.DEFAULT_WORKERID_COLUMN_NAME);
                    buffer.append(" from ").append(workIdTableName);
                    buffer.append(" where ").append(SequenceConstant.DEFAULT_WORKERID_NAMESPACE_COLUMN_NAME)
                            .append(" = ? and ");
                    buffer.append(SequenceConstant.DEFAULT_WORKERID_IP_COLUMN_NAME).append(" = ? ");
                    selectSql = buffer.toString();
                }
            }
        }
        return selectSql;
    }

    protected String getInsertSql() {
        if (insertSql == null) {
            synchronized (this) {
                if (insertSql == null) {
                    StringBuilder buffer = new StringBuilder();
                    buffer.append("insert into ").append(workIdTableName);
                    buffer.append(" ( ");
                    buffer.append(SequenceConstant.DEFAULT_WORKERID_NAMESPACE_COLUMN_NAME).append(",");
                    buffer.append(SequenceConstant.DEFAULT_WORKERID_IP_COLUMN_NAME).append(",");
                    buffer.append(SequenceConstant.DEFAULT_WORKERID_COLUMN_NAME).append(",");
                    buffer.append(SequenceConstant.DEFAULT_WORKERID_CREATETIME_COLUMN_NAME).append(",");
                    buffer.append(SequenceConstant.DEFAULT_WORKERID_UPDATETIME_COLUMN_NAME);
                    buffer.append(" ) ");
                    buffer.append(" VALUES(?, ? ,?,?,?)");
                    insertSql = buffer.toString();
                }
            }
        }
        return insertSql;
    }

    protected static void closeResultSet(ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                logger.debug("Could not close JDBC ResultSet", e);
            } catch (Throwable e) {
                logger.debug("Unexpected exception on closing JDBC ResultSet", e);
            }
        }
    }

    protected static void closeStatement(Statement stmt) {
        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException e) {
                logger.debug("Could not close JDBC Statement", e);
            } catch (Throwable e) {
                logger.debug("Unexpected exception on closing JDBC Statement", e);
            }
        }
    }

    protected static void closeConnection(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                logger.debug("Could not close JDBC Connection", e);
            } catch (Throwable e) {
                logger.debug("Unexpected exception on closing JDBC Connection", e);
            }
        }
    }
}
