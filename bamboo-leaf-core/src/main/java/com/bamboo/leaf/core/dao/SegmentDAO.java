package com.bamboo.leaf.core.dao;

import com.bamboo.leaf.core.entity.SegmentDO;
import com.bamboo.leaf.core.exception.BambooLeafException;

/**
 * @description: Segment算法DAO接口
 * @Author: Zhuzhi
 * @Date: 2020/11/30 下午1:09
 */
public interface SegmentDAO {
    /**
     * 更新Segment
     *
     * @param segmentDO  对象
     * @param oldLeafVal 对象
     * @return 返回序列下一个值
     * @throws BambooLeafException
     */
    int updateSegment(SegmentDO segmentDO, long oldLeafVal) throws BambooLeafException;

    /**
     * 新增Segment
     *
     * @param segmentDO 插入对象
     * @return 返回序列下一个值
     * @throws BambooLeafException
     */
    int insertSegment(SegmentDO segmentDO) throws BambooLeafException;

    /**
     * 查询Segment
     *
     * @param namespace 命名空间
     * @return 返回序列下一个值
     * @throws BambooLeafException
     */
    SegmentDO selectSegment(String namespace) throws BambooLeafException;

    /**
     * 重置Segment
     *
     * @param namespace 命名空间
     * @param maxVal    当前最大值
     * @param version   版本
     * @return 返回序列下一个值
     * @throws BambooLeafException
     */
    int resetSegment(String namespace, long maxVal, long version) throws BambooLeafException;
}
