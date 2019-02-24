package com.biyao.robot.service.user.impl;

import com.biyao.robot.common.code.ErrorCode;
import com.biyao.robot.common.constant.CommonConstant;
import com.biyao.robot.common.exception.RobotRunTimeException;
import com.biyao.robot.common.utils.RandomUtil;
import com.biyao.robot.service.user.RobotUserFileService;
import com.biyao.robot.service.user.RobotUserNativeService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by zhangxiaolei.
 * Create Date: 2018/12/25 17:36
 * Description: 本地缓存实现
 */
@Lazy
@Service
public class RobotUserNativeServiceImpl implements RobotUserNativeService {

    /**
     * 日志.
     */
    private static final Logger LOGGER = LoggerFactory
            .getLogger(RobotUserNativeServiceImpl.class);

    /**
     * 机器人昵称.
     */
    private static Map<String, List<String>> nickNameAndNickNameListMap
            = new ConcurrentHashMap<>();

    /**
     * 机器人头像.
     */
    private static Map<String, List<String>> avatarAndAvatarListMap
            = new ConcurrentHashMap<>();

    /**
     * 本地文件.
     */
    @Autowired(required = false)
    private RobotUserFileService robotUserFileService;

    /**
     * 初始化.
     * @throws Exception 异常
     */
    @Autowired
    private void init() throws Exception {
        if (CollectionUtils.isEmpty(nickNameAndNickNameListMap)
                || CollectionUtils.isEmpty(avatarAndAvatarListMap)) {
            // 获取机器人昵称
            getNickNameList();
            // 获取机器人昵称头像
            getAvatarInfo();
        }
    }

    /**
     * 获取机器人昵称.
     * @return 昵称集合
     * @throws Exception 异常
     */
    public Map<String, List<String>> getNickNameList() throws Exception {

        List<String> resultList = robotUserFileService.batchRandomRobotUserFile(
                CommonConstant.ROBOT_USER_NICK_NAME,
                CommonConstant.DEFAULT_NUM);
        if (!CollectionUtils.isEmpty(resultList)) {
            nickNameAndNickNameListMap.put(
                    CommonConstant.ROBOT_USER_NICK_NAME, resultList);
            LOGGER.info("机器人昵称本地缓存保存成功！");
        }
        return nickNameAndNickNameListMap;
    }

    /**
     * 获取机器人头像.
     * @return 头像
     * @throws Exception 异常
     */
    public Map<String, List<String>> getAvatarInfo() throws Exception {
        List<String> resultList = robotUserFileService.batchRandomRobotUserFile(
                CommonConstant.ROBOT_USER_AVATAR,
                CommonConstant.DEFAULT_NUM);
        if (!CollectionUtils.isEmpty(resultList)) {
            avatarAndAvatarListMap.put(
                    CommonConstant.ROBOT_USER_AVATAR, resultList);
            LOGGER.info("机器人头像本地缓存保存成功！");
        }
        return avatarAndAvatarListMap;
    }

    /**
     * 批量获取本地缓存中的机器人信息.
     * @param type 机器人属性
     * @param counts 获取数量
     * @return 结果
     * @throws Exception 异常
     */
    @Override
    public List<String> randomRobotUserNative(final String type,
                                              final int counts) {
        if (counts > CommonConstant.DEFAULT_NUM) {
            throw new RobotRunTimeException(ErrorCode.COMMON_PARAM_NULL);
        }
        List<String> nativeList = new ArrayList<>();
        if (StringUtils.isNotBlank(type)
                && type.equals(CommonConstant.ROBOT_USER_NICK_NAME)) {
            nativeList = nickNameAndNickNameListMap.get(
                    CommonConstant.ROBOT_USER_NICK_NAME);
        } else if (StringUtils.isNotBlank(type)
                && type.equals(CommonConstant.ROBOT_USER_AVATAR)) {
            nativeList = avatarAndAvatarListMap.get(
                    CommonConstant.ROBOT_USER_AVATAR);
        }

        List<String> backList;
        if (!CollectionUtils.isEmpty(nativeList)) {
            backList = RandomUtil.randomData(nativeList, counts);
        } else {
            LOGGER.error("类型："+ type + ",获取本地缓存数据失败，开始获取本地配置文件数据");
            backList = robotUserFileService.batchRandomRobotUserFile(
                    type, counts);
        }
        return backList;
    }
}
