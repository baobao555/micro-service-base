package com.baobao.micro.domain.to;

import com.baobao.micro.domain.enums.GoodsTypeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author baobao
 * @create 2022-03-25 16:26
 * @description
 */
@Data
@ApiModel("商品修改TO")
public class GoodsUpdateTO {
    @ApiModelProperty(value = "商品名称")
    private String name;

    @ApiModelProperty(value = "商品类型")
    private GoodsTypeEnum type;

    @ApiModelProperty(value = "生产日期")
    private Date productionDate;
}
