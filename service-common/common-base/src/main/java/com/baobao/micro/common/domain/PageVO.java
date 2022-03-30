package com.baobao.micro.common.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author baobao
 * @create 2022-03-29 11:17
 * @description 分页结果VO
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("分页结果VO")
public class PageVO<T> {
    @ApiModelProperty("分页数据")
    private List<T> records;

    @ApiModelProperty("总记录数")
    private Integer total;

    @ApiModelProperty("每页记录数")
    private Integer size;

    @ApiModelProperty("当前页码")
    private Integer current;
}
