package com.baobao.micro.service.base.impl;

import com.baobao.micro.domain.entity.Goods;
import com.baobao.micro.mapper.GoodsMapper;
import com.baobao.micro.service.base.GoodsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author baobao
 * @create 2022-03-25 16:07
 * @description
 */
@Service
@RequiredArgsConstructor
public class GoodsServiceImpl extends ServiceImpl<GoodsMapper, Goods> implements GoodsService {

}
