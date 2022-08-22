package com.banmao.utils;

import java.io.*;
import java.net.Socket;

/**
 * @author banmao
 * @version V1.0.0
 * @date 2022/8/21 上午11:27
 * @description
 */
public class ObjectUtil {

    public static ObjectOutputStream getOos(Socket socket) {
        OutputStream outputStream = null;
        try {
            outputStream = socket.getOutputStream();
            return new ObjectOutputStream(outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static ObjectInputStream getOis(Socket socket) {
        InputStream inputStream = null;
        try {
            inputStream = socket.getInputStream();
            return new ObjectInputStream(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}