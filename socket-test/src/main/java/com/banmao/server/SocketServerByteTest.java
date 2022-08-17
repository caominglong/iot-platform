package com.banmao.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * Company: 杭州领图信息科技有限公司
 *
 * @description:
 * @author: banmao
 * @date: 2022/8/16 10:57
 */
public class SocketServerByteTest {

    public static void main(String[] args) throws IOException {
        // 端口写死8083
        ServerSocket serverSocket = new ServerSocket(8083);
        Socket socket = serverSocket.accept();
        InputStream inputStream = socket.getInputStream();
        byte[] bytes = new byte[32];
        inputStream.read(bytes);
        String str = new String(bytes, UTF_8);
        System.out.println(str);
        // 关闭流
//        inputStream.close();
//        socket.close();
//        serverSocket.close();
    }
}
