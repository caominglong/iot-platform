package com.banmao.model;

import com.banmao.enums.AggWayEnum;
import lombok.Data;

import java.util.List;

/**
 * @author banmao
 * @version V1.0.0
 * @date 2022/8/7 下午2:33
 * @description 数据项类型
 */
@Data
public class DataItem {

    /**
     * 唯一id
     */
    private String id;

    /**
     * 名称
     */
    private String name;

    /**
     * 类型，
     */
    private String type;

    /**
     * 单位
     */
    private int unit;

    /**
     * 最大值
     */
    private int maxValue;

    /**
     * 最小值
     */
    private int minValue;

    /**
     * 精度
     */
    private int precision;

    /**
     * 聚合方式
     */
    private AggWayEnum aggWay;

    /**
     * 聚合方式
     */
    private List<Condition> conditions;

    /**
     * 文本类型（用于显示）【文字、标签、隐藏】
     */
    private int textType;

    /**
     * 状态（可用/不可用）
     */
    private int status;

    /**
     * 备注
     */
    private int remork;


}