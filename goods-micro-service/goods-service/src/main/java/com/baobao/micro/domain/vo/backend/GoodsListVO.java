package com.baobao.micro.domain.vo.backend;

import com.baobao.micro.enums.GoodsTypeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
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

    @ApiModelProperty("商品价格")
    private BigDecimal price;

    @ApiModelProperty("促销开始时间")
    private Date promotionStart;

    @ApiModelProperty("促销结束时间")
    private Date promotionEnd;

    @ApiModelProperty("生产日期")
    private Date productionDate;
}
