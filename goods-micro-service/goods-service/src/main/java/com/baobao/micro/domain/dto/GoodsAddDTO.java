package com.baobao.micro.domain.dto;

import com.baobao.micro.enums.GoodsTypeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author baobao
 * @create 2022-03-25 16:15
 * @description
 */
@Data
@ApiModel("商品添加TO")
public class GoodsAddDTO {
    @ApiModelProperty(value = "商品名称", required = true)
    @NotBlank(message = "商品名称不能为空")
    private String name;

    @ApiModelProperty(value = "商品类型", required = true)
    @NotNull(message = "商品类型不能为空")
    private GoodsTypeEnum type;

    @ApiModelProperty(value = "商品价格", required = true)
    @NotNull(message = "商品价格不能为空")
    @Positive(message = "商品价格必须大于0")
    private BigDecimal price;

    @ApiModelProperty(value = "促销开始时间", required = true)
    @NotNull(message = "促销开始时间不能为空")
    @FutureOrPresent(message = "促销开始时间不能小于当前时间")
    private Date promotionStart;

    @ApiModelProperty(value = "促销结束时间", required = true)
    @NotNull(message = "促销结束时间不能为空")
    @FutureOrPresent(message = "促销结束时间不能小于当前时间")
    private Date promotionEnd;

    @ApiModelProperty(value = "生产日期", required = true)
    @NotNull(message = "生产日期不能为空")
    private Date productionDate;
}
