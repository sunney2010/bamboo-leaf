package com.bamboo.leaf.common.generator.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.bamboo.leaf.common.entity.SegmentRange;
import com.bamboo.leaf.common.generator.SegmentGenerator;
import com.bamboo.leaf.common.service.SegmentRangeService;
import com.xiaoju.uemc.tinyid.base.entity.Result;
import com.xiaoju.uemc.tinyid.base.entity.ResultCode;
import com.xiaoju.uemc.tinyid.base.entity.SegmentId;
import com.xiaoju.uemc.tinyid.base.exception.TinyIdSysException;
import com.xiaoju.uemc.tinyid.base.service.SegmentIdService;
import com.xiaoju.uemc.tinyid.base.util.NamedThreadFactory;

/**
 * @author zhuzhi
 * @date 2020/11/19
 */
public class CachedSegmentGenerator implements SegmentGenerator {
    
    protected String namespace;
    protected SegmentRangeService segmentRangeService;
    protected volatile SegmentRange currentSegment;
    protected volatile SegmentRange nextSegment;
    private volatile boolean isLoadingNext;
    private Object lock = new Object();

    private ExecutorService executorService =
        Executors.newSingleThreadExecutor(new NamedThreadFactory("tinyid-generator"));

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
        throw new TinyIdSysException("error query segmentId: " + message);
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
            Result result = currentSegment.getAndIncrement();
            if (result.getCode() == ResultCode.OVER) {
                loadCurrent();
            } else {
                if (result.getCode() == ResultCode.LOADING) {
                    loadNext();
                }
                return result.getId();
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
