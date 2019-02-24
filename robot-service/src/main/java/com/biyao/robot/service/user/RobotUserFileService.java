package com.biyao.robot.service.user;

import java.util.List;

/**
 * Created by zhangxiaolei.
 * Create Date: 2018/12/25 16:09
 * Description: 机器人基本信息
 */
public interface RobotUserFileService {

    /**
     * 随机从本地文件中获取一个机器人信息.
     * @param type 机器人属性
     * @return 结果
     */
    String randomRobotUserFile(String type);

    /**
     * 随机从本地文件中获取多个机器人信息.
     * @param type 机器人属性
     * @param counts 获取数量
     * @return 结果
     */
    List<String> batchRandomRobotUserFile(String type, int counts);

}
