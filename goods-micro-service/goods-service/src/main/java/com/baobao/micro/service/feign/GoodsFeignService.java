package com.baobao.micro.service.feign;

import com.baobao.micro.domain.dto.GoodsFeignDTO;

/**
 * @author baobao
 * @create 2022-05-26 17:12
 * @description
 */
public interface GoodsFeignService {
    /**
     * 获取商品信息
     * @param goodsId 商品id
     * @return
     */
    GoodsFeignDTO getDTO(Long goodsId);
}
