package com.banmao.concurrent.servers;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Company: 杭州领图信息科技有限公司
 *
 * @description: 处理多个客户端，以下采用的是每一个客户端启动一个线程
 * @author: banmao
 * @date: 2022/8/16 17:22
 */
public class SocketSeverUseThreadPool {

    private final int NTHREADS = 100;
    private final ServerSocket serverSocket;
    /**
     * 存储每个线程对应的socket连接，key用线程名
     */
    private Map<String, Socket> socketMap = new HashMap<>(100);

    public SocketSeverUseThreadPool(int port) throws IOException {
        // 1、创建一个serverSocket，并监听端口port
        serverSocket = new ServerSocket(port);
    }

    public void run() throws IOException {
        while (true) {
            // 2、接收客户端连接
            Socket client = serverSocket.accept();
            handleClient(client);
        }
    }

    public static void main(String[] args) throws IOException {
        // 端口写死，8085
        SocketSeverUseThreadPool socketSeverConcurrent = new SocketSeverUseThreadPool(8085);
        System.out.println("server在线");
        socketSeverConcurrent.run();
    }

    public void handleClient(Socket socket) {
        // 3、处理客户端通信
        // 每来一个客户端，启动一个线程
        Thread thread = new Thread(this::readResponse);
        socketMap.put(thread.getName(), socket);
        thread.start();
    }

    private void readResponse() {
        try {
            int number = 1;
            Socket socket = null;
            while (number < 3) {
                number++;
                // 读取客户端的消息
                Thread thread = Thread.currentThread();
                socket = socketMap.get(thread.getName());
                InputStream inputStream = socket.getInputStream();
                byte[] bytes = new byte[1024];
                int readByte;
                if ((readByte = inputStream.read(bytes)) != -1) {
                    System.out.println(new String(bytes, 0, readByte));
                }
                // 往客户端发消息
                System.out.println("往客户端发消息！");
                writeRequest(socket);
                // 忽略流的关闭
            }
            socket.close();
            System.exit(0);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void writeRequest(Socket socket) throws IOException {
        OutputStream outputStream = socket.getOutputStream();
        byte[] bytes = new byte[1024];
        int n;
        while ((n = System.in.read(bytes)) != -1) {
            outputStream.write(bytes, 0, n);
            // 写完即退出，不然会一直阻塞
            break;
        }
    }
}
