package com.banmao.clients;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Company: 杭州领图信息科技有限公司
 *
 * @description: 阻塞测试
 * @author: banmao
 * @date: 2022/8/16 9:40
 */
public class SocketClientBlockTest {

    public static void main(String[] args) throws IOException {
        // 端口写死8083
        Socket socket = new Socket(InetAddress.getLocalHost().getHostAddress(), 8083);
        OutputStream outputStream = socket.getOutputStream();
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream));
//        System.out.println("我写，写，一直在写！");
//        bufferedWriter.write("hello server!");
        // bufferedWriter.write("\n");
        // 给定一个结束标记
        for(;;) {
            bufferedWriter.write("拜拜！");
            bufferedWriter.write("\r");
            // 冲刷缓冲区
            bufferedWriter.flush();
        }
        // 关闭流
//        outputStream.close();
//        bufferedWriter.close();
//        socket.close();
    }
}
