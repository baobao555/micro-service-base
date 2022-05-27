package com.baobao.micro.controller.feign;

import com.baobao.micro.domain.dto.GoodsFeignDTO;
import com.baobao.micro.feign.GoodsFeignApi;
import com.baobao.micro.service.feign.GoodsFeignService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author baobao
 * @create 2022-04-08 14:16
 * @description 商品远程调用接口
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("feign")
public class GoodsFeignController implements GoodsFeignApi {
    private final GoodsFeignService goodsService;

    @Override
    @GetMapping("{goodsId}")
    public GoodsFeignDTO get(@PathVariable("goodsId") Long goodsId) {
        return goodsService.getDTO(goodsId);
    }
}
