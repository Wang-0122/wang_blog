package com.wang.wangblog.component.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class RedisService {

	private Logger log = LoggerFactory.getLogger(RedisService.class);

	public final static String REDIS_RELOAD_TIME = "redis_reload_time";

	@Autowired
	private StringRedisTemplate stringRedisTemplate;

	@Autowired
	private LettuceConnectionFactory connectionFactory;

	@Async
	public void sendMessage(String message) {
		set(REDIS_RELOAD_TIME, String.valueOf(System.currentTimeMillis()));
		stringRedisTemplate.convertAndSend("loadRoute", message);
	}

	@Async
	public void updateCache(Class<?> cls) {
		set(REDIS_RELOAD_TIME, String.valueOf(System.currentTimeMillis()));
		stringRedisTemplate.convertAndSend("updateCache", cls.getSimpleName());
	}

	public void set(String key, String value) {
		try {
			stringRedisTemplate.opsForValue().set(key, value);
		} catch (Exception e) {
			log.error("Send command to redis error " + e.getMessage(), e);
			try {
				// valid the connection, if the connection is invalid, it will re-create a new connection
				connectionFactory.validateConnection();
				// retry once
				stringRedisTemplate.opsForValue().set(key, value);
			} catch (Exception e1) {
				log.error("Valid Connection Error " + e1.getMessage(), e1);
			}
		}
	}

	public void set(String key, String value, int seconds) {

		try {
			stringRedisTemplate.opsForValue().set(key, value, seconds, TimeUnit.SECONDS);
		} catch (Exception e) {
			log.error("Send command to redis error " + e.getMessage(), e);
			try {
				// valid the connection, if the connection is invalid, it will re-create a new connection
				connectionFactory.validateConnection();
				// retry once
				stringRedisTemplate.opsForValue().set(key, value, seconds, TimeUnit.SECONDS);
			} catch (Exception e1) {
				log.error("Valid Connection Error " + e1.getMessage(), e1);
			}
		}

	}

	public void set(String key, String value, Long timeout, TimeUnit unit) {

		try {
			stringRedisTemplate.opsForValue().set(key, value, timeout, unit);
		} catch (Exception e) {
			log.error("Send command to redis error " + e.getMessage(), e);
			try {
				// valid the connection, if the connection is invalid, it will re-create a new connection
				connectionFactory.validateConnection();
				// retry once
				stringRedisTemplate.opsForValue().set(key, value, timeout, unit);
			} catch (Exception e1) {
				log.error("Valid Connection Error " + e1.getMessage(), e1);
			}
		}

	}

	public Long increment(String key, long value) {
		try {
			return stringRedisTemplate.opsForValue().increment(key, value);
		} catch (Exception e) {
			log.error("Send command to redis error " + e.getMessage(), e);
			try {
				// valid the connection, if the connection is invalid, it will re-create a new connection
				connectionFactory.validateConnection();
				// retry once
				return stringRedisTemplate.opsForValue().increment(key, value);
			} catch (Exception e1) {
				log.error("Valid Connection Error " + e1.getMessage(), e1);
			}
		}
		return 0L;
	}

	public void expire(String key, Long timeout, TimeUnit unit) {
		try {
			stringRedisTemplate.expire(key, timeout, unit);
		} catch (Exception e) {
			log.error("Send command to redis error " + e.getMessage(), e);
			try {
				// valid the connection, if the connection is invalid, it will re-create a new connection
				connectionFactory.validateConnection();
				// retry once
				stringRedisTemplate.expire(key, timeout, unit);
			} catch (Exception e1) {
				log.error("Valid Connection Error " + e1.getMessage(), e1);
			}
		}

	}

	public void remove(String key) {

		try {
			stringRedisTemplate.delete(key);
		} catch (Exception e) {
			log.error("Send command to redis error " + e.getMessage(), e);
			try {
				// valid the connection, if the connection is invalid, it will re-create a new connection
				connectionFactory.validateConnection();
				// retry once
				stringRedisTemplate.delete(key);
			} catch (Exception e1) {
				log.error("Valid Connection Error " + e1.getMessage(), e1);
			}
		}

	}

	public String get(String key) {

		try {
			return stringRedisTemplate.opsForValue().get(key);
		} catch (Exception e) {
			log.error("Send command to redis error " + e.getMessage(), e);
			try {
				// valid the connection, if the connection is invalid, it will re-create a new connection
				connectionFactory.validateConnection();
				// retry once
				return stringRedisTemplate.opsForValue().get(key);
			} catch (Exception e1) {
				log.error("Valid Connection Error " + e1.getMessage(), e1);
			}
		}
		return null;

	}

	public String ping() {
		return stringRedisTemplate.getConnectionFactory().getConnection().ping();
	}

	public boolean isValid(String key) {

		try {
			return stringRedisTemplate.getExpire(key, TimeUnit.SECONDS) > 0;
		} catch (Exception e) {
			log.error("Send command to redis error " + e.getMessage(), e);
			try {
				// valid the connection, if the connection is invalid, it will re-create a new connection
				connectionFactory.validateConnection();
				// retry once
				return stringRedisTemplate.getExpire(key, TimeUnit.SECONDS) > 0;
			} catch (Exception e1) {
				log.error("Valid Connection Error " + e1.getMessage(), e1);
			}
		}
		return false;
	}

	public Long getExpire(String key, TimeUnit unit) {

		try {
			return stringRedisTemplate.getExpire(key, unit);
		} catch (Exception e) {
			log.error("Send command to redis error " + e.getMessage(), e);
			try {
				// valid the connection, if the connection is invalid, it will re-create a new connection
				connectionFactory.validateConnection();
				// retry once
				return stringRedisTemplate.getExpire(key, unit);
			} catch (Exception e1) {
				log.error("Valid Connection Error " + e1.getMessage(), e1);
			}
		}
		return -1L;
	}

}