package com.baobao.micro.controller.backend;

import com.baobao.micro.common.domain.PageVO;
import com.baobao.micro.common.domain.Result;
import com.baobao.micro.domain.query.OrderQuery;
import com.baobao.micro.domain.vo.backend.OrderListVO;
import com.baobao.micro.service.OrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
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
public class OrderBackendController {
    @Autowired
    private OrderService orderService;

    @GetMapping("listPage/{pageNum}/{pageSize}")
    @ApiOperation("分页条件查询订单")
    public Result<PageVO<OrderListVO>> listPage(@PathVariable("pageNum") @Min(value = 1, message = "页码必须大于0") @ApiParam(value = "页码", required = true) Integer pageNum,
                                                @PathVariable("pageSize") @Min(value = 1, message = "每页数据量必须大于0") @ApiParam(value = "每页数据量", required = true) Integer pageSize,
                                                OrderQuery query) {
        return Result.success(orderService.listPage(pageNum, pageSize, query));
    }
}
