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
public class SocketServerPersist {

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
            System.out.println("listen port:" + port + " on host:" + InetAddress.getLocalHost().getHostAddress() + ", Waiting for connection");
            Socket communicateSocket = serverSocket.accept();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(communicateSocket.getInputStream()));
            // 读取一行数据
            String s = bufferedReader.readLine();
            // 输出数据
            System.out.println(s);
            // 通知客户端，收到数据
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(communicateSocket.getOutputStream()));
            if ("让我们开始数数".equals(s)) {
                bufferedWriter.write("好的，呀～");
                bufferedWriter.flush();
            } else {
                bufferedWriter.write("收到的数为：" + s);
                bufferedWriter.flush();
            }
            communicateSocket.shutdownOutput();
            // communicateSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}