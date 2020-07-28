package com.kujin.springredistest.jedis;


import com.kujin.springredistest.util.Redis;

/**
 * 类：使用连接池
 * 编写人：kujin
 * 创建时间：2020/7/23
 * 修改时间：2020/7/23
 */
public class JedisPoolTest {
    public static void main(String[] args) {
        Redis redis=new Redis();
        redis.execute(jedis1 -> {
            System.out.println(jedis1.ping());
        });
    }
}
