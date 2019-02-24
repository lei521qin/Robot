package com.biyao.robot.common.utils;

import com.by.bimdb.service.RedisSentinelService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * @author wangbo.
 * @version 1.0 2017/8/23
 */
@Component
public class RedisUtil {

    /**
     * 日志.
     */
    private static final Logger LOG = LoggerFactory
            .getLogger(RedisUtil.class);

    /**
     * Redis service
     */
    @Resource
    private RedisSentinelService redisService;

    /**
     * 刪除信息.
     * @param key 入参
     * @return 结果
     */
    public Long del(final String key) {
        Long isOk = 0L;
        try {
            isOk = this.redisService.del(key);
        } catch (Exception e) {
            LOG.error("putStringToCache, key:" + key, e);
        }
        return isOk;
    }

    /**
     * set 集合 保存数据.
     * @param key 主键
     * @param valueList 值
     * @param exp 失效时间
     * @return 结果
     * @throws Exception 异常
     */
    public Boolean setMapToCache(final String key,
                                 final List<String> valueList, final int exp)
            throws Exception {
        boolean isOk = true;
        try {
            String[] array = valueList.toArray(new String[valueList.size()]);
            if (exp > 0) {
                this.redisService.sadd(key, array);
                this.redisService.expire(key, exp);
            } else {
                this.redisService.sadd(key, array);
            }
        } catch (Exception e) {
            LOG.error("putStringToCache, key:" + key, e);
            isOk = false;
        }
        return isOk;
    }

    /**
     * set 集合随机获取多个值.
     * @param key 主键
     * @param count 数量
     * @return 结果
     * @throws Exception
     */
    public List<String> randMemberSetMapGetCache(final String key,
                                                 final int count) {
        List<String> list = redisService.srandmember(key, count);
        // 不会返回null 而是返回一个空的集合 避免调用方出现NPE
        return Optional.ofNullable(list).orElse(Collections.emptyList());
    }

}
