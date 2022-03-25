package com.baobao.micro.domain.query;

import com.baobao.micro.domain.enums.GoodsTypeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author baobao
 * @create 2022-03-25 17:30
 * @description
 */
@Data
@ApiModel("商品查询参数")
public class GoodsQuery {
    @ApiModelProperty("商品名称")
    private String name;

    @ApiModelProperty("商品类型")
    private GoodsTypeEnum type;

    @ApiModelProperty("生产日期起始")
    private Date productionDateBegin;

    @ApiModelProperty("生产日期结束")
    private Date productionDateEnd;
}
