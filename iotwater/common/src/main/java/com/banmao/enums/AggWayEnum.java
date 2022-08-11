package com.banmao.enums;

public enum AggWayEnum {

    MAX("最大值"),
    MIN("最小值"),
    AVG("平均值"),
    FIRST("首值"),
    LAST("末值"),
    DATA_SUM("数据和"),
    DATA_DIFF("数据差"),
    DATA_Amount("数据量");

    private String type;
    AggWayEnum(String type) {
        this.type = type;
    }
}
