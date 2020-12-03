package com.bamboo.leaf.core.dao;

import com.bamboo.leaf.core.constant.SequenceConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
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
public abstract class AbstractDAO {

    private static final Logger logger = LoggerFactory.getLogger(AbstractDAO.class);
    /**
     * 数据源
     */
    @Resource
    protected DataSource dataSource;
    /**
     * 重试次数
     */
    protected int retryTimes = SequenceConstant.DEFAULT_RETRY_TIMES;
    /**
     * workId数据库表名，并设置默认值
     */
    protected String workIdTableName = SequenceConstant.DEFAULT_WORKERID_TABLE_NAME;

    /**
     * 序列所在的表名
     */
    protected String segmentTableName = SequenceConstant.DEFAULT_TABLE_NAME;

    /**
     * 命名空间的列名
     */
    protected String nameColumnName = SequenceConstant.DEFAULT_NAME_COLUMN_NAME;

    /**
     * 当前值的列名
     */
    protected String valueColumnName = SequenceConstant.DEFAULT_VALUE_COLUMN_NAME;

    /**
     * 步长的列名
     */
    protected String stepColumnName = SequenceConstant.DEFAULT_STEP_COLUMN_NAME;

    /**
     * 重试次数的列名
     */
    protected String retryColumnName = SequenceConstant.DEFAULT_RETRY_COLUMN_NAME;

    /**
     * 最后更新时间的列名
     */
    protected String updateColumnName = SequenceConstant.DEFAULT_UPDATE_COLUMN_NAME;

    /**
     * 创建时间的列名
     */
    protected String createColumnName = SequenceConstant.DEFAULT_CREATE_COLUMN_NAME;
    /**
     * 每次id增量的列名
     */
    protected String deltaColumnName = SequenceConstant.DEFAULT_DELTA_COLUMN_NAME;
    /**
     *余数的列名
     */
    protected String remaiderColumnName = SequenceConstant.DEFAULT_REMAINDER_COLUMN_NAME;

    /**
     * 备注
     */
    protected String remarkColumnName = SequenceConstant.DEFAULT_REMARK_COLUMN_NAME;

    /**
     * 版本号
     */
    protected String versionColumnName = SequenceConstant.DEFAULT_VERSION_COLUMN_NAME;



    /**
     * SQL拼接
     */
    private volatile String selectWorkerIdSql;
    private volatile String selectWorkerIdMax;
    private volatile String insertWorkerIdSql;

    /**
     * 获取当前namespace下最大的WorkerId
     */
    protected String getSelectSqlWorkerIdMax() {
        if (selectWorkerIdMax == null) {
            synchronized (this) {
                if (selectWorkerIdMax == null) {
                    StringBuilder buffer = new StringBuilder();
                    buffer.append("select ");
                    buffer.append(" max( ").append(SequenceConstant.DEFAULT_WORKERID_COLUMN_NAME).append(" )");
                    buffer.append(" from ").append(workIdTableName);
                    buffer.append(" where ");
                    buffer.append(SequenceConstant.DEFAULT_WORKERID_NAMESPACE_COLUMN_NAME).append(" = ?  ");
                    selectWorkerIdMax = buffer.toString();
                }
            }
        }
        return selectWorkerIdMax;
    }
    /**
     * 通过namespace查询对象
     */
    protected String getSelectWorkerIdSql() {
        if (selectWorkerIdSql == null) {
            synchronized (this) {
                if (selectWorkerIdSql == null) {
                    StringBuilder buffer = new StringBuilder();
                    buffer.append("select ");
                    buffer.append(SequenceConstant.DEFAULT_WORKERID_NAMESPACE_COLUMN_NAME).append(",");
                    buffer.append(SequenceConstant.DEFAULT_WORKERID_IP_COLUMN_NAME).append(",");
                    buffer.append(SequenceConstant.DEFAULT_WORKERID_COLUMN_NAME);
                    buffer.append(" from ").append(workIdTableName);
                    buffer.append(" where ").append(SequenceConstant.DEFAULT_WORKERID_NAMESPACE_COLUMN_NAME)
                            .append(" = ? and ");
                    buffer.append(SequenceConstant.DEFAULT_WORKERID_IP_COLUMN_NAME).append(" = ? ");
                    selectWorkerIdSql = buffer.toString();
                }
            }
        }
        return selectWorkerIdSql;
    }

    protected String getInsertWorkerIdSql() {
        if (insertWorkerIdSql == null) {
            synchronized (this) {
                if (insertWorkerIdSql == null) {
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
                    insertWorkerIdSql = buffer.toString();
                }
            }
        }
        return insertWorkerIdSql;
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
    private volatile String selectSegmentSql;
    private volatile String updateSegmentSql;
    private volatile String insertSegmentSql;
    private volatile String resetSegmentSql;

    protected String getSelectSegmentSql() {
        if (selectSegmentSql == null) {
            synchronized (this) {
                if (selectSegmentSql == null) {
                    StringBuilder buffer = new StringBuilder();
                    buffer.append(" select ");

                    buffer.append(nameColumnName).append(",");
                    buffer.append(valueColumnName).append(",");
                    buffer.append(stepColumnName).append(",");
                    buffer.append(remaiderColumnName).append(",");
                    buffer.append(deltaColumnName).append(",");
                    buffer.append(versionColumnName);

                    buffer.append(" from ").append(segmentTableName);
                    buffer.append(" where ").append(nameColumnName).append(" = ?");
                    selectSegmentSql = buffer.toString();
                }
            }
        }
        return selectSegmentSql;
    }

    protected String getUpdateSegmentSql() {
        if (updateSegmentSql == null) {
            synchronized (this) {
                if (updateSegmentSql == null) {
                    StringBuilder buffer = new StringBuilder();
                    buffer.append("update ").append(segmentTableName);
                    buffer.append(" set ");
                    buffer.append(valueColumnName).append(" = ?, ");
                    buffer.append(versionColumnName).append(" = ? ");
                    buffer.append(" where ");
                    buffer.append(nameColumnName).append(" = ?  and ");
                    buffer.append(valueColumnName).append(" = ? and ");
                    buffer.append(versionColumnName).append(" = ? ");
                    updateSegmentSql = buffer.toString();
                }
            }
        }
        return updateSegmentSql;
    }

    /**
     * seq_value重置
     *
     * @return
     */
    protected String getResetSegmentSql() {
        if (resetSegmentSql == null) {
            synchronized (this) {
                if (resetSegmentSql == null) {
                    StringBuilder buffer = new StringBuilder();
                    buffer.append(" update ").append(segmentTableName);
                    buffer.append(" set ");
                    buffer.append(valueColumnName).append(" = ?, ");
                    buffer.append("  where ");
                    buffer.append(nameColumnName).append(" = ?  and ");
                    buffer.append(valueColumnName).append(" = ? and ");
                    buffer.append(versionColumnName).append(" = ? ");
                    resetSegmentSql = buffer.toString();
                }
            }
        }
        return resetSegmentSql;
    }
    /**
     * 当没有记录集时可以执行插入操作
     *
     * @return
     */
    protected String getInsertSegmentSql() {
        if (insertSegmentSql == null) {
            synchronized (this) {
                if (insertSegmentSql == null) {
                    StringBuilder buffer = new StringBuilder();
                    buffer.append("insert into ").append(segmentTableName);
                    buffer.append("( ");
                    buffer.append(nameColumnName).append(",");
                    buffer.append(valueColumnName).append(",");
                    buffer.append(remaiderColumnName).append(",");
                    buffer.append(stepColumnName).append(",");
                    buffer.append(versionColumnName).append(",");
                    buffer.append(retryColumnName).append(",");
                    buffer.append(deltaColumnName);
                    buffer.append(")");
                    buffer.append(" VALUES(?, ? ,?,?,?,?,?)");
                    insertSegmentSql = buffer.toString();
                }
            }
        }
        return insertSegmentSql;
    }
}
