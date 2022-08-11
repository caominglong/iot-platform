package com.banmao.model;

/**
 *
 * 厂家是设备制造商或协议提供商
 *
 * @author banmao
 * @version V1.0.0
 * @date 2022/8/7 下午4:19
 * @description 厂家
 */
public class Manufacturer {

    private String id;
    private String name;
    private String email;
    private String mobile;
    private String addr;
    private String status;
    /**
     * 联系员
     */
    private String linkman;
//    /**
//     * 表示此厂商提供了哪些协议规则
//     */
//    private List<ProtocolRule> protocolRules;
//    /**
//     * 表示此厂商提供了哪些设备类型
//     */
//    private List<DeviceType> deviceTypes;
}