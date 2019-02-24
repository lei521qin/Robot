package com.biyao.robot.service.user;

import java.util.List;

/**
 * Created by zhangxiaolei.
 * Create Date: 2018/12/25 17:34
 * Description: 本地缓存中获取机器人信息
 */
public interface RobotUserNativeService {

    /**
     * 批量获取本地缓存中的机器人信息.
     * @param type 机器人属性
     * @param counts 获取数量
     * @return 结果
     * @throws Exception 异常
     */
    List<String> randomRobotUserNative(String type, int counts);

}
