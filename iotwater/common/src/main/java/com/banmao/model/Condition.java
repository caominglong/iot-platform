package com.banmao.model;

import com.banmao.enums.OperatorEnum;
import lombok.Data;

/**
 * @author banmao
 * @version V1.0.0
 * @date 2022/8/7 下午3:07
 * @description
 */
@Data
public class Condition {

    private String id;
    private String name;
    /**
     * 条件操作符
     */
    private OperatorEnum operatorEnum;
    private String operationValue;
}