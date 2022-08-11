package com.banmao.model;

import lombok.Data;

/**
 * @author banmao
 * @version V1.0.0
 * @date 2022/8/7 下午4:39
 * @description
 * 品类：当创建一个产品（型号）时，可以从某个品类下面创建，
 * 这样产品就继承了品类的基础的物模型
 */
@Data
public class Category {
    private String id;
    private String name;
    private String key;
    private String superId;
}