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

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    protected SegmentService segmentService;

    /**
     * 命名空间
     */
    protected String namespace;
    /**
     * 最大值
     */
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
        loadCurrent(maxValue, 0);
    }

    public synchronized void loadCurrent(long maxVal, int nextStep) {
        if (currentSegment == null) {
            currentSegment = querySegmentRange(maxVal, nextStep);
        } else if (currentSegment.isOver()) {
            // 判断预加载是否成功
            if (nextSegment != null) {
                // 预加载段赋给当前段
                currentSegment = nextSegment;
                nextSegment = null;
                logger.info("namespace:{} currentSegment is over,nextSegment-->currentSegment! nextRange:{}->{} ",
                        currentSegment.getNamespace(), currentSegment.getCurrentVal().get() + 1, currentSegment.getMaxId());
            } else {
                // 重新实时加载
                currentSegment = querySegmentRange(maxVal, nextStep);
                logger.info("namespace:{} currentSegment is over,reload currentSegment! nextRange:{}->{}",
                        currentSegment.getNamespace(), currentSegment.getCurrentVal().get() + 1, currentSegment.getMaxId());
            }
        }
    }

    /**
     * 获取数据段
     * @param maxVal 最大值
     * @param nextStep 下个步长
     * @return
     */
    private SegmentRange querySegmentRange(long maxVal, int nextStep) {
        try {
            SegmentRange segmentRange = segmentService.getNextSegmentRange(namespace, maxVal, nextStep);
            if (segmentRange != null) {
                return segmentRange;
            } else {
                throw new BambooLeafException("query getNextSegmentRange is null");
            }
        } catch (Exception e) {
            logger.error(" querySegmentRange is error,msg:", e);
            throw new BambooLeafException("error query segmentRange: " + e.getMessage());
        }
    }

    /**
     * 异步获取下个数据段
     *
     * @param maxVal   最大值
     * @param nextStep 下个步长
     * @return
     */
    public void loadNext(long maxVal, int nextStep) {
        if (nextSegment == null && !isLoadingNext) {
            synchronized (lock) {
                if (nextSegment == null && !isLoadingNext) {
                    isLoadingNext = true;
                    executorService.submit(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                // 无论获取下个segmentId成功与否，都要将isLoadingNext赋值为false
                                nextSegment = querySegmentRange(maxVal, nextStep);
                                logger.info("namespace:{} loading nextSegment is success,nextStep:{},nextRange:{}->{}",
                                        nextSegment.getNamespace(), nextStep, nextSegment.getCurrentVal().get() + 1, nextSegment.getMaxId());
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
                loadCurrent(maxValue, 0);
                continue;
            }
            Result result = currentSegment.nextId();
            if (result.getResultEnum() == ResultEnum.OVER) {
                currentSegment.setOver(true);
                loadCurrent(maxValue, result.getNextStep());
                continue;
            } else {
                // LOADING AND NORMAL
                // 预加载下一段序列
                if (result.getResultEnum() == ResultEnum.LOADING) {
                    loadNext(maxValue, result.getNextStep());
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