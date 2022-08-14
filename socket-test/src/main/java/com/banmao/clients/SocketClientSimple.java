package com.banmao.clients;

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;

/**
 * @author banmao
 * @version V1.0.0
 * @date 2022/8/14 下午2:41
 * @description socket客户端构建（简单版）
 * 当与服务端通信完，通信结束，连接断开
 */
public class SocketClientSimple {

    public static void main(String[] args) throws IOException {
        if (args.length != 2) {
            System.out.println("Port and RemoteAddress parameters are required");
            return;
        }
        String remoteAddress = args[0];
        int port = Integer.parseInt(args[1]);
        connectAndCommunication(remoteAddress, port);
    }

    private static void connectAndCommunication(String remoteAddress, int port) throws IOException {
        Socket socket = new Socket(remoteAddress, port);
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        bufferedWriter.write("我是张三");
        // 输出标志，表示"这句话讲完了"
        socket.shutdownOutput();
        bufferedWriter.close();
        socket.close();
    }
}