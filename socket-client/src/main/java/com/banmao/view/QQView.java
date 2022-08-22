package com.banmao.view;

import com.banmao.dataobjects.FileMessage;
import com.banmao.dataobjects.Message;
import com.banmao.dataobjects.User;
import com.banmao.service.CommunicateWithServerService;
import com.banmao.utils.ObjectUtil;
import com.banmao.utils.Utility;

import javax.rmi.CORBA.Util;
import java.io.*;
import java.net.Socket;

/**
 * @author banmao
 * @version V1.0.0
 * @date 2022/8/21 上午9:02
 * @description
 */
public class QQView {

    public CommunicateWithServerService communicateWithServerService = new CommunicateWithServerService();

    private boolean loop = true; // 控制是否显示菜单
    private String key = ""; // 接收用户等键盘输入

    private User user;

    // 显示主菜单
    private void mainMenu() {
        while (loop) {
            System.out.println("==============欢迎登陆网络通信系统==============");
            System.out.println("\t\t 1 登录系统");
            System.out.println("\t\t 9 退出系统");
            System.out.println("请输入你的选择：");
            key = Utility.readString(1);

            //根据用户的输入处理不同的逻辑
            switch (key) {
                case "1":
                    System.out.println("请输入用户号：");
                    String userId = Utility.readString(50);
                    System.out.println("请输入密 码：");
                    String pwd = Utility.readString(50);
                    // 去服务器验证密码
                    boolean loginFlag = communicateWithServerService.verifyUser(userId, pwd);
                    if (loginFlag) {
                        user = new User(userId, pwd);
                        // 起新线程来接收服务端推送的消息
                        receiveServerMsg();
                        System.out.println("=========欢迎（用户" + userId + " ）登录系统=========");
                        // 进入到二级菜单
                        while(loop) {
                            System.out.println("\n==========网络通信系统二级菜单（用户" + userId + "）=========");
                            System.out.println("\t\t 1 显示用户在线列表");
                            System.out.println("\t\t 2 群发消息");
                            System.out.println("\t\t 3 私聊消息");
                            System.out.println("\t\t 4 发送文件");
                            System.out.println("\t\t 9 退出系统");
                            System.out.println("请输入你的选择：");
                            key = Utility.readString(1);
                            switch (key) {
                                case "1":
                                    System.out.println("显示在线用户列表");
                                    communicateWithServerService.getOnlineUserList(userId);
                                    break;
                                case "2":
                                    System.out.println("群发消息");
                                    communicateWithServerService.sendMsgToAll(userId);
                                    break;
                                case "3":
                                    System.out.println("私聊消息");
                                    String sender = user.getUserId();
                                    String getter = "200";
                                    communicateWithServerService.sendMsgToOne(sender, getter);
                                    break;
                                case "4":
                                    System.out.println("发送文件");
                                    String src = "/Users/cao/Documents/小仓由菜4.jpg";
                                    String dest = "/Users/cao/Documents/111/小仓由菜4.jpg";
                                    String sender2 = user.getUserId();
                                    communicateWithServerService.sendFileToOne(src, dest, sender2, "200");
                                    break;
                                case "9":
                                    System.out.println("退出系统");
                                    loop = false;
                                    break;
                                default:
                                    loop = false;
                                    System.out.println("输入错误！请重新输入！");
                                    break;
                            }
                        }
                    } else {
                        System.out.println("登录服务器失败");
                    }
                    break;
                case "9":
                    System.out.println("退出系统");
                    loop = false;
                    break;
            }
        }
    }

    private void receiveServerMsg() {
        // 新起个线程来接收服务端消息（被动接收的）
        new Thread(() -> {
            while(true) {
                ObjectInputStream ois = ObjectUtil.getOis(communicateWithServerService.socket);
                try {
                    Message message = (Message) ois.readObject();
                    switch (message.getMessageType()) {
                        case SEND_MSG_TO_ALL:
                            System.out.println(message.getSender() + "给" + user.getUserId() + "发送了消息：" + message.getContent());
                            break;
                        case SEND_MSG_TO_ONE:
                            System.out.println(message.getSender() + "给" + user.getUserId() + "发送了消息：" + message.getContent());
                            break;
                        case SEND_FILE:
                            FileMessage message1 = (FileMessage) message;
                            System.out.println(message1.getSender() + "给" + user.getUserId() + "发送了文件：" + message1.getSrc());
                            saveFileToDisk(message1.getFileBytes(), message1.getDest());
                            break;
                        default:
                            System.out.println("不支持的类型");
                            break;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

    private void saveFileToDisk(byte[] bytes, String dest) {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(dest);
            try {
                fileOutputStream.write(bytes);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        QQView qqView = new QQView();
        qqView.mainMenu();
    }
}