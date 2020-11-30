package com.bamboo.leaf.client.config;

import java.util.List;

/**
 * @description: TODO
 * @Author: Zhuzhi
 * @Date: 2020/11/30 下午1:10
 */
public class ClientConfig {

    private String leafToken;
    private String leafServer;
    private List<String> serverList;
    private Integer readTimeout;
    private Integer connectTimeout;

    private volatile static ClientConfig clientConfig;

    private ClientConfig() {
    }

    public static ClientConfig getInstance() {
        if (clientConfig != null) {
            return clientConfig;
        }
        synchronized (ClientConfig.class) {
            if (clientConfig != null) {
                return clientConfig;
            }
            clientConfig = new ClientConfig();
        }
        return clientConfig;
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
