package com.kujin.springredistest.main;

import com.kujin.springredistest.pojo.Role;
import javafx.scene.control.Alert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;

import static org.junit.Assert.assertEquals;

public class SpringRedis {

	//Redis模板帮助类
	private RedisTemplate redisTemplate;

	String appxml="applicationContext.xml";

	@Test
	public void spring_redis(){
		//使用 spring xml 配置文件初始化 ApplicationContext
		ApplicationContext context = new ClassPathXmlApplicationContext(appxml);
		//然后使用 ApplicationContext 获取 redisTemplate 的对象
		redisTemplate=context.getBean(RedisTemplate.class);
		//角色实体
		Role role = new Role();
		role.setId(1L);
		role.setRoleName("role_name_1");
		role.setNote("note_1");
		//redsi操作
		redisTemplate.opsForValue().set("role_1", role);
		Role role1 = (Role) redisTemplate.opsForValue().get("role_1");
		System.out.println(role1.getRoleName());
	}

	@Test
	public void testSessionCallback() {
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext(appxml);
		RedisTemplate redisTemplate = applicationContext.getBean(RedisTemplate.class);
		Role role = new Role();
		role.setId(1);
		role.setRoleName("role_name_1");
		role.setNote("role_note_1");
		//SessionCallback接口对redis连接进行了封装，可以把多个命令放入到同一个Redis连接中执行
		SessionCallback callBack = new SessionCallback<Role>() {
			@Override
			public Role execute(RedisOperations ops)  {
				ops.boundValueOps("role_1").set(role);
				return (Role) ops.boundValueOps("role_1").get();
			}
		};
		Role savedRole = (Role) redisTemplate.execute(callBack);
		System.out.println(savedRole.getId());
	}

	/**
	 * 使用Spring测试Redis字符串操作
	 */
	@Test
	public void spring_redis_string(){
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(appxml);
		RedisTemplate redisTemplate = context.getBean(RedisTemplate.class);
		redisTemplate.opsForValue().set("key1","value1");
		redisTemplate.opsForValue().set("key2","value2");
		String key1 = (String) redisTemplate.opsForValue().get("key1");
		assertEquals("value1", key1);
	}


}
