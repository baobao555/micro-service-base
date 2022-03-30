package com.baobao.micro.domain.entity;

import com.baobao.micro.common.domain.CreateUpdateUserBaseEntity;
import com.baobao.micro.domain.enums.GoodsTypeEnum;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.util.Date;

/**
 * @author baobao
 * @create 2022-03-25 14:59
 * @description 商品实体类
 */
@Data
@EqualsAndHashCode(callSuper=true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("goods")
public class Goods extends CreateUpdateUserBaseEntity {
    @TableField("name")
    private String name;

    @TableField("type")
    private GoodsTypeEnum type;

    @TableField("production_date")
    private Date productionDate;
}
