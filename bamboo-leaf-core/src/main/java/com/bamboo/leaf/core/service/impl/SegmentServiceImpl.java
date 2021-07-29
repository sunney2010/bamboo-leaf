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
import java.util.concurrent.atomic.AtomicLong;

/**
 * @description: SegmentService实现类
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
    public synchronized SegmentRange getNextSegmentRange(String namespace, long maxVal, Integer step) {

        // 获取NextSegmentRange的时候，有可能存在version冲突，需要重试
        for (int i = 0; i < leafConfigure.getRetry(); i++) {
            SegmentDO segmentDO = segmentDAO.selectSegment(namespace);
            if (segmentDO == null) {
                //默认插入对象
                segmentDO = new SegmentDO();
                segmentDO.setNamespace(namespace);
                segmentDO.setDelta(1);
                segmentDO.setRemainder(0);
                // 初始化时用默认或配置的步长
                segmentDO.setStep(leafConfigure.getStep());
                segmentDO.setLeafVal(LeafConstant.DEFAULT_VALUE);
                segmentDO.setVersion(1L);
                segmentDO.setRetry(leafConfigure.getRetry());
                int val = segmentDAO.insertSegment(segmentDO);
                //判断插入是否成功,不成功要重试
                if (val == 1) {
                    if (logger.isInfoEnabled()) {
                        logger.info("bamboo-leaf init success,namespace:{},step:{}", namespace, step);
                    }
                    // retry
                    continue;
                }
            }
            // 动态步长不存存时用当前步长
            if (null == step || step <= 0) {
                step = segmentDO.getStep();
            }
            Long oldValue = segmentDO.getLeafVal();
            if (oldValue < 0) {
                StringBuilder message = new StringBuilder();
                message.append(" bamboo-leaf  value cannot be less than zero, value = ").append(oldValue);
                message.append(", please check namespace: ").append(namespace);
                throw new BambooLeafException(message.toString());
            }
            if (oldValue > Long.MAX_VALUE - LeafConstant.DEFAULT_BUFFER) {
                StringBuilder message = new StringBuilder();
                message.append("bamboo-leaf  value overflow, value = ").append(oldValue);
                message.append(", please check table ").append(namespace);
                throw new BambooLeafException(message.toString());
            }
            Long newMaxVal = oldValue + step;
            // reset
            if (maxVal > 0 && newMaxVal > maxVal) {
                int result = segmentDAO.resetSegment(namespace, oldValue, segmentDO.getVersion());
                //判断执行结果
                if (result == 1) {
                    if (logger.isWarnEnabled()) {
                        logger.warn("resetSeq is success,namespace:{},curVal:{},maxVal:{}", namespace, oldValue, maxVal);
                    }
                }
                continue;
            }

            SegmentDO updateSegment = new SegmentDO();
            updateSegment.setLeafVal(newMaxVal);
            updateSegment.setVersion(segmentDO.getVersion());
            updateSegment.setNamespace(namespace);
            //更新动态步长
            updateSegment.setStep(step);
            int row = segmentDAO.updateSegment(updateSegment, oldValue);
            //判断是否更新成功
            if (row == 0) {
                // retry
                logger.warn("bamboo-leaf apply range retry,namespace:{},time:{}", namespace, i + 1);
                sleep();
                continue;
            }
            //对象转换
            SegmentRange segmentRange = convert(segmentDO);
            if (logger.isInfoEnabled()) {
                logger.info("new range is success,namespace:{},step:{},range:{}->{}", namespace, step, oldValue + 1, newMaxVal);
            }
            return segmentRange;
        }
        String msg = "Retried too many times, namespace=" + namespace + ", retryTimes = " + leafConfigure.getRetry();
        logger.error(msg);
        throw new BambooLeafException(msg);
    }

    /**
     * 对象转换
     *
     * @param segmentDO 源对象
     * @return
     */
    private SegmentRange convert(SegmentDO segmentDO) {
        SegmentRange segmentRange = new SegmentRange();
        segmentRange.setNamespace(segmentDO.getNamespace());
        segmentRange.setMaxId(segmentDO.getLeafVal() + segmentDO.getStep());
        segmentRange.setCurrentVal(new AtomicLong(segmentDO.getLeafVal()));
        segmentRange.setRemainder(segmentDO.getRemainder() == null ? 0 : segmentDO.getRemainder());
        segmentRange.setDelta(segmentDO.getDelta() == null ? 1 : segmentDO.getDelta());
        segmentRange.setStep(segmentDO.getStep());
        // current Second
        segmentRange.setTime(System.currentTimeMillis() / 1000);
        // 默认75%加载
        segmentRange.setLoadingVal(segmentRange.getCurrentVal().get() + segmentDO.getStep() * leafConfigure.getLoadingPercent() / 100);
        return segmentRange;
    }

    /**
     * 随机休息
     */
    private void sleep() {
        try {
            //随机休息[10~100]毫秒
            int time = (int) (Math.random() * (100 - 10) + 10);
            Thread.sleep(time);
        } catch (InterruptedException e) {
            logger.error("Thread.sleep error", e);
        }
    }

    public SegmentDAO getSegmentDAO() {
        return segmentDAO;
    }

    public void setSegmentDAO(SegmentDAO segmentDAO) {
        this.segmentDAO = segmentDAO;
    }
}
