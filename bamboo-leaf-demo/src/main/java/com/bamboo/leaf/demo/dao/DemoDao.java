package com.bamboo.leaf.demo.dao;

import com.bamboo.leaf.core.exception.BambooLeafException;
import com.bamboo.leaf.demo.entity.DemoDO;
import com.bamboo.leaf.demo.entity.DemoErrorDO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @description: TODO
 * @Author: Zhuzhi
 * @Date: 2021/3/14 下午9:51
 */
@Service("demoDao")
public class DemoDao {

    private final Logger logger = LoggerFactory.getLogger(DemoDao.class);

    /**
     * 数据源
     */
    @Resource
    protected DataSource dataSource;

    private volatile String insertDemoSql;
    private volatile String insertDemoErrorSql;

    /**
     * 新增
     *
     * @param demoDO 插入对象
     * @return 返回序列下一个值
     * @throws BambooLeafException
     */
    public int insertDemo(DemoDO demoDO) throws BambooLeafException {
        int val = 0;
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = dataSource.getConnection();
            stmt = conn.prepareStatement(getInsertDemoSql());
            stmt.setString(1, demoDO.getId());
            stmt.setString(2, demoDO.getNamespace());
            stmt.setString(3, demoDO.getRemark());
            val = stmt.executeUpdate();
        } catch (SQLException e) {
            logger.error("bamboo-leaf-demo is error,msg:", e);
            // 测试异常写入错误信息表
            DemoErrorDO demoErrorDO = new DemoErrorDO();
            demoErrorDO.setMessage(e.getStackTrace().toString());
            demoErrorDO.setNamespace(demoDO.getNamespace());
            demoErrorDO.setRemark(demoDO.getRemark());
            demoErrorDO.setSeq(demoDO.getId());
            insertDemoError(demoErrorDO);
        } finally {
            closeStatement(stmt);
            stmt = null;
            closeConnection(conn);
            conn = null;
        }
        return val;

    }

    /**
     * 新增
     *
     * @param demoErrorDO 插入对象
     * @return 返回序列下一个值
     * @throws BambooLeafException
     */
    public int insertDemoError(DemoErrorDO demoErrorDO) throws BambooLeafException {
        int val = 0;
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = dataSource.getConnection();
            stmt = conn.prepareStatement(getInsertDemoErrorSql());
            stmt.setString(1, demoErrorDO.getSeq());
            stmt.setString(2, demoErrorDO.getNamespace());
            stmt.setString(3, demoErrorDO.getMessage());
            stmt.setString(4, demoErrorDO.getRemark());

            val = stmt.executeUpdate();
        } catch (SQLException e) {
            logger.error("bamboo_leaf_demo_error is error,msg:", e);
        } finally {
            closeStatement(stmt);
            stmt = null;
            closeConnection(conn);
            conn = null;
        }
        return val;

    }

    private String getInsertDemoSql() {
        if (insertDemoSql == null) {
            synchronized (this) {
                if (insertDemoSql == null) {
                    StringBuilder buffer = new StringBuilder();
                    buffer.append("INSERT INTO bamboo_leaf_demo ");
                    buffer.append("(id,namespace,remark)");
                    buffer.append(" VALUES(?,?,?)");
                    insertDemoSql = buffer.toString();
                }
            }
        }
        return insertDemoSql;
    }

    private String getInsertDemoErrorSql() {
        if (insertDemoErrorSql == null) {
            synchronized (this) {
                if (insertDemoErrorSql == null) {
                    StringBuilder buffer = new StringBuilder();
                    buffer.append("INSERT INTO bamboo_leaf_demo_error ");
                    buffer.append("(seq,namespace,message,remark)");
                    buffer.append(" VALUES(?,?,?,?)");
                    insertDemoErrorSql = buffer.toString();
                }
            }
        }
        return insertDemoErrorSql;
    }

    private void closeStatement(Statement stmt) {
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

    private void closeConnection(Connection conn) {
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
