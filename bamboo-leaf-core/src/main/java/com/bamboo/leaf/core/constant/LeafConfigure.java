package com.bamboo.leaf.core.constant;

/**
 * @description: TODO
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

    private long buffer=LeafConstant.DEFAULT_BUFFER;

    public int getRetry() {
        return retry;
    }

    public void setRetry(int retry) {
        if (retry < 3) {
            this.retry=LeafConstant.DEFAULT_RETRY_TIMES;
        }else{
            this.retry = retry;
        }

    }

    public int getLoadingPercent() {
        return loadingPercent;
    }

    public void setLoadingPercent(int loadingPercent) {
        if (loadingPercent < 10) {
            this.loadingPercent=LeafConstant.DEFAULT_LOADING_PERCENT;
        }else{
            this.loadingPercent = loadingPercent;
        }
    }

    public int getStep() {
        return step;
    }

    public void setStep(int step) {
        this.step = step;
    }
}
