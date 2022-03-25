package com.baobao.micro.controller.app;

import io.swagger.annotations.Api;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author baobao
 * @create 2022-03-25 16:11
 * @description
 */
@RestController
@RequestMapping("app/goods")
@Validated
@Api(tags = "商品接口-app")
public class GoodsAppController {
}
