package com.bamboo.leaf.core.generator.impl;

import com.bamboo.leaf.core.entity.BitsAllocator;
import com.bamboo.leaf.core.exception.BambooLeafException;
import com.bamboo.leaf.core.factory.AbstractSnowflake;
import com.bamboo.leaf.core.generator.SnowflakeGenerator;
import com.bamboo.leaf.core.util.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @description: TODO
 * @Author: Zhuzhi
 * @Date: 2020/12/22 下午7:27
 */

public class DefaultSnowflakeGenerator extends AbstractSnowflake implements SnowflakeGenerator, InitializingBean {
    private static final Logger logger = LoggerFactory.getLogger(DefaultSnowflakeGenerator.class);
    /**
     * Stable fields after spring bean initializing
     */
    protected BitsAllocator bitsAllocator;


    public DefaultSnowflakeGenerator(int workerId) {
        super.workerId= workerId;
        // initialize bits allocator
        bitsAllocator = new BitsAllocator(timeBits, workerIdBits, sequenceBits);

        // initialize worker id
        if (workerId > bitsAllocator.getMaxWorkerId()) {
            throw new RuntimeException("Worker id " + workerId + " exceeds the max " + bitsAllocator.getMaxWorkerId());
        }

        logger.info("Initialized bits(1, {}, {}, {}) for workerID:{}", timeBits, workerIdBits, sequenceBits, workerId);


    }

    @Override
    public void afterPropertiesSet() throws Exception {
        // initialize bits allocator
        bitsAllocator = new BitsAllocator(timeBits, workerIdBits, sequenceBits);

        // initialize worker id
        if (workerId > bitsAllocator.getMaxWorkerId()) {
            throw new RuntimeException("Worker id " + workerId + " exceeds the max " + bitsAllocator.getMaxWorkerId());
        }

        logger.info("Initialized bits(1, {}, {}, {}) for workerID:{}", timeBits, workerIdBits, sequenceBits, workerId);

    }

    @Override
    public Long nextId() {
        try {
            return nextSnowId();
        } catch (Exception e) {
            logger.error("Generate unique id exception. ", e);
            throw new BambooLeafException(e);
        }
    }

    @Override
    public String parseSnowId(long snowId) {
        long totalBits = BitsAllocator.TOTAL_BITS;
        long signBits = bitsAllocator.getSignBits();
        long timestampBits = bitsAllocator.getTimestampBits();
        long workerIdBits = bitsAllocator.getWorkerIdBits();
        long sequenceBits = bitsAllocator.getSequenceBits();

        // parse UID
        long sequence = (snowId << (totalBits - sequenceBits)) >>> (totalBits - sequenceBits);
        long workerId = (snowId << (timestampBits + signBits)) >>> (totalBits - workerIdBits);
        long deltaSeconds = snowId >>> (workerIdBits + sequenceBits);

        Date thatTime = new Date(TimeUnit.SECONDS.toMillis(epochSeconds + deltaSeconds));
        String thatTimeStr = DateUtils.formatByDateTimePattern(thatTime);

        // format as string
        return String.format("{\"UID\":\"%d\",\"timestamp\":\"%s\",\"workerId\":\"%d\",\"sequence\":\"%d\"}",
                snowId, thatTimeStr, workerId, sequence);
    }

    /**
     * Get SnowId
     *
     * @return SnowId
     * @throws BambooLeafException in the case: Clock moved backwards; Exceeds the max timestamp
     */
    protected synchronized long nextSnowId() {
        long currentSecond = getCurrentSecond();
        // Clock moved backwards, refuse to generate SnowId
        if (currentSecond < lastTimestamp) {
            long refusedSeconds = lastTimestamp - currentSecond;
            throw new BambooLeafException("Clock moved backwards. Refusing for " + refusedSeconds + " seconds");
        }
        // At the same second, increase sequence
        if (currentSecond == lastTimestamp) {
            sequence = (sequence + 1) & bitsAllocator.getMaxSequence();
            // Exceed the max sequence, we wait the next second to generate SnowId
            if (sequence == 0) {
                currentSecond = getNextSecond(lastTimestamp);
            }
            // At the different second, sequence restart from zero
        } else {
            sequence = 0L;
        }
        lastTimestamp = currentSecond;
        // Allocate bits for SnowId
        return bitsAllocator.allocate(currentSecond - epochSeconds, workerId, sequence);
    }

    /**
     * Get next millisecond
     */
    private long getNextSecond(long lastTimestamp) {
        long timestamp = getCurrentSecond();
        while (timestamp <= lastTimestamp) {
            timestamp = getCurrentSecond();
        }
        return timestamp;
    }

    /**
     * Get current second
     */
    private long getCurrentSecond() {
        long currentSecond = TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis());
        if (currentSecond - epochSeconds > bitsAllocator.getMaxDeltaSeconds()) {
            throw new BambooLeafException("Timestamp bits is exhausted. Refusing UID generate. Now: " + currentSecond);
        }
        return currentSecond;
    }
}
