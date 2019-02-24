package com.biyao.robot.common.aop;


import java.lang.annotation.*;

/**
 * Created by weizhijian.
 * Create Date: 2018/3/8 18:20
 * Description:异常自定义注解
 */
@Inherited
@Documented
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface FunctionName {
    /**
     * 方法描述 默认为空串.
     * @return 结果
     */
    String description() default "";

    /**
     * 日志打印.
     * @return 结果
     */
    String loggerName() default "";
}
