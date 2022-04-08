package com.baobao.micro.feign;

import com.baobao.micro.domain.dto.GoodsDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author baobao
 * @create 2022-04-08 11:51
 * @description
 */
@FeignClient("GOODS-SERVICE")
public interface GoodsFeignApi {
    /**
     * 获取商品信息
     * @param goodsId 商品id
     * @return 商品信息dto
     */
    @GetMapping("feign/{goodsId}")
    GoodsDTO get(@PathVariable("goodsId") Long goodsId);
}
