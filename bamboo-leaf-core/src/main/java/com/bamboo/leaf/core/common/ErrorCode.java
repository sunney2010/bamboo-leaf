package com.bamboo.leaf.core.common;

/**
 * @description: TODO
 * @Author: Zhuzhi
 * @Date: 2020/12/3 下午7:55
 */
public enum ErrorCode {
    /**
     * success
     */
    SUCCESS("success"),
    /**
     * success
     */
    FAIL("fail"),
    /**
     * token is wrong
     */
    TOKEN_ERR("TOKEN IS ERROR"),
    /**
     * token is wrong
     */
    PARA_ERR("PARAMETER IS NULL"),
    /**
     * server internal error
     */
    SYS_ERR("SYSTEM IS ERROR");


    private String message;

    ErrorCode(String message) {

        this.message = message;
    }


    public String getMessage() {
        return message;
    }
}
