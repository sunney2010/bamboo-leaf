package com.bamboo.leaf.autoconfigure;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @description: TODO
 * @Author: Zhuzhi
 * @Date: 2020/11/30 下午1:10
 */
@ConfigurationProperties("bamboo.leaf.table")
public class LeafTableProperties {

    private String segmentTableName;
    private String workerIdTableName;
    private String namespaceColumnName;
    private String leafValueColumnName;
    private String versionColumnName;
    private String retryColumnName;
    private String deltaColumnName;
    private String stepColumnName;
    private String remainderColumnName;
    private String createColumnName;
    private String updateColumnName;
    private String remarkColumnName;
    private String workerIdColumnName;
    private String hostIpColumnName;



    public String getSegmentTableName() {
        return segmentTableName;
    }

    public void setSegmentTableName(String segmentTableName) {
        this.segmentTableName = segmentTableName;
    }

    public String getWorkerIdTableName() {
        return workerIdTableName;
    }

    public void setWorkerIdTableName(String workerIdTableName) {
        this.workerIdTableName = workerIdTableName;
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

    public String getVersionColumnName() {
        return versionColumnName;
    }

    public void setVersionColumnName(String versionColumnName) {
        this.versionColumnName = versionColumnName;
    }

    public String getRetryColumnName() {
        return retryColumnName;
    }

    public void setRetryColumnName(String retryColumnName) {
        this.retryColumnName = retryColumnName;
    }

    public String getDeltaColumnName() {
        return deltaColumnName;
    }

    public void setDeltaColumnName(String deltaColumnName) {
        this.deltaColumnName = deltaColumnName;
    }

    public String getStepColumnName() {
        return stepColumnName;
    }

    public void setStepColumnName(String stepColumnName) {
        this.stepColumnName = stepColumnName;
    }

    public String getRemainderColumnName() {
        return remainderColumnName;
    }

    public void setRemainderColumnName(String remainderColumnName) {
        this.remainderColumnName = remainderColumnName;
    }

    public String getCreateColumnName() {
        return createColumnName;
    }

    public void setCreateColumnName(String createColumnName) {
        this.createColumnName = createColumnName;
    }

    public String getUpdateColumnName() {
        return updateColumnName;
    }

    public void setUpdateColumnName(String updateColumnName) {
        this.updateColumnName = updateColumnName;
    }

    public String getRemarkColumnName() {
        return remarkColumnName;
    }

    public void setRemarkColumnName(String remarkColumnName) {
        this.remarkColumnName = remarkColumnName;
    }

    public String getWorkerIdColumnName() {
        return workerIdColumnName;
    }

    public void setWorkerIdColumnName(String workerIdColumnName) {
        this.workerIdColumnName = workerIdColumnName;
    }

    public String getHostIpColumnName() {
        return hostIpColumnName;
    }

    public void setHostIpColumnName(String hostIpColumnName) {
        this.hostIpColumnName = hostIpColumnName;
    }
}
