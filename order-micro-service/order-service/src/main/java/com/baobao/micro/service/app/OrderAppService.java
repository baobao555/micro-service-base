package com.baobao.micro.service.app;

import com.baobao.micro.domain.dto.OrderCommitDTO;

/**
 * @author baobao
 * @create 2022-05-27 9:17
 * @description
 */
public interface OrderAppService {
    /**
     * 提交订单
     * @param dto
     */
    void commit(OrderCommitDTO dto);
}
