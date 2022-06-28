package com.bamboo.leaf.client.service;

import java.util.Map;

/**
 * @description: Segment算法客户端接口
 * @Author: Zhuzhi
 * @Date: 2020/12/16 下午12:03
 */
public interface BambooLeafSegmentClient {
    /**
     * 获取segment算法序列
     * 范围(0~Long.MAX_VALUE)
     *
     * @param namespace 命名空间
     * @return
     */
    public Long segmentId(String namespace);

    /**
     * 获取segment算法序列
     *
     * @param namespace
     * @param paramMap
     * @return
     */
    public Long segmentId(String namespace, Map<String, Object> paramMap);

    /**
     * 获取segment算法序列
     * 格式19位：yyyyMMdd(8位)+11位序列
     *
     * @param namespace 命名空间
     * @return
     */
    public Long dateSegmentId(String namespace);

    /**
     * 获取segment算法序列,支持循环，最大值后自动重置。
     * 格式19位+：前缀+yyyyMMdd(8位)+11位序列
     *
     * @param namespace 命名空间
     * @param prefix    前缀
     * @return
     */
    public String dateSegmentId(String namespace, String prefix);

    /**
     * @param namespace 命名空间
     * @param paramMap  个性化参数
     * @return
     */
    public Long dateSegmentId(String namespace, Map<String, Object> paramMap);


    /**
     * 获取segment算法序列,支持循环，最大值后自动重置。
     * 格式19位+：前缀+yyyyMMdd(8位)+11位序列
     *
     * @param namespace 命名空间
     * @param prefix    前缀
     * @param paramMap  个性化参数
     * @return
     */
    public String dateSegmentId(String namespace, String prefix, Map<String, Object> paramMap);


    /**
     * 获取segment算法序列,支持循环，最大值后自动重置。
     * 格式16位：yyMMdd(6位)+10位序列
     *
     * @param namespace 命名空间
     * @return
     */
    public Long shortDateSegmentId(String namespace);

    /**
     * 获取segment算法序列,支持循环，最大值后自动重置。
     * 格式16位：yyMMdd(6位)+10位序列
     *
     * @param namespace 命名空间
     * @param paramMap  个性化参数
     * @return
     */
    public Long shortDateSegmentId(String namespace, Map<String, Object> paramMap);

    /**
     * 获取segment算法序列,支持循环，最大值后自动重置。
     * 格式16位+：前缀+yyMMdd(6位)+10位序列
     *
     * @param namespace 命名空间
     * @param prefix    前缀
     * @return
     */
    public String shortDateSegmentId(String namespace, String prefix);

    /**
     * 获取segment算法序列,支持循环，最大值后自动重置。
     * 格式16位+：前缀+yyMMdd(6位)+10位序列
     *
     * @param namespace 命名空间
     * @param prefix    前缀
     * @param paramMap  个性化参数
     * @return
     */
    public String shortDateSegmentId(String namespace, String prefix, Map<String, Object> paramMap);


    /**
     * 获取segment算法序列,支持循环，最大值后自动重置。
     * 格式19位：yyMMddHHmmss(12位)+7位序列
     *
     * @param namespace 命名空间
     * @param paramMap  个性化参数
     * @return
     */
    public Long timeSegmentId(String namespace, Map<String, Object> paramMap);

    /**
     * 获取segment算法序列,支持循环，最大值后自动重置。
     * 格式19位：yyMMddHHmmss(12位)+7位序列
     *
     * @param namespace 命名空间
     * @return
     */
    public Long timeSegmentId(String namespace);

    /**
     * 获取segment算法序列,支持循环，最大值后自动重置。
     * 格式19位+：前缀+yyMMddHHmmss(12位)+7位序列
     *
     * @param namespace 命名空间
     * @param prefix    前缀
     * @return
     */
    public String timeSegmentId(String namespace, String prefix);

    /**
     * 获取segment算法序列,支持循环，最大值后自动重置。
     * 格式19位+：前缀+yyMMddHHmmss(12位)+7位序列
     *
     * @param namespace 命名空间
     * @param prefix    前缀
     * @return
     */
    public String timeSegmentId(String namespace, String prefix, Map<String, Object> paramMap);
}
