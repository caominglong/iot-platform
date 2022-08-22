package com.banmao.service;

import com.banmao.dataobjects.FileMessage;
import com.banmao.dataobjects.Message;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author banmao
 * @version V1.0.0
 * @date 2022/8/21 上午10:11
 * @description
 */
public class SocketThread implements Runnable{

    private CommunicateWithClientService communicateWithClientService = new CommunicateWithClientService();

    public Socket socket;
    public String userId;

    public SocketThread(Socket socket, String userId) {
        this.socket = socket;
        this.userId = userId;
    }

    public void run() {
        System.out.println("服务器与客户端保持通信，读取数据...");
        while (true) {
            try {
                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                Object object = ois.readObject();
                System.out.println(object.getClass());
                Message message = (Message) object;
//                if (object.getClass().isAssignableFrom(Message.class)) {
//                    message = (Message) object;
//                } else if (object.getClass().isAssignableFrom(FileMessage.class))) {
//                    message = (FileMessage) object;
//                }
                switch (message.getMessageType()) {
                    case ONLINE_USER:
                        System.out.println("用户在线列表");
                        communicateWithClientService.getOnlineUserList(userId);
                        break;
                    case SEND_MSG_TO_ALL:
                        System.out.println("群发消息");
                        communicateWithClientService.sendMsgToAll(message);
                        break;
                    case SEND_MSG_TO_ONE:
                        System.out.println("私聊消息");
                        communicateWithClientService.sendMsgToOne(message);
                        break;
                    case SEND_FILE:
                        System.out.println("发送文件");
                        FileMessage message1 = (FileMessage) message;
                        communicateWithClientService.sendFileToOne(message1);
                        break;
                    case EXIT_SYSTEM:
                        System.out.println("退出系统");
                        break;
                    default:
                        System.out.println("不支持的消息类型...");
                        break;
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}