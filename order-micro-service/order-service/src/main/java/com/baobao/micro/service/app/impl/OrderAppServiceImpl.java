package com.baobao.micro.service.app.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.RandomUtil;
import com.baobao.micro.domain.dto.GoodsFeignDTO;
import com.baobao.micro.domain.dto.OrderCommitDTO;
import com.baobao.micro.domain.entity.Order;
import com.baobao.micro.feign.GoodsFeignApi;
import com.baobao.micro.service.app.OrderAppService;
import com.baobao.micro.service.base.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author baobao
 * @create 2022-05-27 9:18
 * @description
 */
@Service
@RequiredArgsConstructor
public class OrderAppServiceImpl implements OrderAppService {
    private final GoodsFeignApi goodsFeignApi;
    private final OrderService orderService;

    @Override
    public void commit(OrderCommitDTO dto) {
        // 生成订单号
        String orderNo = RandomUtil.randomNumbers(10);
        // 远程接口查询商品信息
        GoodsFeignDTO goodsDTO = goodsFeignApi.get(dto.getGoodsId());
        // 保存订单信息
        Order order = BeanUtil.copyProperties(dto, Order.class);
        order.setOrderNo(orderNo);
        order.setGoodsName(goodsDTO.getName());
        order.setOrderTime(new Date());
        orderService.save(order);
    }
}
