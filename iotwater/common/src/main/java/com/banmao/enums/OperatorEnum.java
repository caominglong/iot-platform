package com.banmao.enums;

public enum OperatorEnum {

    EQUAL("="),
    GREATER_THAN(">"),
    LESS_THAN("<"),
    GREATER_THAN_AND_EQUALS(">="),
    LESS_THAN_AND_EQUALS("<=");

    private String operator;
    OperatorEnum(String operator) {
        this.operator = operator;
    }
}
