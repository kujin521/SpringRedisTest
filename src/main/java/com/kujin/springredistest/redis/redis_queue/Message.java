package com.kujin.springredistest.redis.redis_queue;

/**
 * 类：消息实体
 * 编写人：kujin
 * 创建时间：2020/7/28
 * 修改时间：2020/7/28
 */
public class Message {
    private String id;
    private Object data;

    @Override
    public String toString() {
        return "Message{" +
                "id='" + id + '\'' +
                ", data=" + data +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
