package com.bamboo.leaf.core.factory;

/**
 * @description: TODO
 * @Author: Zhuzhi
 * @Date: 2020/12/22 下午7:10
 */
public class AbstractSnowflake {
    // ==============================Fields===========================================

    /** Bits allocate */
    /** 机器id所占的位数 */
    protected final int workerIdBits = 15;
    /** 序列在id中占的位数 */
    protected final int sequenceBits = 15;
    /** 时间截中占的位数 */
    protected int timeBits = 33;

    /** 开始时间截 (2019-01-01) */
    protected final long epochSeconds = 1546272000000L;


    /** 支持的最大机器id，结果是32768 (这个移位算法可以很快的计算出几位二进制数所能表示的最大十进制数) */
    protected final long maxWorkerId = -1L ^ (-1L << workerIdBits);




    /** 机器ID向左移15位 */
    protected final long workerIdShift = sequenceBits;


    /** 时间截向左移30位(15+15) */
    protected final long timestampLeftShift = sequenceBits + workerIdBits ;

    /** 生成序列的掩码，这里为32768 (0b111111111111=0xfff=4095) */
    protected final long sequenceMask = -1L ^ (-1L << sequenceBits);

    /** 工作机器ID(0~32768) */
    protected long workerId;

    /** 毫秒内序列(0~32768) */
    protected long sequence = 0L;

    /** 上次生成ID的时间截 */
    protected long lastTimestamp = -1L;
}
