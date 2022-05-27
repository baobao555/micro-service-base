package com.baobao.micro.controller;

import com.baobao.micro.common.domain.Result;
import com.baobao.micro.file.aliyun.AliyunOssPolicy;
import com.baobao.micro.file.aliyun.AliyunOssService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;

/**
 * @author baobao
 * @create 2022-04-07 17:34
 * @description 阿里云对象存储
 */
@RestController
@RequestMapping("aliOss")
@Validated
@Api(tags = "阿里云对象存储服务接口")
@RequiredArgsConstructor
public class AliyunOssController {
    private final AliyunOssService aliyunOssService;

    @ApiOperation("获取文件上传预签名信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "dir", value = "上传到哪个子目录", paramType = "query")
    })
    @GetMapping("policy")
    public Result<AliyunOssPolicy> getPolicy(String dir) {
        return Result.success(aliyunOssService.getPolicy(dir));
    }

    @ApiOperation("获取文件访问固定url(需要将bucket权限设置为公共)")
    @ApiImplicitParams({@ApiImplicitParam(name = "path", value = "文件在bucket中的相对路径", dataType = "string", paramType = "query", required = true)})
    @GetMapping("fixedUrl")
    public Result<String> getFixedUrl(@NotBlank(message = "文件路径不能为空") String path) {
        return Result.success("操作成功", aliyunOssService.getFixAccessUrl(path));
    }

    @ApiOperation("获取文件访问临时url")
    @ApiImplicitParams({@ApiImplicitParam(name = "path", value = "文件在bucket中的相对路径", dataType = "string", paramType = "query", required = true)})
    @GetMapping("tempUrl")
    public Result<String> getTempUrl(@NotBlank(message = "文件路径不能为空") String path) {
        return Result.success("操作成功", aliyunOssService.getTempAccessUrl(path));
    }
}
