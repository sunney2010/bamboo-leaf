package com.bamboo.leaf.core.constant;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @description: 参数
 * @Author: Zhuzhi
 * @Date: 2020/12/7 下午12:52
 */
public class LeafConfigure {
    private static final Logger logger = LoggerFactory.getLogger(LeafConfigure.class);
    /**
     * 配置重试次数
     */
    private int retry = LeafConstant.DEFAULT_RETRY_TIMES;
    /**
     * 配置加载比例
     */
    private int loadingPercent = LeafConstant.DEFAULT_LOADING_PERCENT;
    /**
     * 配置步长
     */
    private int step = LeafConstant.DEFAULT_STEP;

    public int getRetry() {
        return retry;
    }

    public void setRetry(int retry) {
        // 重置次数小于最小值或大于最大值获取默认重试次数
        if (retry < LeafConstant.RETRY_TIMES_MIN || retry > LeafConstant.RETRY_TIMES_MAX) {
            // 默认10次
            this.retry = LeafConstant.DEFAULT_RETRY_TIMES;
            if (logger.isWarnEnabled()) {
                logger.warn("current retry is {},not in[{},{}],set default retry:{}",
                        retry, LeafConstant.RETRY_TIMES_MIN, LeafConstant.RETRY_TIMES_MAX, LeafConstant.DEFAULT_RETRY_TIMES);
            }
        } else {
            this.retry = retry;
        }
    }

    public int getLoadingPercent() {
        return loadingPercent;
    }

    public void setLoadingPercent(int loadingPercent) {
        // 加载因素小于最小值或大于最大值获取默认载因素
        if (loadingPercent < LeafConstant.LOADING_PERCENT_MIN || loadingPercent > LeafConstant.LOADING_PERCENT_MAX) {
            // 默认加载因素75%
            this.loadingPercent = LeafConstant.DEFAULT_LOADING_PERCENT;
            if (logger.isWarnEnabled()) {
                logger.warn("current loadingPercent is {},not in[{},{}],set default loadingPercent:{}",
                        retry, LeafConstant.LOADING_PERCENT_MIN, LeafConstant.LOADING_PERCENT_MAX, LeafConstant.DEFAULT_LOADING_PERCENT);
            }
        } else {
            this.loadingPercent = loadingPercent;
        }
    }

    public int getStep() {
        return step;
    }

    public void setStep(int step) {
        // 步长小于最小值或大于最大值获取默认步长
        if (step < LeafConstant.STEP_MIN || step > LeafConstant.STEP_MAX) {
            //默认步长
            this.step = LeafConstant.DEFAULT_STEP;
            if (logger.isWarnEnabled()) {
                logger.warn("current step is {},not in[{},{}],set default step:{}",
                        retry, LeafConstant.STEP_MIN, LeafConstant.STEP_MAX, LeafConstant.DEFAULT_STEP);
            }
        } else {
            this.step = step;
        }
    }
}
