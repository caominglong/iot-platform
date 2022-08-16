package com.banmao.concurrent.servers;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Company: 杭州领图信息科技有限公司
 *
 * @description:
 * 处理多个客户端，以下采用的是每一个客户端启动一个线程
 * @author: banmao
 * @date: 2022/8/16 17:22
 */
public class SocketSeverConcurrent {

    public static void main(String[] args) throws IOException {
        // 端口写死，8085
        ServerSocket serverSocket = new ServerSocket(8085);
        Socket socket = serverSocket.accept();
        Thread thread = new Thread();
    }

    public static void handleClient(Socket socket) {
        // 处理客户端
    }
}
