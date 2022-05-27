package com.baobao.micro.service.base.impl;

import com.baobao.micro.domain.entity.Order;
import com.baobao.micro.mapper.OrderMapper;
import com.baobao.micro.service.base.OrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author baobao
 * @create 2022-04-08 11:30
 * @description
 */
@Service
@RequiredArgsConstructor
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {
}
