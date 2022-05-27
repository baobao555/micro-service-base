package com.baobao.micro.service.backend.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.BooleanUtil;
import cn.hutool.core.util.StrUtil;
import com.baobao.micro.common.domain.PageParam;
import com.baobao.micro.common.domain.PageVO;
import com.baobao.micro.domain.entity.Order;
import com.baobao.micro.domain.query.OrderQuery;
import com.baobao.micro.domain.vo.backend.OrderListVO;
import com.baobao.micro.service.backend.OrderBackendService;
import com.baobao.micro.service.base.OrderService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author baobao
 * @create 2022-05-27 9:18
 * @description
 */
@Service
@RequiredArgsConstructor
public class OrderBackendServiceImpl implements OrderBackendService {
    private final OrderService orderService;

    @Override
    public PageVO<OrderListVO> page(PageParam pageParam, OrderQuery query) {
        LambdaQueryWrapper<Order> wrapper = this.buildQueryWrapper(query);
        int pageNum = pageParam.getPageNum() == null ? 1 : pageParam.getPageNum();
        int pageSize = pageParam.getPageSize() == null ? 10 : pageParam.getPageSize();
        Page<Order> page = new Page<>(pageNum, pageSize);
        if (StrUtil.isNotBlank(pageParam.getOrderColumn())) {
            String columnName = StrUtil.toUnderlineCase(pageParam.getOrderColumn());
            page.addOrder(BooleanUtil.isTrue(pageParam.getIsDesc()) ? OrderItem.desc(columnName) :
                    OrderItem.asc(columnName));
        }
        orderService.page(page, wrapper);
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
