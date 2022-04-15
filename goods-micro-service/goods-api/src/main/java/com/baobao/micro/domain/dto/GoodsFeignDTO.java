package com.baobao.micro.domain.dto;

import com.baobao.micro.enums.GoodsTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author baobao
 * @create 2022-04-08 11:57
 * @description 商品远程接口DTO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GoodsFeignDTO {
    private Long id;

    private String name;

    private GoodsTypeEnum type;

    private BigDecimal price;

    private Date productionDate;
}
