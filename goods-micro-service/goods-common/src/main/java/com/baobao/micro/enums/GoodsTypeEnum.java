package com.baobao.micro.enums;

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

    /**
     * 判断对应描述的枚举是否存在
     * @param desc 描述
     * @return
     */
    public static boolean contains(String desc) {
        for (GoodsTypeEnum value : GoodsTypeEnum.values()) {
            if (value.getDesc().equals(desc)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 根据描述获取枚举实例
     * @param desc 描述
     * @return 实例
     */
    public static GoodsTypeEnum get(String desc) {
        for (GoodsTypeEnum value : GoodsTypeEnum.values()) {
            if (value.getDesc().equals(desc)) {
                return value;
            }
        }
        return null;
    }
}
