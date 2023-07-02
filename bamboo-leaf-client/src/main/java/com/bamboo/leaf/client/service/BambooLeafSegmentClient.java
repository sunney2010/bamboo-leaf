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
     * 默认格式18位：yyyyMMdd(8位)+10位序列
     * 例如：20230101+0000000001
     *
     * @param namespace 命名空间
     * @return
     */
    public Long dateSegmentId(String namespace);


    /**
     * 获取segment算法序列,支持循环，序列段最大值后自动重置。
     * 默认格式18位：yyyyMMdd(8位)+10位序列
     * 例如：20230101+0000000001
     * 自定义格式：[prefix]+yyyyMMdd(8位)+[infix]+10位序列+[suffix]
     * 例如：[123]+20230101+[456]+0000000001+[789]
     * @param namespace 命名空间
     * @param paramMap  个性化参数,定义如下：
     *                  ParamCnstant.PARAM_MAX_VALUE_KEY=最大值范围【8~10】，默认为：10
     *                  ParamCnstant.PARAM_MODE_KEY=模式(Local Remote),默认为：Local
     *                  ParamCnstant.PARAM_PREFIX=前缀最长6位
     *                  ParamCnstant.PARAM_INFIX=中缀最长6位
     *                  ParamCnstant.PARAM_SUFFIX=后缀最长6位
     * @return
     */
    public String dateSegmentId(String namespace, Map<String, Object> paramMap);


    /**
     * 获取segment算法序列,支持循环，序号最大值后自动重置。
     * 格式16位：yyMMdd(6位)+10位序列
     * 例如：230101+0000000001
     * @param namespace 命名空间
     * @return
     */
    public Long shortDateSegmentId(String namespace);

    /**
     * 获取segment算法序列,支持循环，序号最大值后自动重置。
     * 格式16位：yyMMdd(6位)+10位序列
     * 例如：230101+0000000001
     * 自定义格式：[prefix]+yyMMdd(8位)+[infix]+10位序列+[suffix]
     * 例如：[123]+230101+[456]+0000000001+[789]
     * @param namespace 命名空间
     * @param paramMap  个性化参数,定义如下：
     *                  ParamCnstant.PARAM_MAX_VALUE_KEY=最大值范围【8~10】，默认为：10
     *                  ParamCnstant.PARAM_MODE_KEY=模式(Local Remote),默认为：Local
     *                  ParamCnstant.PARAM_PREFIX=前缀最长6位
     *                  ParamCnstant.PARAM_INFIX=中缀最长6位
     *                  ParamCnstant.PARAM_SUFFIX=后缀最长6位
     * @return
     */
    public String shortDateSegmentId(String namespace, Map<String, Object> paramMap);


    /**
     * 获取segment算法序列,支持循环，序号段最大值后自动重置。
     * 默认格式19位：yyMMddHHmmss(12位)+7位序列
     * 例如：230101010101+0000001
     * @param namespace 命名空间
     * @return
     */
    public Long timeSegmentId(String namespace);

    /**
     * 获取segment算法序列,支持循环，序号段最大值后自动重置。
     * 默认格式19位：yyMMddHHmmss(12位)+7位序列
     * 例如：230101010101+0000001
     * 自定义格式：[prefix]+yyMMdd(8位)+[infix]+10位序列+[suffix]
     * 例如：[123]+230101010101+[456]+0000001+[789]
     * @param namespace 命名空间
     * @param paramMap  个性化参数,定义如下：
     *                  ParamCnstant.PARAM_MAX_VALUE_KEY=最大值范围【5~7】，默认为：7
     *                  ParamCnstant.PARAM_MODE_KEY=模式(Local Remote),默认为：Local
     *                  ParamCnstant.PARAM_PREFIX=前缀最长6位
     *                  ParamCnstant.PARAM_INFIX=中缀最长6位
     *                  ParamCnstant.PARAM_SUFFIX=后缀最长6位
     * @return
     */
    public String timeSegmentId(String namespace, Map<String, Object> paramMap);


    /**
     * 获取segment算法序列,支持循环，每天自动重置。
     * 默认格式16位+：yyMMdd(6位)+10位序列
     * 例如：230101+00000000001
     * @param namespace 命名空间
     * @return
     */
    public String autoDateSegmentId(String namespace);

    /**
     * 获取segment算法序列,支持循环，每天自动重置。
     * 默认格式16位+：yyMMdd(6位)+10位序列
     * 自定义格式：[prefix]+yyMMdd(6位)+[infix]+10位序列+[suffix]
     * 例如：[123]+230101+[456]+0000000001+[789]
     * @param namespace 命名空间
     * @param paramMap  个性化参数,定义如下：
     *                  ParamCnstant.PARAM_MAX_VALUE_KEY=最大值范围【8~10】，默认为：10
     *                  ParamCnstant.PARAM_MODE_KEY=模式(Local Remote),默认为：Local
     *                  ParamCnstant.PARAM_PREFIX=前缀最长6位
     *                  ParamCnstant.PARAM_INFIX=中缀最长6位
     *                  ParamCnstant.PARAM_SUFFIX=后缀最长6位
     * @return
     */
    public String autoDateSegmentId(String namespace, Map<String, Object> paramMap);

}
