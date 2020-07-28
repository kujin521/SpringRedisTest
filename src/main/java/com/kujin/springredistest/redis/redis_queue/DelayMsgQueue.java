package com.kujin.springredistest.redis.redis_queue;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import redis.clients.jedis.Jedis;

import javax.xml.crypto.Data;
import java.util.Date;
import java.util.Set;
import java.util.UUID;

/**
 * 类：延迟消息队列
 * 编写人：kujin
 * 创建时间：2020/7/28
 * 修改时间：2020/7/28
 */
public class DelayMsgQueue {
    private Jedis jedis;
    private String queue;
    public DelayMsgQueue(Jedis jedis, String queue) {
        this.jedis = jedis;
        this.queue = queue;
    }

    /**
     * 消息入列
     * @param data 要发送的消息
     */
    public void queue(Object data){
        //构造一个消息
        Message message = new Message();
        message.setId(UUID.randomUUID().toString());
        message.setData(data);
        //序列化
        try {
            String json = new ObjectMapper().writeValueAsString(message);
            //消息发送,延迟5秒
            System.out.println("msg publish: "+new Date());
            jedis.zadd(queue,System.currentTimeMillis()+5000,json);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    /**
     * 消息发送
     */
    public void loop(){
        while (!Thread.interrupted()){
            //读取score 到当前时间戳的消息
            Set<String> zrange = jedis.zrangeByScore(queue, 0, System.currentTimeMillis(), 0, 1);
            //如果消息为空,则休息500毫秒
            if (zrange.isEmpty()){
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    break;
                }
                continue;
            }
            //如果读取到消息,则将消息读取出来
            String next = zrange.iterator().next();
            if (jedis.zrem(queue,next)>0){
                //消息取到,处理业务
                try {
                    Message message = new ObjectMapper().readValue(next, Message.class);
                    System.out.println("receive msg: "+new Date()+">>>>>"+message);
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
