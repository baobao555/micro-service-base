package com.baobao.micro.service.feign.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baobao.micro.domain.dto.GoodsFeignDTO;
import com.baobao.micro.domain.entity.Goods;
import com.baobao.micro.service.base.GoodsService;
import com.baobao.micro.service.feign.GoodsFeignService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author baobao
 * @create 2022-05-26 17:12
 * @description
 */
@Service
@RequiredArgsConstructor
public class GoodsFeignServiceImpl implements GoodsFeignService {
    private final GoodsService goodsService;

    @Override
    public GoodsFeignDTO getDTO(Long goodsId) {
        Goods goods = goodsService.getById(goodsId);
        return BeanUtil.copyProperties(goods, GoodsFeignDTO.class);
    }
}
