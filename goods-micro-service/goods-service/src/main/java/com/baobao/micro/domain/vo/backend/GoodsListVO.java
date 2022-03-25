package com.baobao.micro.domain.vo.backend;

import com.baobao.micro.domain.enums.GoodsTypeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author baobao
 * @create 2022-03-25 17:12
 * @description
 */
@Data
@ApiModel("商品列表VO")
public class GoodsListVO {
    @ApiModelProperty("商品id")
    private Long id;

    @ApiModelProperty("商品名称")
    private String name;

    @ApiModelProperty("商品类型")
    private GoodsTypeEnum type;

    @ApiModelProperty("生产日期")
    private Date productionDate;
}
