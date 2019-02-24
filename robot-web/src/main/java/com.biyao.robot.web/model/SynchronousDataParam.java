package com.biyao.robot.web.model;

/**
 * Created by zhangxiaolei
 * Create Date: 2018/12/25 15:48
 * Description: 同步数据参数
 */
public class SynchronousDataParam {

    /**
     * 同步类型
     */
    private String type;

    /**
     * 同步数量
     */
    private int num;

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
