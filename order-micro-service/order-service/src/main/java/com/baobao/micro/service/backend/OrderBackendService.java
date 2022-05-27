package com.baobao.micro.service.backend;

import com.baobao.micro.common.domain.PageParam;
import com.baobao.micro.common.domain.PageVO;
import com.baobao.micro.domain.query.OrderQuery;
import com.baobao.micro.domain.vo.backend.OrderListVO;

/**
 * @author baobao
 * @create 2022-05-27 9:17
 * @description
 */
public interface OrderBackendService {
    /**
     * 分页条件查询
     * @param pageParam 分页参数
     * @param query 条件
     * @return
     */
    PageVO<OrderListVO> page(PageParam pageParam, OrderQuery query);
}
