package com.bamboo.leaf.server.autoconfigure;

import com.bamboo.leaf.core.constant.LeafConstant;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @description: 配置属性类
 * @Author: Zhuzhi
 * @Date: 2020/11/30 下午1:10
 */
@Component
@ConfigurationProperties("bamboo.leaf.configure")
public class LeafProperties {
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
        this.retry = retry;
    }

    public int getLoadingPercent() {
        return loadingPercent;
    }

    public void setLoadingPercent(int loadingPercent) {
        this.loadingPercent = loadingPercent;
    }

    public int getStep() {
        return step;
    }

    public void setStep(int step) {
        this.step = step;
    }
}