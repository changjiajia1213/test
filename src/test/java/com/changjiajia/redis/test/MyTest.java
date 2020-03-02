package com.changjiajia.redis.test;

import java.util.HashMap;
import java.util.Random;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.changjiajia.redis.test.entity.User;
import com.cjj.utils.RandomUtil;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:redis.xml")
public class MyTest {
	@SuppressWarnings("rawtypes")
	@Autowired
	RedisTemplate redisTemplate;

	@SuppressWarnings("unchecked")
	@Test
	public void addUser() {
		long startTime = System.currentTimeMillis();
		HashMap<String, User> hashMap = new HashMap<String, User>();
		for (int i = 1; i <= 100000; i++) {
			User user = new User();
			user.setId(i);
			user.setName(RandomUtil.getRandomChineseString(3));
			Random random = new Random();
			int sexRdm = random.nextInt(2);
			if (sexRdm == 0) {
				user.setSex("男");
			} else if (sexRdm == 1) {
				user.setSex("女");
			}
			int[] phones = RandomUtil.getRandomNum(9);
			String phone = "";
			for (int j : phones) {
				phone += j;
			}
			user.setPhone("13" + phone);
			int emailRdm = random.nextInt(17) + 3;
			int emailRdmEnd = random.nextInt(7);
			if (emailRdmEnd == 0) {
				user.setEmail(RandomUtil.getRandomCNumberString(emailRdm) + "@qq.com");
			} else if (emailRdmEnd == 1) {
				user.setEmail(RandomUtil.getRandomCNumberString(emailRdm) + "@163.com");
			} else if (emailRdmEnd == 2) {
				user.setEmail(RandomUtil.getRandomCNumberString(emailRdm) + "@sian.com");
			} else if (emailRdmEnd == 3) {
				user.setEmail(RandomUtil.getRandomCNumberString(emailRdm) + "@gmail.com");
			} else if (emailRdmEnd == 4) {
				user.setEmail(RandomUtil.getRandomCNumberString(emailRdm) + "@sohu.com");
			} else if (emailRdmEnd == 5) {
				user.setEmail(RandomUtil.getRandomCNumberString(emailRdm) + "@hotmail.com");
			} else if (emailRdmEnd == 6) {
				user.setEmail(RandomUtil.getRandomCNumberString(emailRdm) + "@foxmail.com");
			}
			int yearRdm = random.nextInt(52) + 1949;
			int monthRdm = RandomUtil.getRandomNum(1, 12);
			int dayRdm = RandomUtil.getRandomNum(1, 30);
			user.setBirthday(yearRdm + "-" + monthRdm + "-" + dayRdm);
			// System.out.println(user);
			// redisTemplate.opsForValue().set("u" + i, user);
			hashMap.put("u" + i, user);
		}
		redisTemplate.opsForHash().putAll("myhash", hashMap);
		long endTime = System.currentTimeMillis();
		// System.out.println("序列化方式：JDK系列化方式");
		// System.out.println("保存数量：100000");
		// System.out.println("所耗时间(毫秒）：" + (endTime - startTime));
		// System.out.println("序列化方式：JSON系列化方式");
		// System.out.println("保存数量：100000");
		// System.out.println("所耗时间(毫秒）：" + (endTime - startTime));
		System.out.println("序列化方式：Hash类型");
		System.out.println("保存数量：100000");
		System.out.println("所耗时间(毫秒）：" + (endTime - startTime));
	}
}
