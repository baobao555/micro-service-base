package com.baobao.micro.controller.app;

import com.baobao.micro.common.domain.Result;
import com.baobao.micro.domain.to.OrderCommitTO;
import com.baobao.micro.service.OrderService;
import com.pig4cloud.plugin.idempotent.annotation.Idempotent;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @author baobao
 * @create 2022-04-08 11:33
 * @description
 */
@RestController
@RequestMapping("app")
@Validated
@Api(tags = "订单接口-app")
public class OrderAppController {
    @Autowired
    private OrderService orderService;

    @PostMapping
    @ApiOperation("提交订单")
    @Idempotent(expireTime = 5)
    public Result<Void> commit(@RequestBody @Valid OrderCommitTO to) {
        orderService.commit(to);
        return Result.success();
    }
}
