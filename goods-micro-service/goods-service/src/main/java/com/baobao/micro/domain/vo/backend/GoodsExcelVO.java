package com.baobao.micro.domain.vo.backend;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import java.util.Date;

/**
 * @author baobao
 * @create 2022-04-05 10:03
 * @description
 */
@Data
@ApiModel("商品ExcelVO")
public class GoodsExcelVO {
    @ApiModelProperty("商品名称")
    @ExcelProperty("商品名称")
    @NotBlank(message = "商品名称不能为空")
    private String name;

    @ApiModelProperty("商品类型")
    @ExcelProperty("商品类型")
    @NotNull(message = "商品类型不能为空")
    private String type;

    @DateTimeFormat("yyyy-MM-dd HH:mm:ss")
    @ExcelProperty("生产日期")
    @ApiModelProperty("生产日期")
    @NotNull(message = "生产日期不能为空")
    @PastOrPresent(message = "生产日期不能大于当前时间")
    private Date productionDate;
}
