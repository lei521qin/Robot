package com.biyao.robot.web.model;

/**
 * Created by zhangxiaolei.
 * Create Date: 2018/12/26 16:49
 * Description: 参数类
 */
public class QueryRobotParam {

    /**
     * 获取数量.
     */
    private int counts;

    /**
     * 调用系统.
     */
    private String caller;

    public int getCounts() {
        return counts;
    }

    public void setCounts(final int counts) {
        this.counts = counts;
    }

    public String getCaller() {
        return caller;
    }

    public void setCaller(final String caller) {
        this.caller = caller;
    }
}
