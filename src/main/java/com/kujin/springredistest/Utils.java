package com.kujin.springredistest;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.redis.core.RedisTemplate;
import redis.clients.jedis.Jedis;

/**
 * 类：
 * 编写人：kujin
 * 创建时间：2020/7/25
 * 修改时间：2020/7/25
 */
public class Utils {
    static String appxml="applicationContext.xml";
    public static Jedis getJedis(){
        ClassPathXmlApplicationContext appContent = new ClassPathXmlApplicationContext(appxml);
        RedisTemplate redisTemplate = appContent.getBean(RedisTemplate.class);
        Jedis jedis = (Jedis) redisTemplate.getConnectionFactory().getConnection().getNativeConnection();
        return jedis;
    }
    public static RedisTemplate getJedisTemlate(){
        ClassPathXmlApplicationContext appContent = new ClassPathXmlApplicationContext(appxml);
        return appContent.getBean(RedisTemplate.class);
    }

}
