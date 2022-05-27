package com.baobao.micro.common.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author baobao
 * @create 2022-05-26 17:28
 * @description 查询基础参数
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("分页参数")
public class PageParam {
    @ApiModelProperty("页码")
    private Integer pageNum;

    @ApiModelProperty("每页数据量")
    private Integer pageSize;

    /**排序列*/
    @ApiModelProperty("排序字段")
    private String orderColumn;

    /**是否降序*/
    @ApiModelProperty("是否降序")
    private Boolean isDesc;
}
