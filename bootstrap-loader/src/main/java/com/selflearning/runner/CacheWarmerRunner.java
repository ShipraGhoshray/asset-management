package com.selflearning.runner;

import com.selflearning.repository.PortfolioRepository;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class CacheWarmerRunner {//implements CommandLineRunner {

    /*private final PortfolioRepository repository;
    private final RedisTemplate<String, Object> redisTemplate;

    public CacheWarmerRunner(PortfolioRepository repository, RedisTemplate<String, Object> redisTemplate) {
        this.repository = repository;
        this.redisTemplate = redisTemplate;
    }*/

    /*@Override
    public void run(String... args) {
        var portfolios = repository.findAll();
        redisTemplate.opsForValue().set("portfolios", portfolios);
        System.out.println("✅ Loaded portfolios into Redis. Cache warmer exiting...");
    }*/
}