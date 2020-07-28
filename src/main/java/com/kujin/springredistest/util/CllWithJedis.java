package com.kujin.springredistest.util;

import redis.clients.jedis.Jedis;

/**
 * 类：约束Jedis创建
 * 编写人：kujin
 * 创建时间：2020/7/23
 * 修改时间：2020/7/23
 */
public interface CllWithJedis {
    void call(Jedis jedis);
}
