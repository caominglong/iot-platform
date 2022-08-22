package com.banmao.dataobjects;

import com.banmao.enums.MessageType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author banmao
 * @version V1.0.0
 * @date 2022/8/21 上午7:02
 * @description 通信的消息对象
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FileMessage extends Message implements Serializable {

    private byte[] fileBytes;
    private String fileName;
    private int fileLen = 0;
    private String dest;
    private String src;

}