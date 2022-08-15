package com.banmao.clients;

import java.io.*;
import java.net.Socket;

/**
 * @author banmao
 * @version V1.0.0
 * @date 2022/8/14 下午4:09
 * @description 单socket客户端，畅聊版
 */
public class SingleSocketClientPersist {

    public static void main(String[] args) throws IOException {
        if (args.length != 2) {
            System.out.println("Port and RemoteAddress parameters are required");
            return;
        }
        String remoteAddress = args[0];
        int port = Integer.parseInt(args[1]);
        connectAndCommunication(remoteAddress, port);
    }

    private static void connectAndCommunication(String remoteAddress, int port) throws IOException {
        Socket socket = new Socket(remoteAddress, port);
        boolean startFlag = true;
        int number = 1;
        BufferedWriter bufferedWriter = null;
        BufferedReader bufferedReader = null;
        while(true) {
            if (startFlag) {
                bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                bufferedWriter.write("让我们开始数数");
                bufferedWriter.write("\n");
                bufferedWriter.flush();
                // socket.shutdownOutput();
                // 读取server返回的信息
                bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String sss = "";
                if ((sss = bufferedReader.readLine()) != null) {
                    System.out.println("Server说：" + sss);
                }
                startFlag = false;
            } else {
                number++;
                // bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                bufferedWriter.write(number+"");
                bufferedWriter.write("\n");
                bufferedWriter.flush();
                // 输出标志，表示"这句话讲完了"
                // socket.shutdownOutput();
                // 读取server返回的信息
                // bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String s1 = "";
                if ((s1 = bufferedReader.readLine()) != null) {
                    System.out.println(s1);
                }
            }
            if (number > 10) {
                break;
            }

        }
         // 关闭流
        bufferedWriter.close();
        bufferedReader.close();
        socket.close();

    }

    /**
     * 通过控制台输入进行socket通信
     * @param remoteAddress
     * @param port
     * @throws IOException
     */
    private static void connectAndCommunicationByConsole(String remoteAddress, int port) throws IOException {
        Socket socket = new Socket(remoteAddress, port);
        while(true) {
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            bufferedWriter.write("我是张三");
            // 输出标志，表示"这句话讲完了"
            socket.shutdownOutput();
            bufferedWriter.close();
        }

    }
}