package com.baobao.micro.service;

import com.baobao.micro.common.domain.PageVO;
import com.baobao.micro.domain.entity.Order;
import com.baobao.micro.domain.query.OrderQuery;
import com.baobao.micro.domain.to.OrderCommitTO;
import com.baobao.micro.domain.vo.backend.OrderListVO;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author baobao
 * @create 2022-04-08 11:25
 * @description
 */
public interface OrderService extends IService<Order> {
    /**
     * 提交订单
     * @param to
     */
    void commit(OrderCommitTO to);

    /**
     * 条件分页查询订单
     * @param pageNum 页码
     * @param pageSize 每页数据量
     * @param query 查询条件
     * @return 订单列表
     */
    PageVO<OrderListVO> listPage(Integer pageNum, Integer pageSize, OrderQuery query);
}
