package com.bamboo.leaf.core.constant;

/**
 * @author zhuzhi
 * @date 2020/11/19
 */
public class SequenceConstant {


    //segment 默认表名及字段 START===========

    public static final String DEFAULT_TABLE_NAME = "bamboo_leaf_segment";
    public static final String DEFAULT_NAME_COLUMN_NAME = "namespace";
    public static final String DEFAULT_VALUE_COLUMN_NAME = "leaf_val";
    public static final String DEFAULT_STEP_COLUMN_NAME = "step";
    public static final String DEFAULT_RETRY_COLUMN_NAME = "retry";
    public static final String DEFAULT_VERSION_COLUMN_NAME = "version";
    public static final String DEFAULT_DELTA_COLUMN_NAME = "delta";
    public static final String DEFAULT_REMAINDER_COLUMN_NAME = "remainder";
    public static final String DEFAULT_REMARK_COLUMN_NAME = "remark";
    public static final String DEFAULT_CREATE_COLUMN_NAME = "create_time";
    public static final String DEFAULT_UPDATE_COLUMN_NAME = "updated_time";

    //================END=============

    public static final int MIN_STEP = 1;
    public static final int MAX_STEP = 100000;
    public static final int DEFAULT_STEP = 1000;
    public static final int DEFAULT_RETRY_TIMES = 10;
    public static final long DELTA = 100000000L;

    public static final long AUTOINCRE_DEFALUT_INITVALUE = 0L;

    public static final long AUTOINCRE_DATEAI_MAXVALUE = 99999999999L;
    public static final long AUTOINCRE_TIMEAI_MAXVALUE = 9999999L;
    public static final long AUTOINCRE_PREFIX_MAXVALUE = 9999999999L;

    public static final String DEFAULT_WORKERID_TABLE_NAME = " bamboo_leaf_workId ";

    public static final String DEFAULT_WORKERID_NAMESPACE_COLUMN_NAME = " namespace ";

    public static final String DEFAULT_WORKERID_IP_COLUMN_NAME = " host_ip";

    public static final String DEFAULT_WORKERID_COLUMN_NAME = " worker_id ";

    public static final String DEFAULT_WORKERID_CREATETIME_COLUMN_NAME = " created_time ";

    public static final String DEFAULT_WORKERID_UPDATETIME_COLUMN_NAME = " updated_time ";
    
    public static final String BAMBOO_SEQUENCE_MAXVAL="maxVal";
    
    public static final String BAMBOO_SEQUENCE_INITVAL="initVal";
    


    public static final int RADIX = 36;
    public static final int RADIX64 = 64;

    public static final int MAX_RANDOM = Integer.parseInt("zzz", RADIX);

    public static final int MAX_NAMESPACE = Integer.parseInt("zzzz", RADIX);

}
