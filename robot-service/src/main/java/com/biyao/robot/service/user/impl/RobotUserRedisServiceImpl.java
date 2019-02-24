package com.biyao.robot.service.user.impl;

import com.biyao.robot.common.constant.CommonConstant;
import com.biyao.robot.common.constant.RedisCommonKey;
import com.biyao.robot.common.utils.RedisUtil;
import com.biyao.robot.service.user.RobotUserNativeService;
import com.biyao.robot.service.user.RobotUserRedisService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangxiaolei.
 * Create Date: 2018/12/26 15:36
 * Description: 获取机器人信息从Redis
 */
@Service
public class RobotUserRedisServiceImpl implements RobotUserRedisService {

    /**
     * 日志.
     */
    private static final Logger LOGGER = LoggerFactory
            .getLogger(RobotUserRedisServiceImpl.class);

    /**
     * redis.
     */
    @Resource
    private RedisUtil redisUtil;

    /**
     * 本地缓存.
     */
    @Resource
    private RobotUserNativeService robotUserNativeService;

    /**
     * 批量获取Redis缓存中的机器人信息.
     * @param type 机器人属性
     * @param counts 获取数量
     * @return 结果
     * @throws Exception 异常
     */
    @Override
    public List<String> randomRobotUserRedis(final String type,
                                             final int counts) {
        List<String> result = new ArrayList<>();
        if (StringUtils.isNotBlank(type)
                && type.equals(CommonConstant.ROBOT_USER_NICK_NAME)) {
            result = redisUtil.randMemberSetMapGetCache(
                    RedisCommonKey.ROBOT_USER_NICK_NAME, counts);
        } else if (StringUtils.isNotBlank(type)
                && type.equals(CommonConstant.ROBOT_USER_AVATAR)) {
            result = redisUtil.randMemberSetMapGetCache(
                    RedisCommonKey.ROBOT_USER_AVATAR, counts);
        }
        if (CollectionUtils.isEmpty(result)) {
            LOGGER.error("类型："+ type + ",获取Redis缓存数据失败，开始获取本地缓存");
            result = robotUserNativeService.randomRobotUserNative(type, counts);
        }
        return result;
    }
}
