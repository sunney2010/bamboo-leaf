package com.bamboo.leaf.core.exception;

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
