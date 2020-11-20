package com.bamboo.leaf.common.entity;

import java.util.concurrent.atomic.AtomicLong;

/**
 * @author zhuzhi
 * @date 2020/11/19
 */
public class SegmentRange {

    private final long min;
    private final long max;
    //当前
    private final AtomicLong value;

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

    public SegmentRange(long min, long max) {
        this.min = min;
        this.max = max;
        this.value = new AtomicLong(min);
    }

    public long getBatch(int size) {
        long currentValue = value.getAndAdd(size) + size - 1;
        if (currentValue > max) {
            over = true;
            return -1;
        }

        return currentValue;
    }

    public long getAndIncrement() {
        if (over) {
            return -1;
        }
        long currentValue = value.getAndIncrement();
        if (currentValue > max) {
            over = true;
            return -1;
        }

        return currentValue;
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
     * @param loadingVal the loadingVal to set
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
     * @param delta the delta to set
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
     * @param remainder the remainder to set
     */
    public void setRemainder(int remainder) {
        this.remainder = remainder;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("max: ").append(max).append(", min: ").append(min).append(", value: ").append(value);
        return sb.toString();
    }

}
