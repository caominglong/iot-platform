package com.banmao.concurrent.clients;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;

/**
 * Company: 杭州领图信息科技有限公司
 *
 * @description: 多个客户端连接上服务端，然后进行传输消息
 * 此为客户端1
 * @author: banmao
 * @date: 2022/8/16 16:42
 */
public class SocketClient1Pool {

    private final Socket socket;

    public SocketClient1Pool(String host, int port) throws IOException {
        // 1、创建socket，并连接服务器
        socket = new Socket(host, port);
    }


    public static void main(String[] args) {
        // 端口写死，为8085
        SocketClient1Pool socketMultiClient1 = null;
        try {
            socketMultiClient1 = new SocketClient1Pool(InetAddress.getLocalHost().getHostAddress(), 8085);
            socketMultiClient1.run();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void run() throws IOException {
        // 2、和服务端进行通讯
        int number = 1;
        while (number < 3) {
            number++;
            // 2.1、往服务端写数据
            System.out.println("客户端1发消息！");
            writeRequest();
            // 2.2、读取服务端的数据
            Thread thread = new Thread(this::readResponse);
            thread.start();
        }
    }

    private void writeRequest() throws IOException {
        OutputStream outputStream = socket.getOutputStream();
        byte[] bytes = new byte[1024];
        int n;
        while((n = System.in.read(bytes)) > 0) {
            outputStream.write(bytes, 0, n);
            // 写完即退出，不然会一直阻塞
            break;
        }
    }

    private void readResponse() {
        try {
            InputStream inputStream = socket.getInputStream();
            byte[] bytes = new byte[1024];
            int readByte;
            while ((readByte = inputStream.read(bytes)) != -1) {
                System.out.print("server return：");
                System.out.println(new String(bytes, 0, readByte));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
