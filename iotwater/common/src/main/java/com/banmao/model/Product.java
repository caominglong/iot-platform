package com.banmao.model;

import com.banmao.base.BaseEntity;

/**
 * @author banmao
 * @version V1.0.0
 * @date 2022/8/9 下午9:35
 * @description
 */
public class Product extends BaseEntity {

    private String id;
    private String key;
    private String name;
    private String categoryId;
    /**
     * 节点类型，有直连设备、网关设备两种
     */
    private String nodeType;
    /**
     * 数据格式（协议）
     */
    private String protocolId;

    /**
     * 状态：1、开发中 2、已上线 3、撤销上线
     *
     */
    private String status;

    private String desc;
}