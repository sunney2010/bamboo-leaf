package com.bamboo.leaf.core.constant;

/**
 * @author zhuzhi
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

    public static final int MIN_STEP = 1;
    public static final int MAX_STEP = 100000;
    /**
     * 默认步长
     */
    public static final int DEFAULT_STEP = 1000;
    /**
     * 默认重试次数
     */
    public static final int DEFAULT_RETRY_TIMES = 10;
    /**
     * 预加载下个号段的百分比
     */
    public static final int DEFAULT_LOADING_PERCENT = 25;

    


    public static final int RADIX = 36;
    public static final int RADIX64 = 64;


    public static final long DEFAULT_BUFFER = 100000000L;

    public static final int INIT_WORKERID = 0;

    public static final int MAX_WORKERID = 1023;

}
