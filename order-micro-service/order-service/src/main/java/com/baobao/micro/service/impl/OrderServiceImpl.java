package com.baobao.micro.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import com.baobao.micro.common.domain.PageVO;
import com.baobao.micro.domain.dto.GoodsDTO;
import com.baobao.micro.domain.entity.Order;
import com.baobao.micro.domain.query.OrderQuery;
import com.baobao.micro.domain.to.OrderCommitTO;
import com.baobao.micro.domain.vo.backend.OrderListVO;
import com.baobao.micro.feign.GoodsFeignApi;
import com.baobao.micro.mapper.OrderMapper;
import com.baobao.micro.service.OrderService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author baobao
 * @create 2022-04-08 11:30
 * @description
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {
    @Autowired
    private GoodsFeignApi goodsFeignApi;

    @Override
    public void commit(OrderCommitTO to) {
        // 生成订单号
        String orderNo = RandomUtil.randomNumbers(10);
        // 远程接口查询商品信息
        GoodsDTO goodsDTO = goodsFeignApi.get(to.getGoodsId());
        // 保存订单信息
        Order order = BeanUtil.copyProperties(to, Order.class);
        order.setOrderNo(orderNo);
        order.setGoodsName(goodsDTO.getGoodsName());
        order.setOrderTime(new Date());
        this.save(order);
    }

    @Override
    public PageVO<OrderListVO> listPage(Integer pageNum, Integer pageSize, OrderQuery query) {
        LambdaQueryWrapper<Order> wrapper = this.buildQueryWrapper(query);
        Page<Order> page = new Page<>(pageNum, pageSize);
        this.page(page, wrapper);
        PageVO<OrderListVO> pageVO = BeanUtil.copyProperties(page, PageVO.class, "records");
        pageVO.setRecords(BeanUtil.copyToList(page.getRecords(), OrderListVO.class));
        return pageVO;
    }


    private LambdaQueryWrapper<Order> buildQueryWrapper(OrderQuery query) {
        LambdaQueryWrapper<Order> wrapper = Wrappers.lambdaQuery();
        if (query != null) {
            wrapper.eq(StrUtil.isNotBlank(query.getOrderNo()), Order::getOrderNo, query.getOrderNo());
            wrapper.ge(query.getOrderTimeBegin() != null, Order::getOrderTime, query.getOrderTimeBegin());
            wrapper.le(query.getOrderTimeEnd() != null, Order::getOrderTime, query.getOrderTimeEnd());
        }
        return wrapper;
    }
}
