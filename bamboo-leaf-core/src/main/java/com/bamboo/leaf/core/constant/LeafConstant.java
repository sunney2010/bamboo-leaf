package com.bamboo.leaf.core.constant;

/**
 * @author zhuzhi
 * @description: 常量类
 * @date 2020/11/19
 */
public class LeafConstant {


    //segment 默认表名及字段 START===========

    public static final String DEFAULT_NAMESPACE_COLUMN_NAME = "namespace";
    public static final String DEFAULT_CREATE_COLUMN_NAME = "create_time";
    public static final String DEFAULT_UPDATE_COLUMN_NAME = "update_time";
    public static final String DEFAULT_REMARK_COLUMN_NAME = "remark";

    public static final String DEFAULT_TABLE_NAME = "bamboo_leaf_segment";

    public static final String DEFAULT_VALUE_COLUMN_NAME = "leaf_val";
    public static final String DEFAULT_STEP_COLUMN_NAME = "step";
    public static final String DEFAULT_RETRY_COLUMN_NAME = "retry";
    public static final String DEFAULT_VERSION_COLUMN_NAME = "version";
    public static final String DEFAULT_DELTA_COLUMN_NAME = "delta";
    public static final String DEFAULT_REMAINDER_COLUMN_NAME = "remainder";

    public static final String DEFAULT_WORKERID_TABLE_NAME = " bamboo_leaf_workerId ";
    public static final String DEFAULT_WORKERID_IP_COLUMN_NAME = " host_ip";
    public static final String DEFAULT_WORKERID_COLUMN_NAME = " worker_id ";



    //================END=============

    /**
     * 最小步长
     */
    public static final int STEP_MIN = 1;
    /**
     * 最大步长
     */
    public static final int STEP_MAX = 100000;
    /**
     * 默认步长
     */
    public static final int DEFAULT_STEP = 1000;
    /**
     * 默认序列值
     */
    public static final long DEFAULT_VALUE = 0L;
    /**
     * 默认重试次数
     */
    public static final int DEFAULT_RETRY_TIMES = 10;
    /**
     * 最小重试次数
     */
    public static final int RETRY_TIMES_MIN = 3;
    /**
     * 最大重试次数
     */
    public static final int RETRY_TIMES_MAX = 20;
    /**
     * 预加载下个号段的默认百分比
     */
    public static final int DEFAULT_LOADING_PERCENT = 25;
    /**
     * 预加载下个号段的最小百分比
     */
    public static final int LOADING_PERCENT_MIN = 10;

    /**
     * 预加载下个号段的最大百分比
     */
    public static final int LOADING_PERCENT_MAX = 60;


    public static final int RADIX = 36;
    public static final int RADIX64 = 64;

    public static final int MAX_RANDOM = Integer.parseInt("zzz", RADIX);

    public static final int MAX_NAMESPACE = Integer.parseInt("zzzz", RADIX);

    public static final long DEFAULT_BUFFER = 100000000L;

    /**
     * 最小workerId
     */
    public static final int INIT_WORKER_ID = 0;
    /**
     * 最大workerId
     */
    public static final int MAX_WORKER_ID = 32768;
    /**
     * 日期序列段的最大值
     */
    public static final long SEGMENT_DATE_MAXVALUE = 99999999999L;
    /**
     * 时间序列段的最大值
     */
    public static final long SEGMENT_TIME_MAXVALUE = 9999999L;

}
