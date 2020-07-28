package com.kujin.springredistest.jedis;

import redis.clients.jedis.Jedis;

/**
 * 类：在java中使用Redis
 * 编写人：kujin
 * 创建时间：2020/7/23
 * 修改时间：2020/7/23
 */
public class Java_Redis_Text {
    public static void main(String[] args) {
        Jedis jedis=new Jedis("192.168.2.168",6379);//连接redis
        jedis.auth("kujin");//输入密码

        int i=0;//记录操作次数
        try {
            long start = System.currentTimeMillis();//开始时间
            while (true){
                long end = System.currentTimeMillis();//结束时间
                if (end-start>=1000){//时间大于1秒，退出
                    break;
                }
                i++;
                jedis.set("test"+i,i+"");
            }
        }finally {//关闭连接
            jedis.close();
        }
        System.out.println("redis每秒操作 "+ i+ "次");

    }
}
