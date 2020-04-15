package com.xmut.modules.sys.redis;


import com.xmut.common.utils.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 系统配置Redis
 */
@Component
public class SysConfigRedis {
    @Autowired
    private RedisUtils redisUtils;

}
