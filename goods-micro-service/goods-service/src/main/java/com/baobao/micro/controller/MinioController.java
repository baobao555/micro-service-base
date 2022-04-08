package com.baobao.micro.controller;

import com.baobao.micro.common.domain.Result;
import com.baobao.micro.common.file.MinioService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;
import java.util.Map;

/**
 * @author baobao
 * @create 2022-04-07 17:00
 * @description minio文件服务
 */
@RestController
@RequestMapping("minio")
@Validated
@Api(tags = "minio文件服务接口")
public class MinioController {
    @Autowired
    private MinioService minioService;

    @ApiOperation("获取文件上传预签名信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "fileName", value = "原始文件名", dataType = "string", paramType = "query", required = true)})
    @GetMapping("preSignedInfo")
    public Result<Map<String, String>> getPresignedInfo(@NotBlank(message = "文件名不能为空") String fileName) {
        return Result.success(minioService.getUploadPresignedInfo("", fileName));
    }

    @ApiOperation("获取文件访问固定url(需要将bucket权限设置为公共)")
    @ApiImplicitParams({@ApiImplicitParam(name = "path", value = "文件在bucket中的相对路径", dataType = "string", paramType = "query", required = true)})
    @GetMapping("fixedUrl")
    public Result<String> getFixedUrl(@NotBlank(message = "文件路径不能为空") String path) {
        return Result.success("操作成功", minioService.getFixAccessUrl(path));
    }

    @ApiOperation("获取文件访问临时url")
    @ApiImplicitParams({@ApiImplicitParam(name = "path", value = "文件在bucket中的相对路径", dataType = "string", paramType = "query", required = true)})
    @GetMapping("tempUrl")
    public Result<String> getTempUrl(@NotBlank(message = "文件路径不能为空") String path) {
        return Result.success("操作成功", minioService.getTempAccessUrl(path));
    }
}
