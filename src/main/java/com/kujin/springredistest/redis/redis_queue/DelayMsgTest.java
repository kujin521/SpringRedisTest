package com.kujin.springredistest.redis.redis_queue;

import com.kujin.springredistest.jedis.util.CllWithJedis;
import com.kujin.springredistest.jedis.util.Redis;
import redis.clients.jedis.Jedis;

/**
 * 类：延迟消息测试
 * 编写人：kujin
 * 创建时间：2020/7/28
 * 修改时间：2020/7/28
 */
public class DelayMsgTest {
    public static void main(String[] args) {
        Redis redis = new Redis();
        redis.execute(jedis -> {
            DelayMsgQueue delayMsgQueue = new DelayMsgQueue(jedis, "kujin-delay-queue");
            //构造消息生产者
            Thread producer=new Thread(() -> {
                for (int i = 0; i < 5; i++) {
                    delayMsgQueue.queue("www.baidu.com>>>>>>"+i);
                }
            });
            //构造消息消费者
            Thread consumer=new Thread(() -> {
                for (int i = 0; i < 5; i++) {
                    delayMsgQueue.loop();
                }
            });
            //启动
            producer.start();
            consumer.start();
            //休息7秒,停止程序
            try {
                Thread.sleep(7000);
                consumer.interrupt();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }
}
