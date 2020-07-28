package com.kujin.springredistest.distrbute_lock;

import com.kujin.springredistest.util.Redis;
import org.junit.Test;
import redis.clients.jedis.params.SetParams;

/**
 * 类：分布式锁学习
 * 编写人：kujin
 * 创建时间：2020/7/24
 * 修改时间：2020/7/24
 */
public class DistributedRedis {

    @Test
    public void t1(){
        Redis redis = new Redis();
        redis.execute(jedis -> {
            /*
             Long setnx = jedis.setnx("k1", "v1");
            jedis.expire("k1",5);//设置5秒的过期时间，防止执行过程中异常导致锁无法释放的问题
             */
            //两个指令合成一个
            String set = jedis.set("k1", "v1", new SetParams().ex(5));
            if ("OK".equalsIgnoreCase(set)){
                //没人占位
                jedis.set("name", "kujin");
                System.out.println(jedis.get("name"));
                jedis.del("k1");//删除k1，释放位置
            }else {
                //有人占位,停止
            }
        });
    }

}
