package com.eh.restapi;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.embedded.RedisServer;
import redis.embedded.RedisServerBuilder;

public class EmbeddedRedisConfiguration {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	private RedisServer redisServer;
	// 메모리 부족상태에서 redis가 뜨지 않음. 옵션처리
	private String maxmemory;

	@PostConstruct
	public void startRedis() {
		redisServer = new RedisServerBuilder().port(6379).setting("maxmemory " + maxmemory).build();

		try {
			logger.info("Embedded Redis Start");
			redisServer.start();
		} catch (RuntimeException e) {
			logger.info("Embedded Redis is already running.");
			logger.error("Embedded Redis Start Error=[{}]", e);
		}
	}

	@PreDestroy
	public void stopRedis() {
		logger.info("Embedded Redis Stop");
		redisServer.stop(); // Redis 종료
	}

	public String getMaxmemory() {
		return maxmemory;
	}

	public void setMaxmemory(String maxmemory) {
		this.maxmemory = maxmemory;
	}

}
