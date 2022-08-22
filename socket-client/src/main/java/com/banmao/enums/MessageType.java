package com.banmao.enums;

/**
 * @author banmao
 * @version V1.0.0
 * @date 2022/8/21 上午8:52
 * @description
 */
public enum MessageType {
    /**
     * 验证用户
     */
    VERIFY_USER,
    /**
     * 登录失败
     */
    LOGIN_FAILURE,

    /**
     * 登录成功
     */
    LOGIN_SUCCESS,
    /**
     * 获取用户在线列表
     */
    ONLINE_USER,
    /**
     * 群发消息
     */
    SEND_MSG_TO_ALL,

    /**
     * 私聊消息
     */
    SEND_MSG_TO_ONE,

    /**
     * 发送文件
     */
    SEND_FILE,

    /**
     * 退出系统
     */
    EXIT_SYSTEM;
}