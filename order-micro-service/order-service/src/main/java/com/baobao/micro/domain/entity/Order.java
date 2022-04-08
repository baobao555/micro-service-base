package com.baobao.micro.domain.entity;

import com.baobao.micro.common.domain.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.util.Date;

/**
 * @author baobao
 * @create 2022-04-08 11:21
 * @description
 */
@Data
@EqualsAndHashCode(callSuper=true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("`order`")
public class Order extends BaseEntity {
    @TableField("order_no")
    private String orderNo;

    @TableField("goods_id")
    private Long goodsId;

    @TableField("goods_name")
    private String goodsName;

    @TableField("buy_num")
    private Integer buyNum;

    @TableField("order_time")
    private Date orderTime;
}
