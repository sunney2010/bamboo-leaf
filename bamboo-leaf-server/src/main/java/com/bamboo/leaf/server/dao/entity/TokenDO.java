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

    private String namespace;

    private String token;

    private Date createTime;

    private Date updateTime;

    private String remark;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNamespace() {
        return namespace;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
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
                ", namespace='" + namespace + '\'' +
                ", token='" + token + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", remark='" + remark + '\'' +
                '}';
    }
}