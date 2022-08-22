package com.banmao.service;

import com.banmao.dataobjects.User;
import sun.rmi.server.UnicastServerRef;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author banmao
 * @version V1.0.0
 * @date 2022/8/21 上午10:04
 * @description
 */
public class SocketThreadManageService {
    // key为userId，value为SocketThread
    public static Map<String, SocketThread> socketThreadMap = new HashMap<>();

    public static SocketThread getSocketThread(String userId) {
        return socketThreadMap.get(userId);
    }

    public static void addSocketThread(String userId, SocketThread socketThread) {
        socketThreadMap.put(userId, socketThread);
    }


    public static List<User> getAllUser() {
        List<User> users = new ArrayList<>();
        for(String userId : socketThreadMap.keySet()) {
            users.add(new User(userId,null));
        }
        return users;
    }

}