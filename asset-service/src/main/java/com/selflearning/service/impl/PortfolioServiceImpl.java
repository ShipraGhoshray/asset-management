package com.selflearning.service.impl;

import com.selflearning.model.PortfolioReadModel;
import com.selflearning.repository.PortfolioRepository;
import com.selflearning.service.PortfolioService;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Map;
import java.util.List;

@Slf4j
@Service
public class PortfolioServiceImpl implements PortfolioService {

    private final CacheManager cacheManager;
    private final PortfolioRepository portfolioRepository;
    public PortfolioServiceImpl(CacheManager cacheManager, PortfolioRepository portfolioRepository) {
        this.cacheManager = cacheManager;
        this.portfolioRepository = portfolioRepository;
    }

    @PostConstruct
    public void preloadCache() {
        List<PortfolioReadModel> portfolios = portfolioRepository.findAll();
        Cache cache = cacheManager.getCache("portfolios");
        if (cache != null) {
            portfolios.forEach(p -> cache.put(p.getId(), p)); // put directly into cache
        }
        log.info("Portfolio data cached at startup (single DB hit).");
    }

    public PortfolioReadModel getPortfolioFromCache(Long id) {
        Cache cache = cacheManager.getCache("portfolios");
        if (cache != null) {
            return cache.get(id, PortfolioReadModel.class); // returns cached Portfolio or null if not present
        }
        return null;
    }

    @Override
    public List<PortfolioReadModel> getPortfolios(){
        List<PortfolioReadModel> portfolioList = new ArrayList<>();
        Cache cache = cacheManager.getCache("portfolios");
        if (cache instanceof ConcurrentMapCache concurrentMapCache) {
            Map<Object, Object> nativeCache = concurrentMapCache.getNativeCache();
            for (Object value : nativeCache.values()) {
                portfolioList.add((PortfolioReadModel) value);
            }
        }
        return portfolioList;
    }

    @Override
    public PortfolioReadModel getPortfolioById(long id){
        return getPortfolioFromCache(id);
    }
}