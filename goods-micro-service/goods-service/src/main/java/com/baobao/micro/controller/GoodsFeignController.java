package com.baobao.micro.controller;

import com.baobao.micro.domain.dto.GoodsDTO;
import com.baobao.micro.feign.GoodsFeignApi;
import com.baobao.micro.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author baobao
 * @create 2022-04-08 14:16
 * @description 商品远程调用接口
 */
@RestController
@RequestMapping("feign")
public class GoodsFeignController implements GoodsFeignApi {
    @Autowired
    private GoodsService goodsService;

    @Override
    @GetMapping("{goodsId}")
    public GoodsDTO get(@PathVariable("goodsId") Long goodsId) {
        return goodsService.getDTO(goodsId);
    }
}
