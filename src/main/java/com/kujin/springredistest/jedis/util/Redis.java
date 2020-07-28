package com.kujin.springredistest.jedis.util;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * 类：优化redis编写
 * 编写人：kujin
 * 创建时间：2020/7/23
 * 修改时间：2020/7/23
 */
public class Redis {
    //定义redis连接池
    private JedisPool pool;
    public Redis() {
        //配置连接参数
        GenericObjectPoolConfig config=new GenericObjectPoolConfig();
        config.setMaxIdle(50);//最大空闲数
        config.setMaxTotal(100);//最大连接数
        config.setTestOnBorrow(true);//在空闲时检查有效性
        pool=new JedisPool(config,"192.168.2.168",6379,20000,"kujin");
    }
    public void execute(CllWithJedis cllWithJedis){
        //在try里获取单个连接
        try (Jedis jedis=pool.getResource()){
            cllWithJedis.call(jedis);
        }
    }
}
