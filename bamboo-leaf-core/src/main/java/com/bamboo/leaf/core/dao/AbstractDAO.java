package com.bamboo.leaf.core.dao;

import com.bamboo.leaf.core.constant.TableConfigure;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.annotation.Resource;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @description: DAO抽象类
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
    @Resource
    protected TableConfigure tableConfigure;

    /**
     * SQL拼接
     */
    private volatile String selectWorkerIdSql;
    private volatile String selectMaxWorkerIdSql;
    private volatile String insertWorkerIdSql;

    /**
     * 获取当前namespace下最大的WorkerId
     */
    protected String getSelectMaxWorkerIdSql() {
        if (selectMaxWorkerIdSql == null) {
            synchronized (this) {
                if (selectMaxWorkerIdSql == null) {
                    StringBuilder buffer = new StringBuilder();
                    buffer.append("select ");
                    buffer.append(" max( ").append(tableConfigure.getWorkerIdColumnName()).append(" )");
                    buffer.append(" from ").append(tableConfigure.getWorkIdTableName());
                    buffer.append(" where ");
                    buffer.append(tableConfigure.getNamespaceColumnName()).append(" = ?  ");
                    selectMaxWorkerIdSql = buffer.toString();
                }
            }
        }
        return selectMaxWorkerIdSql;
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
                    buffer.append(tableConfigure.getNamespaceColumnName()).append(",");
                    buffer.append(tableConfigure.getHostIpColumnName()).append(",");
                    buffer.append(tableConfigure.getWorkerIdColumnName());
                    buffer.append(" from ").append(tableConfigure.getWorkIdTableName());
                    buffer.append(" where ");
                    buffer.append(tableConfigure.getNamespaceColumnName()).append(" = ? and ");
                    buffer.append(tableConfigure.getHostIpColumnName()).append(" = ? ");
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
                    buffer.append("insert into ").append(tableConfigure.getWorkIdTableName());
                    buffer.append(" ( ");
                    buffer.append(tableConfigure.getNamespaceColumnName()).append(",");
                    buffer.append(tableConfigure.getHostIpColumnName()).append(",");
                    buffer.append(tableConfigure.getWorkerIdColumnName());
                    buffer.append(" ) ");
                    buffer.append(" VALUES(?, ? ,?)");
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

    /**
     *获取查询Segment的SQL
     * @return
     */
    protected String getSelectSegmentSql() {
        if (selectSegmentSql == null) {
            synchronized (this) {
                if (selectSegmentSql == null) {
                    StringBuilder buffer = new StringBuilder();
                    buffer.append(" select ");

                    buffer.append(tableConfigure.getNamespaceColumnName()).append(",");
                    buffer.append(tableConfigure.getLeafValueColumnName()).append(",");
                    buffer.append(tableConfigure.getStepColumnName()).append(",");
                    buffer.append(tableConfigure.getRemaiderColumnName()).append(",");
                    buffer.append(tableConfigure.getDeltaColumnName()).append(",");
                    buffer.append(tableConfigure.getVersionColumnName());

                    buffer.append(" from ").append(tableConfigure.getSegmentTableName());
                    buffer.append(" where ").append(tableConfigure.getNamespaceColumnName()).append(" = ?");
                    selectSegmentSql = buffer.toString();
                }
            }
        }
        return selectSegmentSql;
    }

    /**
     * 获取更新Segment的SQL
     * @return
     */
    protected String getUpdateSegmentSql() {
        if (updateSegmentSql == null) {
            synchronized (this) {
                if (updateSegmentSql == null) {
                    StringBuilder buffer = new StringBuilder();
                    buffer.append("update ").append(tableConfigure.getSegmentTableName());
                    buffer.append(" set ");
                    buffer.append(tableConfigure.getLeafValueColumnName()).append(" = ?, ");
                    buffer.append(tableConfigure.getVersionColumnName()).append(" = ?, ");
                    buffer.append(tableConfigure.getStepColumnName()).append(" = ? ");
                    buffer.append(" where ");
                    buffer.append(tableConfigure.getNamespaceColumnName()).append(" = ?  and ");
                    buffer.append(tableConfigure.getLeafValueColumnName()).append(" = ? and ");
                    buffer.append(tableConfigure.getVersionColumnName()).append(" = ? ");
                    updateSegmentSql = buffer.toString();
                }
            }
        }
        return updateSegmentSql;
    }

    /**
     * 获取重置Segment的SQL
     *
     * @return
     */
    protected String getResetSegmentSql() {
        if (resetSegmentSql == null) {
            synchronized (this) {
                if (resetSegmentSql == null) {
                    StringBuilder buffer = new StringBuilder();
                    buffer.append(" update ").append(tableConfigure.getSegmentTableName());
                    buffer.append(" set ");
                    buffer.append(tableConfigure.getLeafValueColumnName()).append(" = ?, ");
                    buffer.append(tableConfigure.getVersionColumnName()).append(" = ? ");
                    buffer.append("  where ");
                    buffer.append(tableConfigure.getNamespaceColumnName()).append(" = ?  and ");
                    buffer.append(tableConfigure.getLeafValueColumnName()).append(" = ? and ");
                    buffer.append(tableConfigure.getVersionColumnName()).append(" = ? ");
                    resetSegmentSql = buffer.toString();
                }
            }
        }
        return resetSegmentSql;
    }
    /**
     * 获取插入Segment的SQL
     *
     * @return
     */
    protected String getInsertSegmentSql() {
        if (insertSegmentSql == null) {
            synchronized (this) {
                if (insertSegmentSql == null) {
                    StringBuilder buffer = new StringBuilder();
                    buffer.append("insert into ").append(tableConfigure.getSegmentTableName());
                    buffer.append("( ");
                    buffer.append(tableConfigure.getNamespaceColumnName()).append(",");
                    buffer.append(tableConfigure.getLeafValueColumnName()).append(",");
                    buffer.append(tableConfigure.getRemaiderColumnName()).append(",");
                    buffer.append(tableConfigure.getStepColumnName()).append(",");
                    buffer.append(tableConfigure.getVersionColumnName()).append(",");
                    buffer.append(tableConfigure.getRetryColumnName()).append(",");
                    buffer.append(tableConfigure.getDeltaColumnName());
                    buffer.append(")");
                    buffer.append(" VALUES(?, ? ,?,?,?,?,?)");
                    insertSegmentSql = buffer.toString();
                }
            }
        }
        return insertSegmentSql;
    }

    public TableConfigure getTableConfigure() {
        return tableConfigure;
    }

    public void setTableConfigure(TableConfigure tableConfigure) {
        this.tableConfigure = tableConfigure;
    }
}
