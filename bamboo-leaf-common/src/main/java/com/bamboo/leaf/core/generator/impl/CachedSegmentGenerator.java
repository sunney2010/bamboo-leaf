package com.bamboo.leaf.core.generator.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.bamboo.leaf.core.entity.Result;
import com.bamboo.leaf.core.entity.ResultEnum;
import com.bamboo.leaf.core.entity.SegmentRange;
import com.bamboo.leaf.core.exception.BambooLeafException;
import com.bamboo.leaf.core.factory.NamedThreadFactory;
import com.bamboo.leaf.core.generator.SegmentGenerator;
import com.bamboo.leaf.core.service.SegmentRangeService;

/**
 * @author zhuzhi
 * @date 2020/11/19
 */
public class CachedSegmentGenerator implements SegmentGenerator {

    protected SegmentRangeService segmentRangeService;
    // 命名空间
    protected String namespace;

    // 当前号段
    protected volatile SegmentRange currentSegment;
    // 下个号段
    protected volatile SegmentRange nextSegment;

    private volatile boolean isLoadingNext;

    private Object lock = new Object();

    private ExecutorService executorService =
        Executors.newSingleThreadExecutor(new NamedThreadFactory("SegmentGenerator"));

    public CachedSegmentGenerator(String namespace, SegmentRangeService segmentRangeService) {
        this.namespace = namespace;
        this.segmentRangeService = segmentRangeService;
        loadCurrent();
    }

    public synchronized void loadCurrent() {
        if (currentSegment == null || !currentSegment.isOver()) {
            if (nextSegment == null) {
                SegmentRange segmentRange = querySegmentRange();
                this.currentSegment = segmentRange;
            } else {
                currentSegment = nextSegment;
                nextSegment = null;
            }
        }
    }

    private SegmentRange querySegmentRange() {
        String message = null;
        try {
            SegmentRange segmentRange = segmentRangeService.getNextSegmentRange(namespace);
            if (segmentRange != null) {
                return segmentRange;
            }
        } catch (Exception e) {
            message = e.getMessage();
        }
        throw new BambooLeafException("error query segmentRange: " + message);
    }

    public void loadNext() {
        if (nextSegment == null && !isLoadingNext) {
            synchronized (lock) {
                if (nextSegment == null && !isLoadingNext) {
                    isLoadingNext = true;
                    executorService.submit(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                // 无论获取下个segmentId成功与否，都要将isLoadingNext赋值为false
                                nextSegment = querySegmentRange();
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
    public Long nextId() {
        while (true) {
            if (currentSegment == null) {
                loadCurrent();
                continue;
            }
            Result result = currentSegment.nextId();
            if (result.getResultEnum() == ResultEnum.OVER) {
                loadCurrent();
            } else {
                if (result.getResultEnum() == ResultEnum.LOADING) {
                    loadNext();
                }
                return result.getVal();
            }
        }
    }

    @Override
    public List<Long> nextId(Integer batchSize) {
        List<Long> ids = new ArrayList<>();
        for (int i = 0; i < batchSize; i++) {
            Long id = nextId();
            ids.add(id);
        }
        return ids;
    }

}
