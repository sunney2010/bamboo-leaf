package com.bamboo.leaf.core.entity;

import com.bamboo.leaf.core.constant.LeafConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.atomic.AtomicLong;

/**
 * Segment区间实体
 *
 * @author zhuzhi
 * @date 2020/11/19
 */
public class SegmentRange {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private static int MAX_TIME = 40;
    private static int MIN_TIME = 20;

    /**
     * 当前段最大值
     */
    private long maxId;
    /**
     * 当前值
     */
    private AtomicLong currentVal;

    /**
     * 预加载值
     */
    private long loadingVal;
    /**
     * increment by
     */
    private int delta;
    /**
     * mod num
     */
    private int remainder;
    /**
     * 步长
     */
    private int step;
    /**
     * 加载的时间，单位秒
     */
    private long time;

    /**
     * 是否已用完
     */
    private volatile boolean over = false;

    private volatile boolean isInit;


    public SegmentRange() {

    }
    /**
     * 这个方法主要为了1,4,7,10...这种序列准备的 设置好初始值之后，会以delta的方式递增，保证无论开始id是多少都能生成正确的序列 如当前是号段是(1000,2000]，delta=3,
     * remainder=0，则经过这个方法后，currentId会先递增到1002,之后每次增加delta
     * 因为currentId会先递增，所以会浪费一个id，所以做了一次减delta的操作，实际currentId会从999开始增，第一个id还是1002
     */
    public void init() {
        if (isInit) {
            return;
        }
        synchronized (this) {
            if (isInit) {
                return;
            }
            long id = currentVal.get();
            if (id % delta == remainder) {
                isInit = true;
                return;
            }
            for (int i = 0; i <= delta; i++) {
                id = currentVal.incrementAndGet();
                if (id % delta == remainder) {
                    // 避免浪费 减掉系统自己占用的一个id
                    currentVal.addAndGet(0 - delta);
                    isInit = true;
                    return;
                }
            }
        }
    }

    public long getBatch(int size) {
        long currentValue = currentVal.getAndAdd(size) + size - 1;
        if (currentValue > maxId) {
            over = true;
            return -1;
        }
        return currentValue;
    }

    public Result nextId() {
        init();
        long val = currentVal.addAndGet(delta);
        if (val > maxId) {
            return new Result(val, step, ResultEnum.OVER);
        } else if (val >= loadingVal) {
            int nextStep = nextStep();
            return new Result(val, nextStep, ResultEnum.LOADING);
        } else {
            return new Result(val, step, ResultEnum.NORMAL);
        }
    }

    /**
     * 动态步长
     * (0-20)分钟步长加倍,但不能大于最大步长
     * [20-40]分钟步不变
     * (大于40)分钟步长减半,但不能小于最小步长
     *
     * @return
     */
    private int nextStep() {
        long currentSecond = System.currentTimeMillis() / 1000;
        long diff = currentSecond - time;
        if (logger.isInfoEnabled()) {
            logger.info("nextId Range:[{}~{}],time:{} second", maxId - step, maxId, diff);
        }
        if (diff > MAX_TIME) {
            // 大于40分钟 步长减半
            int tempStep = step / 2;
            // 步长不能小于默认最小步长
            return tempStep < LeafConstant.DEFAULT_STEP ? LeafConstant.DEFAULT_STEP : tempStep;

        } else if (diff < MIN_TIME) {
            // 大于20分钟 步长加倍
            int tempStep = step * 2;
            // 步长不能小于默认最小步长
            return tempStep > LeafConstant.STEP_MAX ? LeafConstant.STEP_MAX : tempStep;
        } else {
            //[30~60]步长不变
            return step;
        }
    }

    public boolean useful() {
        return currentVal.get() <= maxId;
    }

    public boolean isOver() {
        return over;
    }

    public void setOver(boolean over) {
        this.over = over;
    }

    /**
     * @return the loadingVal
     */
    public long getLoadingVal() {
        return loadingVal;
    }

    /**
     * @param loadingVal
     *            the loadingVal to set
     */
    public void setLoadingVal(long loadingVal) {
        this.loadingVal = loadingVal;
    }

    /**
     * @return the delta
     */
    public int getDelta() {
        return delta;
    }

    /**
     * @param delta
     *            the delta to set
     */
    public void setDelta(int delta) {
        this.delta = delta;
    }

    /**
     * @return the remainder
     */
    public int getRemainder() {
        return remainder;
    }

    /**
     * @param remainder
     *            the remainder to set
     */
    public void setRemainder(int remainder) {
        this.remainder = remainder;
    }

    public AtomicLong getCurrentVal() {
        return currentVal;
    }

    public void setCurrentVal(AtomicLong currentVal) {
        this.currentVal = currentVal;
    }

    public long getMaxId() {
        return maxId;
    }

    public void setMaxId(long maxId) {
        this.maxId = maxId;
    }

    public int getStep() {
        return step;
    }

    public void setStep(int step) {
        this.step = step;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}