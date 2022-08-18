package com.banmao.http_imitate;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.*;

/**
 * @author banmao
 * @version V1.0.0
 * @date 2022/8/17 下午9:16
 * @description
 */
public class SocketServerHttpThreadPool {

    private final ServerSocket serverSocket;
    // 返回可用处理器的Java虚拟机的数量：Runtime.getRuntime().availableProcessors()

    private final ExecutorService executorService =
            new ThreadPoolExecutor(5,
                    200,
                    0L,
                    TimeUnit.MILLISECONDS,
                    new LinkedBlockingQueue<>(1024),
                    new ThreadFactoryBuilder().setNameFormat("demo-pool-%d").build(),
                    new ThreadPoolExecutor.AbortPolicy()
                    );

    public SocketServerHttpThreadPool(int port) throws IOException {
        serverSocket = new ServerSocket(port);
    }

    public static void main(String[] args) throws IOException {
        SocketServerHttpThreadPool serverHttp = new SocketServerHttpThreadPool(8801);
        while(true) {
            serverHttp.run();
        }
    }

    private void run() {
        try {
            Socket accept = serverSocket.accept();
            executorService.execute(()-> handleClient(accept));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleClient(Socket socket){
        PrintWriter printWriter = null;
        try {
            printWriter = new PrintWriter(socket.getOutputStream(), true);
            printWriter.println("HTTP/1.1 200 OK");
            printWriter.println("Content-Type:text/html;charset=utf-8");
            String body = "hello nio1";
            printWriter.println("Content-Length:" + body.getBytes().length);
            printWriter.println();
            printWriter.write(body);
            printWriter.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}