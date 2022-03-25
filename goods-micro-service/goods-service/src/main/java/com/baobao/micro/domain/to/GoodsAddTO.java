package com.baobao.micro.domain.to;

import com.baobao.micro.domain.enums.GoodsTypeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @author baobao
 * @create 2022-03-25 16:15
 * @description
 */
@Data
@ApiModel("商品添加TO")
public class GoodsAddTO {
    @ApiModelProperty(value = "商品名称", required = true)
    @NotBlank(message = "商品名称不能为空")
    private String name;

    @ApiModelProperty(value = "商品类型", required = true)
    @NotNull(message = "商品类型不能为空")
    private GoodsTypeEnum type;

    @ApiModelProperty(value = "生产日期", required = true)
    @NotNull(message = "生产日期不能为空")
    private Date productionDate;
}
