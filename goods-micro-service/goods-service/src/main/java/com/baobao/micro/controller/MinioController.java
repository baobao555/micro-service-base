package com.baobao.micro.controller;

import com.baobao.micro.common.domain.Result;
import com.baobao.micro.common.exception.BusinessException;
import com.baobao.micro.file.minio.MinioService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
@RequiredArgsConstructor
public class MinioController {
    private final MinioService minioService;

    @ApiOperation("获取文件上传预签名信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "fileName", value = "原始文件名", paramType = "query", required = true),
            @ApiImplicitParam(name = "dir", value = "上传到哪个子目录", paramType = "query")
    })
    @GetMapping("preSignedInfo")
    public Result<Map<String, String>> getPresignedInfo(String dir, @NotBlank(message = "文件名不能为空") String fileName) {
        try {
            return Result.success(minioService.getUploadPresignedInfo(dir, fileName));
        } catch (Exception e) {
            log.error("获取文件上传预签名信息失败", e);
            throw new BusinessException("获取文件上传预签名信息失败");
        }
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
        try {
            return Result.success("操作成功", minioService.getTempAccessUrl(path));
        } catch (Exception e) {
            log.error("获取文件访问临时url失败", e);
            throw new BusinessException("获取文件访问临时url失败");
        }
    }
}
