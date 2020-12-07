package com.bamboo.leaf.core.service.impl;

import com.bamboo.leaf.core.constant.LeafConfigure;
import com.bamboo.leaf.core.constant.LeafConstant;
import com.bamboo.leaf.core.dao.SegmentDAO;
import com.bamboo.leaf.core.entity.SegmentDO;
import com.bamboo.leaf.core.entity.SegmentRange;
import com.bamboo.leaf.core.exception.BambooLeafException;
import com.bamboo.leaf.core.service.SegmentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @description: TODO
 * @Author: Zhuzhi
 * @Date: 2020/11/30 下午11:12
 */
@Component
public class SegmentServiceImpl implements SegmentService {
    private static final Logger logger = LoggerFactory.getLogger(SegmentServiceImpl.class);

    @Resource
    SegmentDAO segmentDAO;

    @Resource
    LeafConfigure leafConfigure;

    @Override
    public SegmentRange getNextSegmentRange(String namespace) {
        // 获取NextSegmentRange的时候，有可能存在version冲突，需要重试
        for (int i = 0; i < leafConfigure.getRetry(); i++) {
            SegmentDO segmentDO = segmentDAO.selectSegment(namespace);
            if (segmentDO == null) {
                //默认插入对象
                segmentDO = new SegmentDO();
                segmentDO.setNamespace(namespace);
                segmentDO.setDelta(1);
                segmentDO.setRemainder(1);
                segmentDO.setStep(LeafConstant.DEFAULT_STEP);
                segmentDO.setLeafVal(1L);
                segmentDO.setVersion(1L);
                segmentDO.setRetry(leafConfigure.getRetry());
                int val = segmentDAO.insertSegment(segmentDO);
                //判断插入是否成功,不成功要重试
                if (val == 1) {
                    if (logger.isInfoEnabled()) {
                        logger.info("bamboo-leaf init success,namespace:{}", namespace);
                    }
                    // retry
                    continue;
                }
            }
            Long oldValue = segmentDO.getLeafVal();
            if (oldValue < 0) {
                StringBuilder message = new StringBuilder();
                message.append("bamboo-leaf  value cannot be less than zero, value = ").append(oldValue);
                message.append(", please check namespace: ").append(namespace);
                throw new BambooLeafException(message.toString());
            }
            if (oldValue > Long.MAX_VALUE - LeafConstant.DEFAULT_BUFFER) {
                StringBuilder message = new StringBuilder();
                message.append("bamboo-leaf  value overflow, value = ").append(oldValue);
                message.append(", please check table ").append(namespace);
                throw new BambooLeafException(message.toString());
            }
            Long newMaxId = oldValue + segmentDO.getStep() - 1;
            SegmentDO updateSegment = new SegmentDO();
            updateSegment.setLeafVal(newMaxId);
            updateSegment.setVersion(segmentDO.getVersion());
            updateSegment.setNamespace(namespace);
            int row = segmentDAO.updateSegment(updateSegment, oldValue);
            //判断是否更新成功
            if (row == 0) {
                // retry
                continue;
            }
            //对象转换
            SegmentRange segmentRange = convert(segmentDO);
            if (logger.isInfoEnabled()) {
                logger.info("new range is success,namespace:{},range:{}->{}", namespace, oldValue, newMaxId);
            }
            return segmentRange;
        }
        throw new BambooLeafException("Retried too many times, retryTimes = " + leafConfigure.getRetry());
    }

    private SegmentRange convert(SegmentDO segmentDO) {
        SegmentRange segmentRange = new SegmentRange(segmentDO.getLeafVal(), segmentDO.getLeafVal() + segmentDO.getStep());
        segmentRange.setRemainder(segmentDO.getRemainder() == null ? 0 : segmentDO.getRemainder());
        segmentRange.setDelta(segmentDO.getDelta() == null ? 1 : segmentDO.getDelta());
        // 默认20%加载
        segmentRange.setLoadingVal(segmentRange.getCurrentVal().get() + segmentDO.getStep() * leafConfigure.getLoadingPercent() / 100);
        return segmentRange;
    }

    public SegmentDAO getSegmentDAO() {
        return segmentDAO;
    }

    public void setSegmentDAO(SegmentDAO segmentDAO) {
        this.segmentDAO = segmentDAO;
    }
}
