package com.banmao.http_imitate;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author banmao
 * @version V1.0.0
 * @date 2022/8/17 下午9:16
 * @description
 */
public class SocketServerHttp {

    private final ServerSocket serverSocket;

    public SocketServerHttp(int port) throws IOException {
        serverSocket = new ServerSocket(port);
    }

    public static void main(String[] args) throws IOException {
        SocketServerHttp serverHttp = new SocketServerHttp(8801);
        while(true) {
            serverHttp.run();
        }
    }

    private void run() {
        try {
            Socket accept = serverSocket.accept();
            handleClient(accept);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleClient(Socket socket) throws IOException {
        PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true);
        printWriter.println("HTTP/1.1 200 OK");
        printWriter.println("Content-Type:text/html;charset=utf-8");
        String body = "hello nio1";
        printWriter.println("Content-Length:" + body.getBytes().length);
        printWriter.println();
        printWriter.write(body);
        printWriter.close();
        socket.close();
    }
}