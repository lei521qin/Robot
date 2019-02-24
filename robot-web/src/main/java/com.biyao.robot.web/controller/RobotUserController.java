package com.biyao.robot.web.controller;

import com.biyao.robot.client.user.dto.RobotUserDto;
import com.biyao.robot.client.user.service.IRobotUserListService;
import com.biyao.robot.common.bean.Result;
import com.biyao.robot.common.code.ErrorCode;
import com.biyao.robot.common.constant.CommonConstant;
import com.biyao.robot.common.constant.Properties;
import com.biyao.robot.common.constant.RedisCommonKey;
import com.biyao.robot.common.exception.RobotException;
import com.biyao.robot.common.utils.RedisUtil;
import com.biyao.robot.service.user.RobotUserFileService;
import com.biyao.robot.web.model.QueryRobotParam;
import com.biyao.robot.web.model.SynchronousDataParam;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

/**
 * Created by zhangxiaolei.
 * Create Date: 2018/12/24 20:18
 * Description: 机器人基本信息
 */
@RestController
public class RobotUserController {

    /**
     * 文件中获取.
     */
    @Resource
    private RobotUserFileService robotUserFileService;

    /**
     * redis工具类.
     */
    @Resource
    private RedisUtil redisUtil;

    /**
     * 配置文件属性.
     */
    @Resource
    private Properties properties;

    /**
     * 本地缓存获取.
     */
    @Resource
    private IRobotUserListService robotUserListService;

    /**
     * 删除缓存.
     *
     * @param synchronousDataParam 输入参数类
     * @throws Exception 异常处理
     */
    @RequestMapping(value = "/robot/deleteRedis", method = RequestMethod.GET)
    public void deleteRedis(
            final SynchronousDataParam synchronousDataParam) throws Exception {
        if (StringUtils.isNotBlank(synchronousDataParam.getType())
                && synchronousDataParam.getType()
                .equals(CommonConstant.ROBOT_USER_NICK_NAME)) {
            redisUtil.del(RedisCommonKey.ROBOT_USER_NICK_NAME);
        } else if (StringUtils.isNotBlank(synchronousDataParam.getType())
                && synchronousDataParam.getType()
                .equals(CommonConstant.ROBOT_USER_AVATAR)) {
            redisUtil.del(RedisCommonKey.ROBOT_USER_AVATAR);
        }

    }

    /**
     * 同步本地文件数据到Redis中.
     *
     * @param synchronousDataParam 入参
     * @return 处理结果
     * @throws Exception 异常
     */
    @RequestMapping(value = "/robot/synchronousDataToRedis",
            method = RequestMethod.GET)
    public String synchronousDataToRedis(
            final SynchronousDataParam synchronousDataParam) throws Exception {

        if (null == synchronousDataParam) {
            throw new RobotException(ErrorCode.COMMON_PARAM_NULL);
        }

        long startTime = System.currentTimeMillis();
        if (synchronousDataParam.getNum() > 0) {
            // 同步多个
            synchronousBatchDataToRedis(synchronousDataParam);
        } else {
            // 同步全量
            synchronousAllDataToRedis(synchronousDataParam);

        }
        long endTime = System.currentTimeMillis();

        return "总共运行时间为：" + String.valueOf((float) endTime - startTime);
    }


    /**
     * 同步多个.
     *
     * @param synchronousDataParam 入参
     * @throws Exception 异常
     */
    private void synchronousBatchDataToRedis(
            final SynchronousDataParam synchronousDataParam) throws Exception {
        List<String> result = robotUserFileService
                .batchRandomRobotUserFile(synchronousDataParam.getType(),
                synchronousDataParam.getNum());
        if (StringUtils.isNotBlank(synchronousDataParam.getType())
                && synchronousDataParam.getType()
                .equals(CommonConstant.ROBOT_USER_NICK_NAME)) {
            redisUtil.setMapToCache(RedisCommonKey.ROBOT_USER_NICK_NAME,
                    result, 0);
        } else if (StringUtils.isNotBlank(synchronousDataParam.getType())
                && synchronousDataParam.getType()
                .equals(CommonConstant.ROBOT_USER_AVATAR)) {
            redisUtil.setMapToCache(RedisCommonKey.ROBOT_USER_AVATAR,
                    result, 0);
        }
    }

    /**
     * 同步全量.
     *
     * @param synchronousDataParam 入参
     * @throws Exception 异常
     */
    private void synchronousAllDataToRedis(
            final SynchronousDataParam synchronousDataParam) throws Exception {

        String robotUser = null;
        if (StringUtils.isNotBlank(synchronousDataParam.getType())
                && synchronousDataParam.getType()
                .equals(CommonConstant.ROBOT_USER_NICK_NAME)) {
            robotUser = properties.robotName;
        } else if (StringUtils.isNotBlank(synchronousDataParam.getType())
                && synchronousDataParam.getType()
                .equals(CommonConstant.ROBOT_USER_AVATAR)) {
            if(properties.onlineFlag.equals(CommonConstant.onlineFlag)){
                robotUser = properties.robotAvatar;
            } else {
                robotUser = properties.robotAvatarTest;
            }
        }
        if (StringUtils.isNotBlank(robotUser)) {
            List<String> robotUsers = Arrays.asList(robotUser.split(";"));
            List<List<String>> partition = Lists.partition(robotUsers,
                    CommonConstant.DEFAULT_PAGE_SIZE);
            for (List<String> list : partition) {
                if (StringUtils.isNotBlank(synchronousDataParam.getType())
                        && synchronousDataParam.getType()
                        .equals(CommonConstant.ROBOT_USER_NICK_NAME)) {
                    redisUtil.setMapToCache(RedisCommonKey.ROBOT_USER_NICK_NAME,
                            list, 0);
                } else if (StringUtils.isNotBlank(
                        synchronousDataParam.getType())
                        && synchronousDataParam.getType()
                        .equals(CommonConstant.ROBOT_USER_AVATAR)) {
                    redisUtil.setMapToCache(RedisCommonKey.ROBOT_USER_AVATAR,
                            list, 0);
                }
            }
        }
    }

    /**
     * 批量获取机器人的昵称.
     *
     * @param queryRobotParam 入参
     * @return 结果
     */
    @RequestMapping(value = "/robot/getRobotUserNickNameBatch",
            method = RequestMethod.GET)
    public Result<List<String>> getRobotUserNickNameBatch(
            final QueryRobotParam queryRobotParam) {
        Result<List<String>> result = robotUserListService
                .getRobotUserNickeNameBatch(queryRobotParam.getCounts(),
                queryRobotParam.getCaller());
        return result;
    }

    /**
     * 批量获取机器人的昵称.
     *
     * @param queryRobotParam 入参
     * @return 结果
     */
    @RequestMapping(value = "/robot/getRobotUserHeadPictureBatch",
            method = RequestMethod.GET)
    public Result<List<String>> getRobotUserHeadPictureBatch(
            final QueryRobotParam queryRobotParam) {
        Result<List<String>> result = robotUserListService
                .getRobotUserHeadPictureBatch(queryRobotParam.getCounts(),
                queryRobotParam.getCaller());
        return result;
    }

    /**
     * 批量获取机器人（包含：头像与昵称）.
     *
     * @param queryRobotParam 入参
     * @return 结果
     */
    @RequestMapping(value = "/robot/getRobotUserBatch",
            method = RequestMethod.GET)
    public Result<List<RobotUserDto>> getRobotUserBatch(
            final QueryRobotParam queryRobotParam) {
        Result<List<RobotUserDto>> result = robotUserListService
                .getRobotUserBatch(queryRobotParam.getCounts(),
                queryRobotParam.getCaller());
        return result;
    }

}
