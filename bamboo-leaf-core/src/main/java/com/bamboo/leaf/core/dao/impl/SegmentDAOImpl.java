package com.bamboo.leaf.core.dao.impl;

import com.bamboo.leaf.core.dao.AbstractDAO;
import com.bamboo.leaf.core.dao.SegmentDAO;
import com.bamboo.leaf.core.entity.SegmentDO;
import com.bamboo.leaf.core.exception.BambooLeafException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @description: SegmentDAO
 * @Author: Zhuzhi
 * @Date: 2020/11/30 下午1:10
 */
public class SegmentDAOImpl extends AbstractDAO implements SegmentDAO {

    private static final Logger logger = LoggerFactory.getLogger(SegmentDAOImpl.class);

    @Override
    public int updateSegment(SegmentDO segmentDO,long oldLeafVal) throws BambooLeafException {
        int val = 0;
        try {
            val = this.updateSegmentSql(segmentDO,oldLeafVal);
        } catch (BambooLeafException e) {
            logger.error("updateSegment is error,msb", e);
            throw e;
        }
        return val;
    }

    @Override
    public int insertSegment(SegmentDO segmentDO) throws BambooLeafException {
        int val = 0;
        try {
            val = this.insertSegmentSql(segmentDO);
        } catch (BambooLeafException e) {
            logger.error("insertSegment is error,msb", e);
            throw e;
        }
        return val;
    }

    @Override
    public SegmentDO selectSegment(String namespace) throws BambooLeafException {
        SegmentDO segmentDO = null;
        try {
            segmentDO = this.selectSegmentSql(namespace);
        } catch (BambooLeafException e) {
            logger.error("insertSegment is error,msb", e);
            throw e;
        }
        return segmentDO;
    }

    @Override
    public int resetSegment(String namespace, long maxVal, long version) throws BambooLeafException {
        int val = 0;
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = dataSource.getConnection();
            stmt = conn.prepareStatement(getResetSegmentSql());
            stmt.setLong(1, 0);
            stmt.setLong(2, version + 1);
            stmt.setString(3, namespace);
            stmt.setLong(4, maxVal);
            stmt.setLong(5, version);
            val = stmt.executeUpdate();
        } catch (SQLException e) {
            logger.error("resetSegment is error,msg", e);
            throw new BambooLeafException(e);
        } finally {
            closeStatement(stmt);
            stmt = null;
            closeConnection(conn);
            conn = null;
        }
        return val;
    }

    /**
     * 初始化当前namespace数据段
     * @param segmentDO
     * @return
     * @throws BambooLeafException
     */
    private int insertSegmentSql(SegmentDO segmentDO) throws BambooLeafException {
        int val = 0;
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = dataSource.getConnection();
            stmt = conn.prepareStatement(getInsertSegmentSql());
            stmt.setString(1, segmentDO.getNamespace());
            stmt.setLong(2, segmentDO.getLeafVal());
            stmt.setInt(3, segmentDO.getRemainder());
            stmt.setInt(4, segmentDO.getStep());
            stmt.setLong(5, segmentDO.getVersion());
            stmt.setInt(6, segmentDO.getRetry());
            stmt.setInt(7, segmentDO.getDelta());
            val = stmt.executeUpdate();
        } catch (SQLException e) {
            logger.error("insertSegmentSql is error,msb", e);
            throw new BambooLeafException(e);
        } finally {
            closeStatement(stmt);
            stmt = null;
            closeConnection(conn);
            conn = null;
        }
        return val;
    }

    /**
     *
     * @param segmentDO
     * @param oldLeafVal
     * @return
     * @throws BambooLeafException
     */
    private int updateSegmentSql(SegmentDO segmentDO,long oldLeafVal) throws BambooLeafException {
        int val = 0;
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = dataSource.getConnection();
            stmt = conn.prepareStatement(getUpdateSegmentSql());
            stmt.setLong(1, segmentDO.getLeafVal());
            stmt.setLong(2, segmentDO.getVersion()+1);
            stmt.setLong(3, segmentDO.getStep());
            stmt.setString(4, segmentDO.getNamespace());
            stmt.setLong(5, oldLeafVal);
            stmt.setLong(6, segmentDO.getVersion());
            val = stmt.executeUpdate();
        } catch (SQLException e) {
            logger.error("updateSegmentSql is error,msg", e);
            throw new BambooLeafException(e);
        } finally {
            closeStatement(stmt);
            stmt = null;
            closeConnection(conn);
            conn = null;
        }
        return val;
    }

    /**
     *
     * @param namespace
     * @return
     * @throws BambooLeafException
     */
    private SegmentDO selectSegmentSql(String namespace) throws BambooLeafException {
        SegmentDO segmentDO = null;
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            conn = dataSource.getConnection();
            stmt = conn.prepareStatement(getSelectSegmentSql());
            stmt.setString(1, namespace);
            rs = stmt.executeQuery();
            if (rs == null || !rs.next()) {
                return null;
            } else {
                segmentDO = new SegmentDO();
                segmentDO.setNamespace(rs.getString(1));
                segmentDO.setLeafVal(rs.getLong(2));
                segmentDO.setStep(rs.getInt(3));
                segmentDO.setRemainder(rs.getInt(4));
                segmentDO.setDelta(rs.getInt(5));
                segmentDO.setVersion(rs.getLong(6));
            }
        } catch (SQLException e) {
            logger.error("updateSegmentSql is error,msb", e);
            throw new BambooLeafException(e);
        } finally {
            closeStatement(stmt);
            stmt = null;
            closeConnection(conn);
            conn = null;
        }
        return segmentDO;
    }
}
