package com.bamboo.leaf.autoconfigure;

import com.bamboo.leaf.core.util.PURL;
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
     * 应用编号
     * mode=Remote 模式有效
     */
    private String appId;

    /**
     * mode=Remote 服务模式时，通信的Token
     */
    private String leafToken;
    /**
     * Remote:服务模式时的服务地址
     */
    private String leafServer;

    /**
     * Remote:服务模式时的端口
     */
    private String leafPort;

    /**
     * purl
     */
    private PURL purl;
    /**
     * segment算法服务地址
     */
    private List<String> segmentServerList;
    /**
     * snowflake算法服务地址
     */
    private List<String> snowServerList;

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

    public List<String> getSegmentServerList() {
        return segmentServerList;
    }

    public void setSegmentServerList(List<String> segmentServerList) {
        this.segmentServerList = segmentServerList;
    }

    public List<String> getSnowServerList() {
        return snowServerList;
    }

    public void setSnowServerList(List<String> snowServerList) {
        this.snowServerList = snowServerList;
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

    public String getLeafPort() {
        return leafPort;
    }

    public void setLeafPort(String leafPort) {
        this.leafPort = leafPort;
    }

    public PURL getPurl() {
        return purl;
    }

    public void setPurl(PURL purl) {
        this.purl = purl;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }
}