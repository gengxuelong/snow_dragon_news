package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * @Auther: GengXuelong
 * @Date: 2022/7/31 16:06
 * @Description:
 */
@Configuration
public class MyRedisConfig {
    /**
     * @Auther: GengXuelong
     * <p> 函数功能描述如下:
     * @Description:
     *     将一个redisTemplete 放入spring容器
     */
    @Bean
    public RedisTemplate<String,Object> redisTemplate(RedisConnectionFactory redisConnectionFactory){
        RedisTemplate<String ,Object> redisTemplate = new RedisTemplate<>();
        //key序列化
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        //value 序列化
        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        //hash key序列化
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        //hash value序列化
        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        //注入链接工厂
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        return redisTemplate;
    }
}
