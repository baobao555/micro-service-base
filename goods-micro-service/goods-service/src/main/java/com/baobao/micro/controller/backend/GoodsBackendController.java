package com.baobao.micro.controller.backend;

import com.baobao.micro.common.domain.Result;
import com.baobao.micro.domain.entity.Goods;
import com.baobao.micro.domain.query.GoodsQuery;
import com.baobao.micro.domain.to.GoodsAddTO;
import com.baobao.micro.domain.to.GoodsUpdateTO;
import com.baobao.micro.domain.vo.backend.GoodsListVO;
import com.baobao.micro.service.GoodsService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;
import java.util.Set;

/**
 * @author baobao
 * @create 2022-03-25 16:09
 * @description
 */
@RestController
@RequestMapping("backend/goods")
@Validated
@Api(tags = "商品接口-后台")
public class GoodsBackendController {
    @Autowired
    private GoodsService goodsService;

    @PostMapping
    @ApiOperation("添加商品")
    public Result<Void> add(@RequestBody @Valid GoodsAddTO to) {
        goodsService.add(to);
        return Result.success();
    }

    @PutMapping("{id}")
    @ApiOperation("修改商品")
    public Result<Void> update(@PathVariable("id") @ApiParam(value = "商品id", required = true) Long id,
                               @RequestBody @Valid GoodsUpdateTO to) {
        goodsService.update(id, to);
        return Result.success();
    }

    @DeleteMapping("{ids}")
    @ApiOperation("删除商品")
    public Result<Void> delete(@PathVariable("ids") @ApiParam(value = "商品id数组", required = true) Set<Long> ids) {
        goodsService.removeByIds(ids);
        return Result.success();
    }

    @GetMapping("list")
    @ApiOperation("条件查询商品")
    public Result<List<GoodsListVO>> list(GoodsQuery query) {
        return Result.success(goodsService.list(query));
    }

    @GetMapping("listPage/{pageNum}/{pageSize}")
    public Result<Page<Goods>> listPage(@PathVariable("pageNum") @Min(value = 1, message = "页码必须大于0") @ApiParam(value = "页码", required = true) Integer pageNum,
                                        @PathVariable("pageSize") @Min(value = 1, message = "每页数据量必须大于0") @ApiParam(value = "每页数据量", required = true) Integer pageSize,
                                        GoodsQuery query) {
        return Result.success(goodsService.listPage(pageNum, pageSize, query));
    }
}
