package com.bamboo.leaf.core.service.impl;

import com.bamboo.leaf.core.constant.Constants;
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
    @Override
    public SegmentRange getNextSegmentRange(String namespace) {
        // 获取NextSegmentRange的时候，有可能存在version冲突，需要重试
        for (int i = 0; i < Constants.RETRY; i++) {
            SegmentDO segmentDO = segmentDAO.selectSegment(namespace);
            if (segmentDO == null) {
                //默认插入对象
                segmentDO = new SegmentDO();
                segmentDO.setNamespace(namespace);
                int val = segmentDAO.insertSegment(segmentDO);
                //判断插入是否成功,不成功要重试
                if (val == 1) {
                    if (logger.isInfoEnabled()) {
                        logger.info("bamboo-leaf init success,namespace:{}", namespace);
                    }
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
            if (oldValue > Long.MAX_VALUE - Constants.BUFFER) {
                StringBuilder message = new StringBuilder();
                message.append("bamboo-leaf  value overflow, value = ").append(oldValue);
                message.append(", please check table ").append(namespace);
                throw new BambooLeafException(message.toString());
            }
            Long newMaxId = oldValue + segmentDO.getStep();
            SegmentDO updateSegment = new SegmentDO();
            updateSegment.setLeafVal(newMaxId);
            updateSegment.setVersion(segmentDO.getVersion());
            updateSegment.setNamespace(namespace);
            int row = segmentDAO.updateSegment(updateSegment, oldValue);
            //判断是否更新成功
            if (row == 1) {
                //对象转换
                SegmentRange segmentRange = convert(segmentDO);
                logger.info("getNextSegmentRange success segmentDO:{} current:{}", segmentDO, segmentRange);
                return segmentRange;
            } else {
                logger.info("getNextSegmentRange conflict segmentDO:{}", segmentDO);
            }
        }
        throw new BambooLeafException("Retried too many times, retryTimes = " + Constants.RETRY);
    }

    public SegmentRange convert(SegmentDO segmentDO) {
        SegmentRange segmentRange = new SegmentRange(segmentDO.getLeafVal(), segmentDO.getLeafVal() + segmentDO.getStep());
        segmentRange.setRemainder(segmentDO.getRemainder() == null ? 0 : segmentDO.getRemainder());
        segmentRange.setDelta(segmentDO.getDelta() == null ? 1 : segmentDO.getDelta());
        // 默认20%加载
        segmentRange.setLoadingVal(segmentRange.getCurrentVal().get() + segmentDO.getStep() * Constants.LOADING_PERCENT / 100);
        return segmentRange;
    }
}
