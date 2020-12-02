package com.bamboo.leaf.core.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * @description: TODO
 * @Author: Zhuzhi
 * @Date: 2020/12/2 下午12:12
 */
public class SegmentDO implements Serializable {
    /**
     * 自增主键
     */
    private Long id;
    /**
     * 业务类型，唯一
     */
    private String namespace;
    /**
     * 当前值
     */
    private Long leafVal;
    /**
     * 步长
     */
    private Integer step;
    /**
     * 每次id增量
     */
    private Integer delta;
    /**
     * 余数
     */
    private Integer remainder;
    /**
     * 重试次数
     */
    private Integer retry;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 更新时间
     */
    private Date updateTime;
    /**
     * 备注
     */
    private Long version;
    /**
     * 备注
     */
    private String remark;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNamespace() {
        return namespace;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    public Long getLeafVal() {
        return leafVal;
    }

    public void setLeafVal(Long leafVal) {
        this.leafVal = leafVal;
    }

    public Integer getStep() {
        return step;
    }

    public void setStep(Integer step) {
        this.step = step;
    }

    public Integer getDelta() {
        return delta;
    }

    public void setDelta(Integer delta) {
        this.delta = delta;
    }

    public Integer getRemainder() {
        return remainder;
    }

    public void setRemainder(Integer remainder) {
        this.remainder = remainder;
    }

    public Integer getRetry() {
        return retry;
    }

    public void setRetry(Integer retry) {
        this.retry = retry;
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

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
