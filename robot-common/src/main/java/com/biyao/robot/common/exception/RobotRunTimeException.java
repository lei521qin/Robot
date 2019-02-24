package com.biyao.robot.common.exception;

import com.biyao.robot.common.code.ErrorCode;

/**
 * Created by 吴晓明.
 * Create Date: 2018年11月28日
 * Description: 不受检查异常 为了实现 aop 统一异常处理
 * @since 1.9.4
 */
public class RobotRunTimeException extends RuntimeException {

    /**
     * 错误码.
     */
    private ErrorCode errorCode;

    /**
     * 构造方法.
     */
    public RobotRunTimeException() {
    }

    /**
     * 父异常.
     * @param message 入参
     */
    public RobotRunTimeException(final String message) {
        super(message);
    }

    /**
     * 运行异常.
     * @param message 入参
     * @param cause 原因
     */
    public RobotRunTimeException(final String message, final Throwable cause) {
        super(message, cause);
    }

    /**
     * 初始化.
     * @param errorCode 错误码
     */
    public RobotRunTimeException(final ErrorCode errorCode) {
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
    public void setErrorCode(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }
}

