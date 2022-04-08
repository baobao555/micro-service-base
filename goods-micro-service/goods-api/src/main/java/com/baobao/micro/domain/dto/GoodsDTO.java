package com.baobao.micro.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author baobao
 * @create 2022-04-08 11:57
 * @description
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GoodsDTO {
    private Long goodsId;

    private String goodsName;
}
