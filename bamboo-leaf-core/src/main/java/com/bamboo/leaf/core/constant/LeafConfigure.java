package com.bamboo.leaf.core.constant;

/**
 * @description: 参数
 * @Author: Zhuzhi
 * @Date: 2020/12/7 下午12:52
 */
public class LeafConfigure {
    /**
     * 配置重试次数
     */
    private int retry = LeafConstant.DEFAULT_RETRY_TIMES;
    /**
     * 配置加载比例
     */
    private int loadingPercent=LeafConstant.DEFAULT_LOADING_PERCENT;
    /**
     * 配置步长
     */
    private int step=LeafConstant.DEFAULT_STEP;

    public int getRetry() {
        return retry;
    }

    public void setRetry(int retry) {
        if (retry < LeafConstant.RETRY_TIMES_MIN || retry > LeafConstant.RETRY_TIMES_MAX) {
            this.retry = LeafConstant.DEFAULT_RETRY_TIMES;
        } else {
            this.retry = retry;
        }
    }

    public int getLoadingPercent() {
        return loadingPercent;
    }

    public void setLoadingPercent(int loadingPercent) {
        if (loadingPercent < LeafConstant.LOADING_PERCENT_MIN || loadingPercent > LeafConstant.LOADING_PERCENT_MAX) {
            this.loadingPercent = LeafConstant.DEFAULT_LOADING_PERCENT;
        } else {
            this.loadingPercent = loadingPercent;
        }
    }

    public int getStep() {
        return step;
    }

    public void setStep(int step) {
        if (step < LeafConstant.STEP_MIN || step > LeafConstant.STEP_MAX) {
            this.step = LeafConstant.DEFAULT_STEP;
        } else {
            this.step = step;
        }
    }
}
