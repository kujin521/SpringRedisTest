package com.kujin.springredistest.lettuce;

import io.lettuce.core.RedisClient;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisStringCommands;

/**
 * 类：Lettuce测试
 * 编写人：kujin
 * 创建时间：2020/7/23
 * 修改时间：2020/7/23
 */
public class LettuceTest {
    static RedisClient client = RedisClient.create("redis://kujin@192.168.2.168");//获取redis实例
    public static void main(String[] args) {
        baseUsage();//基本用法
    }

    private static void baseUsage() {
        StatefulRedisConnection<String, String> connection = client.connect();
        RedisStringCommands sync = connection.sync();//同步对象
        sync.set("ku","jin");//设置值
        String ku = (String) sync.get("ku");//获取值
        System.out.println(ku);
        connection.close();
    }
}
