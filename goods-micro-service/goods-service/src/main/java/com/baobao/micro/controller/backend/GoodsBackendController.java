package com.baobao.micro.controller.backend;

import cn.hutool.core.collection.CollUtil;
import com.baobao.micro.common.domain.PageParam;
import com.baobao.micro.common.domain.PageVO;
import com.baobao.micro.common.domain.Result;
import com.baobao.micro.domain.dto.GoodsAddDTO;
import com.baobao.micro.domain.dto.GoodsUpdateDTO;
import com.baobao.micro.domain.query.GoodsQuery;
import com.baobao.micro.domain.vo.backend.GoodsExcelVO;
import com.baobao.micro.domain.vo.backend.GoodsListVO;
import com.baobao.micro.service.backend.GoodsBackendService;
import com.pig4cloud.plugin.excel.annotation.RequestExcel;
import com.pig4cloud.plugin.excel.annotation.ResponseExcel;
import com.pig4cloud.plugin.excel.vo.ErrorMessage;
import com.pig4cloud.plugin.idempotent.annotation.Idempotent;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;

/**
 * @author baobao
 * @create 2022-03-25 16:09
 * @description
 */
@RestController
@RequestMapping("backend")
@Validated
@Api(tags = "商品接口-后台")
@RequiredArgsConstructor
public class GoodsBackendController {
    private final GoodsBackendService goodsService;

    @PostMapping
    @ApiOperation("添加商品")
    @Idempotent(expireTime = 5)
    public Result<Void> add(@RequestBody @Valid GoodsAddDTO to) {
        goodsService.add(to);
        return Result.success();
    }

    @PutMapping("{id}")
    @ApiOperation("修改商品")
    public Result<Void> update(@PathVariable("id") @ApiParam(value = "商品id", required = true) Long id,
                               @RequestBody @Valid GoodsUpdateDTO to) {
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

    @ResponseExcel(name = "商品信息")
    @GetMapping("export")
    @ApiOperation("导出商品")
    public List<GoodsExcelVO> export(GoodsQuery query) {
        return goodsService.export(query);
    }

    @PostMapping("import")
    @ApiOperation("导入商品")
    @Idempotent(expireTime = 5)
    public Result<List<ErrorMessage>> importGoods(@RequestExcel List<GoodsExcelVO> excelVOList, BindingResult bindingResult) {
        List<ErrorMessage> basicErrorMessageList = (List<ErrorMessage>) bindingResult.getTarget();
        if (CollUtil.isNotEmpty(excelVOList)) {
            List<ErrorMessage> customErrorMessageList = goodsService.importGoods(excelVOList);
            basicErrorMessageList.addAll(customErrorMessageList);
        }
        return Result.success(basicErrorMessageList);
    }

    @GetMapping("page")
    @ApiOperation("分页条件查询商品")
    public Result<PageVO<GoodsListVO>> page(PageParam pageParam, GoodsQuery query) {
        return Result.success(goodsService.page(pageParam, query));
    }
}
