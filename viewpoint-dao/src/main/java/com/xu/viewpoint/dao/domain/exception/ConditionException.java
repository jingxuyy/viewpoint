package com.xu.viewpoint.dao.domain.exception;

/**
 * @author: xuJing
 * @date: 2024/11/20 20:18
 */

public class ConditionException extends RuntimeException{

    private static final long serialVersionUID = 1L;

    private String code;

    public ConditionException(String code, String message) {
        super(message);
        this.code = code;
    }

    public ConditionException(String message) {
        super(message);
        code = "500";
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
