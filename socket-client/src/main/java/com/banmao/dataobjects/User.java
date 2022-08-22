package com.banmao.dataobjects;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author banmao
 * @version V1.0.0
 * @date 2022/8/21 上午7:00
 * @description 通信的用户对象
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable {

    private static final long serialVersionUID = -6682752950868071927L;

    private String userId;
    private String password;
}