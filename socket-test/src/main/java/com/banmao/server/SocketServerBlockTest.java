package com.banmao.server;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Company: 杭州领图信息科技有限公司
 *
 * @description: 阻塞测试
 * @author: banmao
 * @date: 2022/8/16 9:40
 */
public class SocketServerBlockTest {

    public static void main(String[] args) throws IOException {
        // 这里端口就写死了，8083
        ServerSocket serverSocket = new ServerSocket(8083);
        // block1：第一步阻塞，等待客户端的连接
        Socket socket = serverSocket.accept();
        System.out.println(">>>>>>>>>>>>>>>>>>>>终于有客户端连上来了！<<<<<<<<<<<<<<<<<");
        InputStream inputStream = socket.getInputStream();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        // block2：第二步阻塞，等待数据的传输并被告知传输结束
        for(;;) {
            String strdd = bufferedReader.readLine();
            System.out.println(strdd);
        }
//        String str = "";
//        if ((str = bufferedReader.readLine()).contains("拜拜！")) {
//            System.out.println("收到的数据为：" + str);
//        }
        // 关闭流
//        inputStream.close();
//        bufferedReader.close();
//        socket.close();
    }
}
