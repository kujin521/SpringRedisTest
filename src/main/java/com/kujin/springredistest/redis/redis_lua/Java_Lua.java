package com.kujin.springredistest.redis.redis_lua;

import com.kujin.springredistest.util.Utils;
import com.kujin.springredistest.util.Redis;
import com.kujin.springredistest.pojo.Role;
import org.junit.Test;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.params.SetParams;

import java.util.ArrayList;
import java.util.Collections;
import java.util.UUID;

/**
 * 类：在java中使用Lua脚本
 * 编写人：kujin
 * 创建时间：2020/7/25
 * 修改时间：2020/7/25
 */
public class Java_Lua {
    @Test
    public void java_redis(){
        Jedis jedis = Utils.getJedis();
        //执行简单的脚本
        String helloJava = (String) jedis.eval("return 'hello java'");
        System.out.println(helloJava);

        //执行带参数的脚本
        jedis.eval("redis.call('set',KEYS[1],ARGV[1])",1,"lua-key","lua-value");
        String luaKey = jedis.get("lua-key");
        System.out.println(luaKey);

        //缓存脚本，返回sha1签名标识
        String sha1 = jedis.scriptLoad("redis.call('set',KEYS[1],ARGV[1])");
        //通过标识执行脚本
        jedis.evalsha(sha1,1,new String[]{"sha-key","sha-val"});
        //获取执行脚本后的数据
        String shaVal = jedis.get("sha-key");
        System.out.println(shaVal);

        //关闭连接
        jedis.close();
    }

    /**
     * 使用RedisScript接口对象通过Lua脚本操作对象
     */
    @Test
    public void redisScript_Lua(){
        //1.定义默认脚本封装类
        DefaultRedisScript<Role> redisScript = new DefaultRedisScript<>();
        //2. 设置脚本
        redisScript.setScriptText("redis.call('set',KEYS[1],ARGV[1]) return redis.call('get',KEYS[1])");
        //3. 定义操作key的列表
        ArrayList<String> keyList = new ArrayList<>();
        keyList.add("role1");
        //4. 需要序列化保存和读取的对象
        Role role = new Role();
        role.setId(1L);
        role.setRoleName("role_name_1");
        role.setNote("note_1");
        //5. 获取标识字符串
        String sha1 = redisScript.getSha1();
        System.out.println(sha1);
        //6. 设置返回结果类型，如果没有这句话，返回结果为空
        redisScript.setResultType(Role.class);
        //7. 定义序列化器
        JdkSerializationRedisSerializer serializer = new JdkSerializationRedisSerializer();
        //8. 执行脚本
        RedisTemplate jedisTemlate = Utils.getJedisTemlate();
        //第一个是redisScript接口对象，第二个是参数序列化器
        //第三个是结果序列化器，第四个是Redis的key列表，最后是参数列表
        Role obj = (Role) jedisTemlate.execute(redisScript, serializer, serializer, keyList, role);
        System.out.println(obj.toString());
    }

    /**
     * 执行Lua文件
     */
    @Test
    public void excuLuaFile(){
        Redis redis = new Redis();
        for (int i = 0; i < 2; i++) {
            redis.execute(jedis -> {
                //1.获取一个随机字符串
                String value = UUID.randomUUID().toString();
                //2. 获取锁
                String k1 = jedis.set("k1", value, new SetParams().nx().ex(5));
                //3. 判断是否成功拿到锁
                if (k1!=null&&"ok".equalsIgnoreCase(k1)){
                    //4具体的业务操作
                    jedis.set("site","www.baidu.com");
                    System.out.println(jedis.get("site"));
                    //5 释放锁
                    jedis.evalsha("b8059ba43af6ffe8bed3db65bac35d452f8115d8", Collections.singletonList("k1"), Collections.singletonList(value));
                }else {
                    System.out.println("没拿到锁");
                }
            });
        }
    }
}
