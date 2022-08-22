package com.banmao.utils;

import com.banmao.dataobjects.User;

import java.util.HashMap;
import java.util.Map;

/**
 * @author banmao
 * @version V1.0.0
 * @date 2022/8/21 上午10:40
 * @description
 */
public class VerifyUtil {

    // key为userId，value为password
    public static Map<String, String> userMap = new HashMap();

    static {
        userMap.put("100", "123456");
        userMap.put("200", "123456");
        userMap.put("300", "123456");
        userMap.put("杜兰特", "123456");
        userMap.put("詹姆斯", "456432");
        userMap.put("库里", "123456");
    }

    public static boolean verify(User user) {
        if (userMap.containsKey(user.getUserId())) {
            if (userMap.get(user.getUserId()).equals(user.getPassword())) {
                return true;
            }
        }
        return false;
    }
}