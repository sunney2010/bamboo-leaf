package com.bamboo.leaf.server.dao.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * @description: Token实体类
 * @Author: Zhuzhi
 * @Date: 2020/11/21 下午8:29
 */
public class TokenDO implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -8089634722955185988L;

    private Integer id;

    /**
     * 应用编号
     */
    private String appId;
    /**
     * TOKEN
     */
    private String token;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 最后更新时间
     */
    private Date updateTime;
    /**
     * T
     */
    private String remark;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
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

    @Override
    public String toString() {
        return "TokenDO{" +
                "id=" + id +
                ", namespace='" + appId + '\'' +
                ", token='" + token + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", remark='" + remark + '\'' +
                '}';
    }
}