package com.banmao.server;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author banmao
 * @version V1.0.0
 * @date 2022/8/14 上午11:33
 * @description socker服务端（简单版）
 * 支持客户端的一次通信，通信结束及连接断开且服务停止
 */
public class SocketServerSimple {

    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Port parameter is required");
            return;
        }
        int port = Integer.parseInt(args[0]);
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(port);
            // 阻塞，等待连接
            System.out.println("listen port:" + port + " on Server:" + InetAddress.getLocalHost().getHostAddress() + ", Waiting for connection");
            Socket communicateSocket = serverSocket.accept();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(communicateSocket.getInputStream()));
            // 读取一行数据
            String s = bufferedReader.readLine();
            // 输出数据
            System.out.println(s);
            communicateSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}