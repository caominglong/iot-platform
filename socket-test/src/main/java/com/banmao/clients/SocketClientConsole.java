package com.banmao.clients;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * Company: 杭州领图信息科技有限公司
 *
 * @description:
 * @author: banmao
 * @date: 2022/8/16 13:47
 */
public class SocketClientConsole {

    public static void main(String[] args) throws IOException {
        // 端口写死，用8084
        Socket socket = new Socket(InetAddress.getLocalHost().getHostAddress(), 8084);
        startChat(socket);
    }

    private static void startChat(Socket socket) throws IOException {
        BufferedWriter bufferedWriter = null;
        BufferedReader bufferedReader = null;
        for(;;) {
            // send message to server
            String message = writeFromConsole();
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            bufferedWriter.write(message);
            bufferedWriter.write("\n");
            bufferedWriter.flush();
            if ("exit".equals(message)) {
                break;
            }
            // receive message from server
            bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String messageFromServer = bufferedReader.readLine();
            System.out.println("server：" + messageFromServer);
            if ("exit".equals(messageFromServer)) {
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
        System.out.print("client：");
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }


}
