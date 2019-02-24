package com.biyao.robot.common.constant;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 *
 * @ClassName: Properties
 * @Description: 属性配置文件类
 * @author zhouxg
 *
 */
@Component
public class Properties {

    /**
     * 昵称.
     */
    @Value("#{robot['robot.name']}")
    public String robotName;

    /**
     * 头像.
     */
    @Value("#{robot['robot.avatar']}")
    public String robotAvatar;


    /**
     * 头像(测试环境).
     */
    @Value("#{robot['robot.test.avatar']}")
    public String robotAvatarTest;


    /**
     * 环境标识（true:正式，false:测试）.
     */
    @Value("#{robot['robot.online_flag']}")
    public String onlineFlag;
}
