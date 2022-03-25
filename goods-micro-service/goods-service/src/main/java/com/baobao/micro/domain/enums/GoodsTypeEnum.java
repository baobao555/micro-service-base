package com.baobao.micro.domain.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author baobao
 * @create 2022-03-25 15:28
 * @description 商品类型枚举
 */
@AllArgsConstructor
@Getter
public enum GoodsTypeEnum {
    /**手机*/
    PHONE(0, "手机"),
    /**电脑*/
    COMPUTER(1, "电脑"),
    /**食品*/
    FOOD(2, "食品");

    @EnumValue
    private final int code;
    private final String desc;
}
