package com.bamboo.leaf.core.constant;

/**
 * @description: 表定义字段常量类
 * @Author: Zhuzhi
 * @Date: 2020/12/4 下午11:13
 */
public class TableConfigure {

    /**
     * workId数据库表名，并设置默认值
     */
    private String workIdTableName = LeafConstant.DEFAULT_WORKERID_TABLE_NAME;

    /**
     * 序列所在的表名
     */
    private String segmentTableName = LeafConstant.DEFAULT_TABLE_NAME;

    /**
     * 命名空间的列名
     */
    private String namespaceColumnName = LeafConstant.DEFAULT_NAMESPACE_COLUMN_NAME;

    /**
     * 当前值的列名
     */
    private String leafValueColumnName = LeafConstant.DEFAULT_VALUE_COLUMN_NAME;

    /**
     * 步长的列名
     */
    private String stepColumnName = LeafConstant.DEFAULT_STEP_COLUMN_NAME;

    /**
     * 重试次数的列名
     */
    private String retryColumnName = LeafConstant.DEFAULT_RETRY_COLUMN_NAME;

    /**
     * 最后更新时间的列名
     */
    private String updateColumnName = LeafConstant.DEFAULT_UPDATE_COLUMN_NAME;

    /**
     * 创建时间的列名
     */
    private String createColumnName = LeafConstant.DEFAULT_CREATE_COLUMN_NAME;
    /**
     * 每次id增量的列名
     */
    private String deltaColumnName = LeafConstant.DEFAULT_DELTA_COLUMN_NAME;
    /**
     *余数的列名
     */
    private String remaiderColumnName = LeafConstant.DEFAULT_REMAINDER_COLUMN_NAME;

    /**
     * 备注
     */
    private String remarkColumnName = LeafConstant.DEFAULT_REMARK_COLUMN_NAME;

    /**
     * 版本号
     */
    private String versionColumnName = LeafConstant.DEFAULT_VERSION_COLUMN_NAME;

    /**
     * hostIp
     */
    private String hostIpColumnName = LeafConstant.DEFAULT_WORKERID_IP_COLUMN_NAME;

    /**
     * workerId
     */
    private String workerIdColumnName = LeafConstant.DEFAULT_WORKERID_COLUMN_NAME;



    public String getWorkIdTableName() {
        return workIdTableName;
    }

    public void setWorkIdTableName(String workIdTableName) {
        this.workIdTableName = workIdTableName;
    }

    public String getSegmentTableName() {
        return segmentTableName;
    }

    public void setSegmentTableName(String segmentTableName) {
        this.segmentTableName = segmentTableName;
    }

    public String getNamespaceColumnName() {
        return namespaceColumnName;
    }

    public void setNamespaceColumnName(String namespaceColumnName) {
        this.namespaceColumnName = namespaceColumnName;
    }

    public String getLeafValueColumnName() {
        return leafValueColumnName;
    }

    public void setLeafValueColumnName(String leafValueColumnName) {
        this.leafValueColumnName = leafValueColumnName;
    }

    public String getStepColumnName() {
        return stepColumnName;
    }

    public void setStepColumnName(String stepColumnName) {
        this.stepColumnName = stepColumnName;
    }

    public String getRetryColumnName() {
        return retryColumnName;
    }

    public void setRetryColumnName(String retryColumnName) {
        this.retryColumnName = retryColumnName;
    }

    public String getUpdateColumnName() {
        return updateColumnName;
    }

    public void setUpdateColumnName(String updateColumnName) {
        this.updateColumnName = updateColumnName;
    }

    public String getCreateColumnName() {
        return createColumnName;
    }

    public void setCreateColumnName(String createColumnName) {
        this.createColumnName = createColumnName;
    }

    public String getDeltaColumnName() {
        return deltaColumnName;
    }

    public void setDeltaColumnName(String deltaColumnName) {
        this.deltaColumnName = deltaColumnName;
    }

    public String getRemaiderColumnName() {
        return remaiderColumnName;
    }

    public void setRemaiderColumnName(String remaiderColumnName) {
        this.remaiderColumnName = remaiderColumnName;
    }

    public String getRemarkColumnName() {
        return remarkColumnName;
    }

    public void setRemarkColumnName(String remarkColumnName) {
        this.remarkColumnName = remarkColumnName;
    }

    public String getVersionColumnName() {
        return versionColumnName;
    }

    public void setVersionColumnName(String versionColumnName) {
        this.versionColumnName = versionColumnName;
    }

    public String getHostIpColumnName() {
        return hostIpColumnName;
    }

    public void setHostIpColumnName(String hostIpColumnName) {
        this.hostIpColumnName = hostIpColumnName;
    }

    public String getWorkerIdColumnName() {
        return workerIdColumnName;
    }

    public void setWorkerIdColumnName(String workerIdColumnName) {
        this.workerIdColumnName = workerIdColumnName;
    }
}
