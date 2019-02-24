package com.biyao.robot.common.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by zhangxiaolei.
 * Create Date: 2018/12/26 19:16
 * Description: 随机获取工具类
 */
public final class RandomUtil {

    /**
     * 构造方法.
     */
    private RandomUtil() {

    }

    /**
     * 获取list随机数，不重复.
     * @param paramList 入参list
     * @param counts 数量
     * @return 结果
     */
    public static List<String> randomData(final List<String> paramList,
                                          final int counts) {

        List<String> resultList = new ArrayList<>();

        Random random = new Random();
        int backSum;
        if (paramList.size() >= counts) {
            backSum = counts;
        } else {
            backSum = paramList.size();
        }
        List<Integer> tempList = new ArrayList<>();
        for (int i = 0; i < backSum; i++) {
            int temp = random.nextInt(paramList.size()); // 将产生的随机数作为被抽list的索引
            if (!tempList.contains(temp)) {
                tempList.add(temp);
                resultList.add(paramList.get(temp));
            } else {
                i--;
            }
        }

        return resultList;

    }
}
