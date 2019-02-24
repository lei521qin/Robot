package com.biyao.robot.dubbo.service.user.paramvalidator;

import com.biyao.robot.common.code.ErrorCode;
import com.biyao.robot.common.constant.CommonConstant;
import com.biyao.robot.common.exception.RobotRunTimeException;
import org.apache.commons.lang3.StringUtils;

/**
 * Created by zhangxiaolei.
 * Create Date: 2018/12/26 19:50
 * Description: 请求参数的验证
 */
public final class RobotUserParamValidator {

    /**
     * 构造方法.
     */
    private RobotUserParamValidator() {

    }

    /**
     * 参数验证.
     * @param counts 数量
     * @param caller 调用方
     */
    public static void validateRobotUserBatchParam(final int counts,
                                                   final String caller) {

        if (StringUtils.isBlank(caller)) {
            throw new RobotRunTimeException(ErrorCode.ERROR_CALLER_IS_NULL);
        }

        if (counts <= 0) {
            throw new RobotRunTimeException(ErrorCode.ERROR_COUNTS_IS_NULL);
        }

        if (counts > CommonConstant.PARAM_MAX_NUM) {
            throw new RobotRunTimeException(ErrorCode.ERROR_MAX_WANG_COUNTS);
        }
    }
}
