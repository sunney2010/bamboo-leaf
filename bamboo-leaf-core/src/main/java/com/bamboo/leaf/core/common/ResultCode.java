package com.bamboo.leaf.core.common;

/**
 * @description: 返回代码类
 * @Author: Zhuzhi
 * @Date: 2020/12/3 下午7:55
 */
public enum ResultCode {
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

    ResultCode(String message) {

        this.message = message;
    }


    public String getMessage() {
        return message;
    }
}
