package com.bamboo.leaf.autoconfigure;

import com.bamboo.leaf.core.constant.LeafConstant;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @description: TODO
 * @Author: Zhuzhi
 * @Date: 2020/11/30 下午1:10
 */
@Component
@ConfigurationProperties("bamboo.leaf.client")
public class LeafClientProperties {
    /**
     * 模式：Local：本地模式，Remote:服务模式
     */
    private String mode;

    /**
     * Remote:服务模式时，通信的Token
     */
    private String leafToken;
    /**
     * Remote:服务模式时的服务地址
     */
    private String leafServer;

    private List<String> serverList;

    private Integer readTimeout;

    private Integer connectTimeout;

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getLeafToken() {
        return leafToken;
    }

    public void setLeafToken(String leafToken) {
        this.leafToken = leafToken;
    }

    public String getLeafServer() {
        return leafServer;
    }

    public void setLeafServer(String leafServer) {
        this.leafServer = leafServer;
    }

    public List<String> getServerList() {
        return serverList;
    }

    public void setServerList(List<String> serverList) {
        this.serverList = serverList;
    }

    public Integer getReadTimeout() {
        return readTimeout;
    }

    public void setReadTimeout(Integer readTimeout) {
        this.readTimeout = readTimeout;
    }

    public Integer getConnectTimeout() {
        return connectTimeout;
    }

    public void setConnectTimeout(Integer connectTimeout) {
        this.connectTimeout = connectTimeout;
    }
}