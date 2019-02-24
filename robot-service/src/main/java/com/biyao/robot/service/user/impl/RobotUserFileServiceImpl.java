package com.biyao.robot.service.user.impl;

import com.biyao.robot.common.constant.CommonConstant;
import com.biyao.robot.common.constant.Properties;
import com.biyao.robot.common.utils.RandomUtil;
import com.biyao.robot.service.user.RobotUserFileService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Created by zhangxiaolei.
 * Create Date: 2018/12/25 16:12
 * Description: 机器人基本信息实现
 */
@Service
public class RobotUserFileServiceImpl implements RobotUserFileService {

    /**
     * 配置文件.
     */
    @Resource
    private Properties properties;

    /**
     * 随机从本地文件中获取一个机器人信息.
     * @param type 机器人属性
     * @return 结果
     */
    @Override
    public String randomRobotUserFile(final String type) {
        String robotUser = null;
        if (StringUtils.isNotBlank(type)
                && type.equals(CommonConstant.ROBOT_USER_NICK_NAME)) {
            robotUser = properties.robotName;
        } else if (StringUtils.isNotBlank(type)
                && type.equals(CommonConstant.ROBOT_USER_AVATAR)) {
            if(properties.onlineFlag.equals(CommonConstant.onlineFlag)){
                robotUser = properties.robotAvatar;
            } else {
                robotUser = properties.robotAvatarTest;
            }


        }

        if (StringUtils.isNotBlank(robotUser)) {
            List<String> robotUsers = Arrays.asList(robotUser.split(";"));
            Random random = new Random();
            int index = random.nextInt(robotUsers.size());
            return robotUsers.get(index);
        }
        return null;
    }

    /**
     * 随机从本地文件中获取多个机器人信息.
     * @param type 机器人属性
     * @param counts 获取数量
     * @return 结果
     */
    @Override
    public List<String> batchRandomRobotUserFile(final String type,
                                                 final int counts) {
        String robotUser = null;
        if (StringUtils.isNotBlank(type)
                && type.equals(CommonConstant.ROBOT_USER_NICK_NAME)) {
            robotUser = properties.robotName;
        } else if (StringUtils.isNotBlank(type)
                && type.equals(CommonConstant.ROBOT_USER_AVATAR)) {
            if(properties.onlineFlag.equals(CommonConstant.onlineFlag)){
                robotUser = properties.robotAvatar;
            } else {
                robotUser = properties.robotAvatarTest;
            }
        }
        if (StringUtils.isNotBlank(robotUser)) {

            List<String> robotUsers = Arrays.asList(robotUser.split(";"));
            List<String> backList = RandomUtil.randomData(robotUsers, counts);
            return backList;
        }
        return null;
    }
}
