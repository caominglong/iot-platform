package com.banmao.service;

import com.banmao.dataobjects.Message;
import com.banmao.dataobjects.User;
import com.banmao.utils.VerifyUtil;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author banmao
 * @version V1.0.0
 * @date 2022/8/21 上午10:25
 * @description
 */
public class QQServer {

    public ServerSocket serverSocket;
    public CommunicateWithClientService communicateWithClientService = new CommunicateWithClientService();
    public QQServer() {
        System.out.println("服务器在8081端口监听...");
        try {
            this.serverSocket = new ServerSocket(8081);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleClient() {
        try {
            Socket socket = serverSocket.accept();
            System.out.println("第二次验证！！！");
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            Message message = (Message) ois.readObject();
            switch (message.getMessageType()) {
                case VERIFY_USER:
                    System.out.println("服务端开始验证用户...");
                    User user = (User) message.getContent();
                    boolean loginFlag = VerifyUtil.verify(user);
                    if (loginFlag) {
                        communicateWithClientService.notifyLoginSuccess(socket);
                        // 启动一个线程与客户端通信
                        SocketThread socketThread = new SocketThread(socket, user.getUserId());
                        new Thread(socketThread).start();
                        SocketThreadManageService.addSocketThread(user.getUserId(), socketThread);
                    } else {
                        System.out.println("用户验证失败！");
                        communicateWithClientService.notifyLoginFailure(socket);
                        // socket.close();
                    }
                    break;
                default:
                    System.out.println("暂不处理...");
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        QQServer qqServer = new QQServer();
        // 获取客户端连接
        while (true) {
            qqServer.handleClient();
        }
    }
}