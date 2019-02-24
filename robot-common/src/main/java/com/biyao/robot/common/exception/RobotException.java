package com.biyao.robot.common.exception;


import com.biyao.robot.common.code.ErrorCode;

/**
 * Created by wuguohui.
 * Create Date: 2017/11/23 12:03
 * Description: 公共异常
 */
public class RobotException extends Exception {

    /**
     * 错误码.
     */
    private ErrorCode errorCode;

    /**
     * 错误异常.
     */
    public RobotException() {
    }

    /**
     * 父异常.
     * @param message 入参
     */
    public RobotException(final String message) {
        super(message);
    }

    /**
     * 父异常.
     * @param message 入参
     * @param cause 原因
     */
    public RobotException(final String message, final Throwable cause) {
        super(message, cause);
    }

    /**
     * 初始化.
     * @param errorCode 错误码
     */
    public RobotException(final ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    /**
     * 错误码get.
     * @return 结果
     */
    public ErrorCode getErrorCode() {
        return errorCode;
    }

    /**
     * 错误码set.
     * @param errorCode 入参
     */
    public void setErrorCode(final ErrorCode errorCode) {
        this.errorCode = errorCode;
    }
}

