package com.baobao.micro.common.swagger;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author baobao
 * @create 2022-03-03 14:52
 * @description Swagger配置属性
 */
@Data
@ConfigurationProperties(prefix = "swagger")
public class SwaggerProperties {
    /**文档标题*/
    private String title;
    /**文档描述*/
    private String description;
    /**文档版本*/
    private String version;
}
