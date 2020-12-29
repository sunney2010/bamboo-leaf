package com.bamboo.leaf.core.generator.impl;

import com.bamboo.leaf.core.entity.Result;
import com.bamboo.leaf.core.entity.ResultEnum;
import com.bamboo.leaf.core.entity.SegmentRange;
import com.bamboo.leaf.core.exception.BambooLeafException;
import com.bamboo.leaf.core.factory.NamedThreadFactory;
import com.bamboo.leaf.core.generator.SegmentGenerator;
import com.bamboo.leaf.core.service.SegmentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author zhuzhi
 * @date 2020/11/19
 */
public class CachedSegmentGenerator implements SegmentGenerator {

    private static final Logger logger = LoggerFactory.getLogger(CachedSegmentGenerator.class);

    protected SegmentService segmentService;

    /**
     * 命名空间
     */
    protected String namespace;

    protected long maxValue = 0L;
    /**
     * 当前号段
     */
    protected volatile SegmentRange currentSegment;
    /**
     * 下个号段
     */
    protected volatile SegmentRange nextSegment;

    private volatile boolean isLoadingNext;

    private Object lock = new Object();

    private ExecutorService executorService =
            new ThreadPoolExecutor(5, 200,
                    0L, TimeUnit.MILLISECONDS,
                    new LinkedBlockingQueue<Runnable>(1024), new NamedThreadFactory("SegmentGenerator"), new ThreadPoolExecutor.AbortPolicy());

    public CachedSegmentGenerator(String namespace, long maxValue, SegmentService segmentService) {
        this.namespace = namespace;
        this.segmentService = segmentService;
        this.maxValue = maxValue;
        loadCurrent(maxValue);
    }

    public synchronized void loadCurrent(long maxVal) {
        if (currentSegment == null || !currentSegment.isOver()) {
            if (nextSegment == null) {
                SegmentRange segmentRange = querySegmentRange(maxVal);
                this.currentSegment = segmentRange;
            } else {
                currentSegment = nextSegment;
                nextSegment = null;
            }
        }
    }

    private SegmentRange querySegmentRange(long maxVal) {
        String message = null;
        try {
            SegmentRange segmentRange = segmentService.getNextSegmentRange(namespace, maxVal);
            if (segmentRange != null) {
                return segmentRange;
            }
        } catch (Exception e) {
            logger.error(" querySegmentRange is error,msg:", e);
        }
        throw new BambooLeafException("error query segmentRange: " + message);
    }

    public void loadNext(long maxVal) {
        if (nextSegment == null && !isLoadingNext) {
            synchronized (lock) {
                if (nextSegment == null && !isLoadingNext) {
                    isLoadingNext = true;
                    executorService.submit(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                // 无论获取下个segmentId成功与否，都要将isLoadingNext赋值为false
                                nextSegment = querySegmentRange(maxVal);
                                logger.info("loading nextSegment is success,range:{}->{}", nextSegment.getCurrentVal(), nextSegment.getMaxId());
                            } finally {
                                isLoadingNext = false;
                            }
                        }
                    });
                }
            }
        }
    }

    @Override
    public Long nextSegmentId() {
        while (true) {
            if (currentSegment == null) {
                loadCurrent(maxValue);
                continue;
            }
            Result result = currentSegment.nextId();
            if (result.getResultEnum() == ResultEnum.OVER) {
                loadCurrent(maxValue);
            } else {
                if (result.getResultEnum() == ResultEnum.LOADING) {
                    loadNext(maxValue);
                }
                return result.getVal();
            }
        }
    }

    @Override
    public String nextSegmentIdFixed(long maxValue) {
        int fixedLength = Long.toString(maxValue).length();
        long val = this.nextSegmentId();
        String valTemp = val + "";
        StringBuilder id = new StringBuilder(fixedLength);
        // 不足位数前面补"0"
        for (int i = 0; i < (fixedLength - valTemp.length()); i++) {
            id.append("0");
        }
        id.append(val);
        return id.toString();

    }

    @Override
    public List<Long> nextSegmentId(Integer batchSize) {
        List<Long> ids = new ArrayList<>();
        for (int i = 0; i < batchSize; i++) {
            Long id = nextSegmentId();
            ids.add(id);
        }
        return ids;
    }
}
