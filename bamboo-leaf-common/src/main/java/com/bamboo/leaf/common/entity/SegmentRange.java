package com.bamboo.leaf.common.entity;

import java.util.concurrent.atomic.AtomicLong;

/**
 * @author zhuzhi
 * @date 2020/11/19
 */
public class SegmentRange {

    private final long min;
    private final long max;
    // 当前
    private final AtomicLong currentVal;

    private volatile boolean over = false;

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

    private volatile boolean isInit;

    public SegmentRange(long min, long max) {
        this.min = min;
        this.max = max;
        this.currentVal = new AtomicLong(min);
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
        if (currentValue > max) {
            over = true;
            return -1;
        }

        return currentValue;
    }

    public Result nextId() {
        init();
        long val = currentVal.addAndGet(delta);
        if (val > max) {
            return new Result(val, ResultEnum.OVER);
        }
        if (val >= loadingVal) {
            return new Result(val, ResultEnum.LOADING);
        }
        return new Result(val, ResultEnum.NORMAL);
    }

    public boolean useful() {
        return currentVal.get() <= max;
    }

    public long getMin() {
        return min;
    }

    public long getMax() {
        return max;
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

}
