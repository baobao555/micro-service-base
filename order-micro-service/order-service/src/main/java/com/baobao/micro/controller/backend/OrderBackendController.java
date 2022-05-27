package com.baobao.micro.controller.backend;

import com.baobao.micro.common.domain.PageParam;
import com.baobao.micro.common.domain.PageVO;
import com.baobao.micro.common.domain.Result;
import com.baobao.micro.domain.query.OrderQuery;
import com.baobao.micro.domain.vo.backend.OrderListVO;
import com.baobao.micro.service.backend.OrderBackendService;
import com.baobao.micro.service.base.OrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Min;

/**
 * @author baobao
 * @create 2022-04-08 11:34
 * @description
 */
@RestController
@RequestMapping("backend")
@Validated
@Api(tags = "订单接口-后台")
@RequiredArgsConstructor
public class OrderBackendController {
    private final OrderBackendService orderService;

    @GetMapping("page")
    @ApiOperation("分页条件查询订单")
    public Result<PageVO<OrderListVO>> page(PageParam pageParam, OrderQuery query) {
        return Result.success(orderService.page(pageParam, query));
    }
}
