package com.bamboo.leaf.demo.mybatis.Model;

import com.bamboo.leaf.client.annotation.SegmentId;

import java.io.Serializable;
import java.util.Date;

public class BambooDemoModel implements Serializable {

    @SegmentId(namespace="SegmentIdTest")
    private String id;
    private String namespace;
    private Date createTime;
    private Date updateTime;
    private String remark;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNamespace() {
        return namespace;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
