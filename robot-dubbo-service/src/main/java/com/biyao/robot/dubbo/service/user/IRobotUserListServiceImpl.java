package com.biyao.robot.dubbo.service.user;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.biyao.robot.client.user.dto.RobotUserDto;
import com.biyao.robot.client.user.service.IRobotUserListService;
import com.biyao.robot.common.aop.FunctionName;
import com.biyao.robot.common.bean.Result;
import com.biyao.robot.common.constant.CommonConstant;
import com.biyao.robot.dubbo.service.user.paramvalidator.RobotUserParamValidator;
import com.biyao.robot.service.user.RobotUserRedisService;
import com.by.profiler.annotation.BProfiler;
import com.by.profiler.annotation.MonitorType;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by zhangxiaolei.
 * Create Date: 2018/12/24 16:13
 * Description: 机器人基本信息
 */
public class IRobotUserListServiceImpl implements IRobotUserListService {

    /**
     * 缓存获中机器人.
     */
    @Resource
    private RobotUserRedisService robotUserRedisService;

    /**
     * 批量获取机器人昵称.
     * @param counts
     * @param caller
     * @return
     */
    @FunctionName(description = "批量获取机器人昵称")
    @BProfiler(key = "com.biyao.robot.dubbo.service.user."
                    +
                    "IRobotUserListServiceImpl.getRobotUserNickeNameBatch",
            monitorType = {MonitorType.TP,
                    MonitorType.HEARTBEAT,
                    MonitorType.FUNCTION_ERROR })
    @Override
    public Result<List<String>> getRobotUserNickeNameBatch(
            final int counts,
            final String caller) {
        Result<List<String>> result = new Result<>();
        RobotUserParamValidator.validateRobotUserBatchParam(counts, caller);
        List<String> resultList = robotUserRedisService.randomRobotUserRedis(
                CommonConstant.ROBOT_USER_NICK_NAME,
                counts);
        result.setData(resultList);
        return result;
    }

    /**
     * 批量获取机器人头像.
     * @param counts
     * @param caller
     * @return
     */
    @FunctionName(description = "批量获取机器人头像")
    @BProfiler(
            key = "com.biyao.robot.dubbo.service.user."
            +
            "IRobotUserListServiceImpl.getRobotUserHeadPictureBatch",
            monitorType = {MonitorType.TP,
                    MonitorType.HEARTBEAT,
                    MonitorType.FUNCTION_ERROR })
    @Override
    public Result<List<String>> getRobotUserHeadPictureBatch(
            final int counts,
            final String caller) {
        Result<List<String>> result = new Result<>();
        RobotUserParamValidator.validateRobotUserBatchParam(counts, caller);
        List<String> resultList = robotUserRedisService.
                randomRobotUserRedis(CommonConstant.ROBOT_USER_AVATAR, counts);
        result.setData(resultList);
        return result;
    }

    /**
     * 批量获取机器人(包含：昵称+头像）.
     * @param counts
     * @param caller
     * @return
     */
    @FunctionName(description = "批量获取机器人(包含：昵称+头像）")
    @BProfiler(key = "com.biyao.robot.dubbo.service.user."
            +
            "IRobotUserListServiceImpl.getRobotUserHeadPictureBatch",
            monitorType = { MonitorType.TP,
                    MonitorType.HEARTBEAT,
                    MonitorType.FUNCTION_ERROR })
    @Override
    public Result<List<RobotUserDto>> getRobotUserBatch(
            final int counts,
            final String caller) {
        Result<List<RobotUserDto>> result = new Result<>();
        RobotUserParamValidator.validateRobotUserBatchParam(counts, caller);

        // 随机获取昵称
        List<String> nickNameList = robotUserRedisService
                .randomRobotUserRedis(CommonConstant.ROBOT_USER_NICK_NAME,
                        counts);
        // 随机获取头像
        List<String> avatarList = robotUserRedisService
                .randomRobotUserRedis(CommonConstant.ROBOT_USER_AVATAR,
                        counts);
        // 返回结果封装
        List<RobotUserDto> robotUserDtoList = packageRobotUserDto(
                nickNameList, avatarList, counts);
        result.setData(robotUserDtoList);
        return result;
    }


    /**
     * 返回结果封装.
     * @param nickNameList 昵称list
     * @param avatarList 头像list
     * @param counts 数量
     * @return 机器人对象list
     */
    private List<RobotUserDto> packageRobotUserDto(
            final List<String> nickNameList,
            final List<String> avatarList,
            final int counts) {
        List<RobotUserDto> robotUserDtoList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(nickNameList)
                && CollectionUtils.isNotEmpty(avatarList)) {
            for (int i = 0; i < counts; i++) {
                Random random = new Random();
                int nameIndex = random.nextInt(nickNameList.size());
                int avatarIndex = random.nextInt(avatarList.size());
                RobotUserDto robotUserDto = new RobotUserDto();
                // 昵称
                if (nickNameList.get(nameIndex).length() > CommonConstant.ROBOT_USER_NICK_MAX_LENGTH) {
                    robotUserDto.setUserNickName(
                            nickNameList.get(nameIndex).substring(0, CommonConstant.ROBOT_USER_NICK_MAX_LENGTH));
                } else {
                    robotUserDto.setUserNickName(nickNameList.get(nameIndex));
                }

                // 头像
                robotUserDto.setUserHeadPicture(avatarList.get(avatarIndex));

                robotUserDtoList.add(robotUserDto);
            }
        }
        return robotUserDtoList;
    }
}
