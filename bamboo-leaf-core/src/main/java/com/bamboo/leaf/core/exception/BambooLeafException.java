package com.bamboo.leaf.core.exception;

/**
 * @description: 自定义异常类
 * @Author: Zhuzhi
 * @Date: 2020/11/30 下午1:10
 */
public class BambooLeafException extends RuntimeException {

    /**
     *
     */
    private static final long serialVersionUID = 330187098951999138L;

    public BambooLeafException(String params) {
        super(params);
    }

    public BambooLeafException(Throwable cause) {
        super(cause);
    }
}
