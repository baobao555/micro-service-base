package com.baobao.micro.domain.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * @author baobao
 * @create 2022-04-08 11:36
 * @description
 */
@Data
@ApiModel("订单提交TO")
public class OrderCommitTO {
    @ApiModelProperty(value = "商品id", required = true)
    @NotNull(message = "商品id不能为空")
    private Long goodsId;

    @ApiModelProperty(value = "购买数量", required = true)
    @NotNull(message = "购买数量不能为空")
    @Min(value = 1, message = "购买数量必须大于0")
    private Integer buyNum;
}
