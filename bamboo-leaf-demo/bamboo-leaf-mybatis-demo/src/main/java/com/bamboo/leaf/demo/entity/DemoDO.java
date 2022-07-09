package com.bamboo.leaf.demo.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * @description: Segment算法实体
 * @Author: Zhuzhi
 * @Date: 2020/12/2 下午12:12
 */
public class DemoDO implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = -2360620799995587767L;
    /**
     * 自增主键
     */
    private String id;
    /**
     * 业务类型，唯一
     */
    private String namespace;


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
