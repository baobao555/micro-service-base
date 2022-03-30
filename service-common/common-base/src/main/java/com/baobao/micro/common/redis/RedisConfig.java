package com.baobao.micro.common.redis;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * @author baobao
 * @create 2021-09-13 10:29
 * @description Redis自定义配置类
 */
@Configuration
@EnableCaching
@ConditionalOnClass(RedisAutoConfiguration.class)
public class RedisConfig {
    /**
     * 定制RedisTemplate，指定key序列化为String，value序列化为json
     * @param factory
     * @return RedisTemplate
     */
    @Bean
    @ConditionalOnMissingClass("net.dreamlu.mica.redis.config.MicaRedisCacheAutoConfiguration")
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(factory);
        // key的序列化器为string
        StringRedisSerializer keySer = new StringRedisSerializer();
        // value的序列化器为通用的json
        GenericJackson2JsonRedisSerializer valueSer = new GenericJackson2JsonRedisSerializer();
        // key序列化方式
        template.setKeySerializer(keySer);
        // value序列化
        template.setValueSerializer(valueSer);
        // 单独设置hash类型的序列化器
        template.setHashKeySerializer(keySer);
        template.setHashValueSerializer(valueSer);
        return template;
    }

    /**
     * 定制自己的CacheManager，将key序列化为字符串，value序列化为json
     * @param factory
     * @return CacheManager
     */
    @Bean
    @ConditionalOnMissingClass("net.dreamlu.mica.redis.config.MicaRedisCacheAutoConfiguration")
    public CacheManager cacheManager(RedisConnectionFactory factory) {
        // key的序列化器为string
        StringRedisSerializer keySer = new StringRedisSerializer();
        // value的序列化器为通用的json
        GenericJackson2JsonRedisSerializer valueSer = new GenericJackson2JsonRedisSerializer();
        // 配置序列化（解决乱码的问题）,过期时间600秒
        RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig()
                //.entryTtl(Duration.ofSeconds(600))
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(keySer))
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(valueSer))
                .disableCachingNullValues();
        return RedisCacheManager.builder(factory)
                .cacheDefaults(config)
                .build();
    }
}
