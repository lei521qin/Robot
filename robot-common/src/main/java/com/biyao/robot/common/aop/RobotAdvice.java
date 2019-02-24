package com.biyao.robot.common.aop;

import com.alibaba.fastjson.JSONArray;
import com.biyao.robot.common.bean.Error;
import com.biyao.robot.common.bean.Result;
import com.biyao.robot.common.code.ErrorCode;
import com.biyao.robot.common.exception.RobotRunTimeException;
import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * Created by weizhijian.
 * Create Date: 2018/3/8 18:21
 * Description:异常处理切面
 */
@Aspect
@Component
public class RobotAdvice {

    /**
     * 日志打印.
     */
    private Logger log = Logger.getLogger(this.getClass().getName());

    /**
     * 切面方法.
     * @param point 切点
     * @param functionName 名
     * @return 错误信息
     * @throws Throwable 错误信息
     */
    @Around(value = "@annotation(functionName)")
    public Object aroundExceptionHandler(final ProceedingJoinPoint point,
                                         final FunctionName functionName)
            throws Throwable {

        // 此句执行业务逻辑方法
        Result result = new Result();
        try {
            result = (Result) point.proceed();
            return result;
        } catch (RobotRunTimeException me) {
            result.setSuccess(Boolean.FALSE);
            me = new RobotRunTimeException(me.getErrorCode());
            result.setError(new Error(
                    me.getErrorCode().getCode(), me.getErrorCode().getMessage()));
            log.error(functionName.description() + " - args:" + JSONArray.toJSONString(point.getArgs()) + "\n"
                    + me.getMessage(), me);
            return result;
        } catch (Exception e) {
            result.setSuccess(Boolean.FALSE);
            Error error = new Error(
                    ErrorCode.INTERNAL_SERVER_EXCEPTION.getCode(),
                    ErrorCode.INTERNAL_SERVER_EXCEPTION.getMessage());
            result.setError(error);
            log.error("内部服务异常:"
                    + functionName.description()
                    + " - args:"
                    + JSONArray.toJSONString(point.getArgs()), e);
            return result;
        }
    }
}
