package com.banmao.dataobjects;

import com.banmao.enums.MessageType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author banmao
 * @version V1.0.0
 * @date 2022/8/21 上午7:02
 * @description 通信的消息对象
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Message implements Serializable {

    private String id;
    private Object content;
    private MessageType messageType;
    private String sender;
    private String getter;
    private LocalDateTime sendTime;


}