package com.baobao.micro.domain.vo.backend;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author baobao
 * @create 2022-04-08 17:05
 * @description
 */
@Data
@ApiModel("订单列表VO")
public class OrderListVO {
    @ApiModelProperty("订单id")
    private Long id;

    @ApiModelProperty("订单号")
    private String orderNo;

    @ApiModelProperty("商品名称")
    private String goodsName;

    @ApiModelProperty("购买数量")
    private Integer buyNum;

    @ApiModelProperty("下单时间")
    private Date orderTime;
}
