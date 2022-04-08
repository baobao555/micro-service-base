package com.baobao.micro.domain.query;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author baobao
 * @create 2022-04-08 17:02
 * @description
 */
@Data
@ApiModel("订单查询参数")
public class OrderQuery {
    @ApiModelProperty("订单号")
    private String orderNo;

    @ApiModelProperty("下单时间起始")
    private Date orderTimeBegin;

    @ApiModelProperty("下单时间结束")
    private Date orderTimeEnd;
}
