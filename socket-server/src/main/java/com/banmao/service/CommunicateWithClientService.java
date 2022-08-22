package com.banmao.service;

import com.banmao.dataobjects.FileMessage;
import com.banmao.dataobjects.Message;
import com.banmao.dataobjects.User;
import com.banmao.enums.MessageType;
import com.banmao.utils.ObjectUtil;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @author banmao
 * @version V1.0.0
 * @date 2022/8/21 上午8:56
 * @description 与客户端通讯类
 */
public class CommunicateWithClientService {

    /**
     * 通知客户端登录失败
     */
    public void notifyLoginFailure(Socket socket) {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            Message message = new Message();
            message.setId(UUID.randomUUID().toString());
            message.setMessageType(MessageType.LOGIN_FAILURE);
            message.setSendTime(LocalDateTime.now());
            oos.writeObject(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 通知客户端登录成功
     */
    public void notifyLoginSuccess(Socket socket) {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            Message message = new Message();
            message.setId(UUID.randomUUID().toString());
            message.setMessageType(MessageType.LOGIN_SUCCESS);
            message.setSendTime(LocalDateTime.now());
            oos.writeObject(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取在线用户列表
     */
    public void getOnlineUserList(String userId) {
        SocketThread socketThread = SocketThreadManageService.getSocketThread(userId);
        List<User> allUsers= SocketThreadManageService.getAllUser();
        Message message = new Message();
        message.setId(UUID.randomUUID().toString());
        message.setContent(allUsers);
        message.setMessageType(MessageType.ONLINE_USER);
        ObjectOutputStream oos = ObjectUtil.getOos(socketThread.socket);
        try {
            oos.writeObject(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 群发消息
     */
    public void sendMsgToAll(Message message) {
        System.out.println(message.getSender() + "对所有人说：" + message.getContent());
        Map<String, SocketThread> socketThreadMap = SocketThreadManageService.socketThreadMap;
        for (String userId : socketThreadMap.keySet()) {
            if (!userId.equals(message.getSender())) {
                // 忽略发送者本人
                SocketThread socketThread = SocketThreadManageService.getSocketThread(userId);
                ObjectOutputStream oos = ObjectUtil.getOos(socketThread.socket);
                Message message1 = new Message();
                message1.setMessageType(MessageType.SEND_MSG_TO_ALL);
                message1.setContent(message.getContent());
                message1.setId(UUID.randomUUID().toString());
                message1.setSender(message.getSender());
                try {
                    oos.writeObject(message1);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 私聊消息
     */
    public void sendMsgToOne(Message message) {
        System.out.println(message.getSender() + "对" + message.getGetter() + "说：" + message.getContent());

        SocketThread socketThread = SocketThreadManageService.getSocketThread(message.getGetter());
        ObjectOutputStream oos = ObjectUtil.getOos(socketThread.socket);
        Message message1 = new Message();
        message1.setMessageType(MessageType.SEND_MSG_TO_ONE);
        message1.setContent(message.getContent());
        message1.setId(UUID.randomUUID().toString());
        message1.setSender(message.getSender());
        try {
            oos.writeObject(message1);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void sendFileToOne(FileMessage message1) {
        System.out.println(message1.getSender() + "对" + message1.getGetter() + "发送了文件：" + message1.getSrc());
        SocketThread socketThread = SocketThreadManageService.getSocketThread(message1.getSender());
        ObjectOutputStream oos = ObjectUtil.getOos(socketThread.socket);
        try {
            oos.writeObject(message1);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}