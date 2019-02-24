package com.biyao.robot.common.code;

/**
 * Created by wuguohui.
 * Create Date: 2017/11/23/12:56
 * Description: 错误码和错误信息定义
 */
public enum ErrorCode {
    /**
     * 1-100 预留作为通用异常.
     */

    /**
     * 参数不能为空.
     */
    COMMON_PARAM_NULL(1, "参数不能为空"),
    /**
     * 获取数量不能超过500个.
     */
    ERROR_MAX_COUNTS(2, "获取数量不能超过500个"),

    /**
     * 内部服务异常.
     */
    INTERNAL_SERVER_EXCEPTION(3, "内部服务异常"),
    /**
     * 第三方依赖服务异常.
     */
    THIRD_PARTY_SERVICE_EXCEPTION(4, "第三方依赖服务异常"),
    /**
     * pageIndex不能为空.
     */
    PAGE_INDEX_NULL_ERROR(5, "pageIndex不能为空"),
    /**
     * pageSize不能为空.
     */
    PAGE_SIZE_NULL_ERROR(6, "pageSize不能为空"),
    /**
     * 获取机器人数量不能为0.
     */
    ERROR_COUNTS_IS_NULL(7, "获取机器人数量不能为0"),
    /**
     * 系统调用方不能为空.
     */
    ERROR_CALLER_IS_NULL(8, "系统调用方不能为空"),
    /**
     * 获取数量不能超过一万个.
     */
    ERROR_MAX_WANG_COUNTS(9, "获取数量不能超过一万个");

    /**
     * key.
     */
    private int code;

    /**
     * 信息.
     */
    private String message;

    /**
     * 错误类.
     * @param code 码
     * @param message 信息
     */
    ErrorCode(final int code, final String message) {
        this.code = code;
        this.message = message;
    }

    /**
     * code get属性.
     * @return 结果
     */
    public int getCode() {
        return code;
    }

    /**
     * code set属性.
     * @param code 入参
     */
    public void setCode(final int code) {
        this.code = code;
    }

    /**
     * message get属性.
     * @return 结果
     */
    public String getMessage() {
        return message;
    }

    /**
     * message set属性.
     * @param message 入参
     */
    public void setMessage(final String message) {
        this.message = message;
    }

}
