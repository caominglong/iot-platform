package com.banmao.clients;

import sun.nio.cs.StandardCharsets;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * Company: 杭州领图信息科技有限公司
 *
 * @description:
 * @author: banmao
 * @date: 2022/8/16 10:57
 */
public class SocketClientByteTest {

    public static void main(String[] args) throws IOException {
        // 端口写死8083
        Socket socket = new Socket(InetAddress.getLocalHost().getHostAddress(), 8083);
        OutputStream outputStream = socket.getOutputStream();
//        byte[] bytes = new byte[1024];
        outputStream.write("你好,我叫曹明龙，我今天26岁，我从事于计算机编程行业，是一名程序员！".getBytes(UTF_8));
        outputStream.flush();
    }
}
