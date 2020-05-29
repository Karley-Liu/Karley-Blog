package com.lin.karley.Common;

import com.alibaba.fastjson.JSON;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.*;

/**
 * @Description Description
 * @Author Karley LIn
 * @Date Created in 2020/5/17
 */
//@Getter
public enum ResponseCode {
    REGISTERERROR(0,"注册失败"),
    REGISTERSUCCESS(1,"注册成功"),
    LOGINERROR(2,"登录失败"),
    LOGINSUCCESS(3,"登录成功"),
    LOGINNOTFOUND(4,"此用户不存在");

//    成员变量
    private int status;
    private String msg;

//    构造方法
    ResponseCode(int status, String msg) {
        this.status = status;
        this.msg = msg;
    }

    ResponseCode(int status){
        this.status = status;
    }

    public int getStatus() {
        return status;
    }

    public String getMsg() {
        return msg;
    }
//    将数据缓存到map中
    private static final Map<Integer,String> map = new HashMap<Integer,String>();
    static {
        for (ResponseCode code: ResponseCode.values()){
            map.put(code.getStatus(),code.getMsg());
        }
    }

    public static String getMsgByStatus(Integer status) {
        return new String(map.get(status));
    }
}
