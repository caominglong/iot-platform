package com.banmao.service;


import com.banmao.dataobjects.FileMessage;
import com.banmao.dataobjects.Message;
import com.banmao.dataobjects.User;
import com.banmao.enums.MessageType;
import com.banmao.utils.ObjectUtil;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * @author banmao
 * @version V1.0.0
 * @date 2022/8/21 上午9:41
 * @description 与服务器通信的service
 */
public class CommunicateWithServerService {

    public Socket socket;

    public CommunicateWithServerService() {

    }

    public boolean verifyUser(String userId, String pwd) {
        try {
            socket = new Socket(InetAddress.getLocalHost().getHostAddress(), 8081);
        } catch (IOException e) {
            e.printStackTrace();
        }
        boolean loginFlag = false;
        try {
            // 发送登录信息
            ObjectOutputStream oos = ObjectUtil.getOos(socket);
            User user = new User(userId, pwd);
            Message message = new Message(
                    UUID.randomUUID().toString(),
                    user,
                    MessageType.VERIFY_USER,
                    userId,
                    null,
                    LocalDateTime.now()
            );
            oos.writeObject(message);
            // 接收认证结果
            ObjectInputStream ois = ObjectUtil.getOis(socket);
            Message message1 = (Message) ois.readObject();
            switch (message1.getMessageType()) {
                case LOGIN_SUCCESS:
                    loginFlag = true;
                    System.out.println("客户端接收到服务端的提示：登录成功！");
                    break;
                case LOGIN_FAILURE:
                    System.out.println("客户端接收到服务端的提示：登录失败！");
                    break;
                default:
                    System.out.println("不予处理...");
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return loginFlag;
    }

    /**
     * 获取用户在线列表
     */
    public void getOnlineUserList(String userId) {
        // 发送登录信息
        ObjectOutputStream oos = ObjectUtil.getOos(socket);
        Message message = new Message();
        message.setMessageType(MessageType.ONLINE_USER);
        message.setSender(userId);
        message.setId(UUID.randomUUID().toString());
        try {
            oos.writeObject(message);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 获取服务端返回信息
        ObjectInputStream ois = ObjectUtil.getOis(socket);
        try {
            Message message1 = (Message) ois.readObject();
            List<User> userList = (List<User>) message1.getContent();
            System.out.print("\n用户在线列表\n");
            for (int i = 0; i < userList.size(); i++) {
                System.out.print("\t\t" + i+1 + "、" + userList.get(i).getUserId() +"\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    /**
     * 群发消息
     * @param sender 发送人
     */
    public void sendMsgToAll(String sender){
        Message message = new Message();
        message.setMessageType(MessageType.SEND_MSG_TO_ALL);
        message.setContent("你们好呀！");
        message.setId(UUID.randomUUID().toString());
        message.setSendTime(LocalDateTime.now());
        message.setSender(sender);
        ObjectOutputStream oos = ObjectUtil.getOos(socket);
        try {
            oos.writeObject(message);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 本该存在获取是否发送成功，这里忽略
    }

    /**
     * 私聊消息
     */
    public void sendMsgToOne(String sender, String getter) {
        Message message = new Message();
        message.setMessageType(MessageType.SEND_MSG_TO_ONE);
        message.setContent(getter + "，你好呀！");
        message.setId(UUID.randomUUID().toString());
        message.setSendTime(LocalDateTime.now());
        message.setSender(sender);
        message.setGetter(getter);
        ObjectOutputStream oos = ObjectUtil.getOos(socket);
        try {
            oos.writeObject(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @param src 源文件
     * @param dest 目的文件
     * @param senderId 发送人
     * @param getterId 接收人
     */
    public void sendFileToOne(String src, String dest, String senderId, String getterId) {

        FileMessage fileMessage = new FileMessage();
        fileMessage.setMessageType(MessageType.SEND_FILE);
        fileMessage.setId(UUID.randomUUID().toString());
        fileMessage.setSrc(src);
        fileMessage.setDest(dest);
        ObjectOutputStream oos = ObjectUtil.getOos(socket);
        byte[] fileBytesFromDisk = getFileBytesFromDisk(src);
        fileMessage.setFileBytes(fileBytesFromDisk);
        fileMessage.setSender(senderId);
        fileMessage.setGetter(getterId);
        try {
            oos.writeObject(fileMessage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 从磁盘获取文件
     * @return
     */
    private byte[] getFileBytesFromDisk(String src) {
        try {
            BufferedInputStream bis = new BufferedInputStream(new FileInputStream(src));
            byte[] bytes = new byte[(int) new File(src).length()];
            // 读入文件
            bis.read(bytes);
            return bytes;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}