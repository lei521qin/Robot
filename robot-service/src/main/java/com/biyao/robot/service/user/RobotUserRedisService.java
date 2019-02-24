package com.biyao.robot.service.user;

import java.util.List;

/**
 * Created by zhangxiaolei.
 * Create Date: 2018/12/25 17:34
 * Description: 获取机器人信息从Redis
 */
public interface RobotUserRedisService {

    /**
     * 批量获取Redis缓存中的机器人信息.
     * @param type 机器人属性
     * @param counts 获取数量
     * @return 结果
     * @throws Exception 异常
     */
    List<String> randomRobotUserRedis(String type, int counts);
}
