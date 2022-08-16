package com.banmao.server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

/**
 * Company: 杭州领图信息科技有限公司
 *
 * @description:
 * @author: banmao
 * @date: 2022/8/16 13:46
 */
public class SocketServerConsole {

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(8084);
        Socket socket = serverSocket.accept();
        BufferedReader bufferedReader = null;
        BufferedWriter bufferedWriter = null;
        for(;;) {
            bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String message = bufferedReader.readLine();
            if ("exit".equals(message)) {
                System.out.println("client：" + message);
                break;
            }
            System.out.println("client：" + message);
            // send message to client
            String messageFromConsole = writeFromConsole();
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            bufferedWriter.write(messageFromConsole);
            bufferedWriter.write("\n");
            bufferedWriter.flush();
            if ("exit".equals(messageFromConsole)) {
                break;
            }
        }
        closeStream(bufferedWriter, bufferedReader, socket);
    }

    private static void closeStream(BufferedWriter bufferedWriter, BufferedReader bufferedReader, Socket socket) throws IOException {
        if (bufferedWriter != null) {
            bufferedWriter.close();
        }
        if (bufferedReader != null) {
            bufferedReader.close();
        }
        if (socket != null) {
            socket.close();
        }
    }

    private static String writeFromConsole() {
        System.out.print("server：");
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }
}
