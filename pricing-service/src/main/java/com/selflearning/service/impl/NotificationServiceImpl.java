package com.selflearning.service.impl;
import com.selflearning.service.NotificationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class NotificationServiceImpl implements NotificationService {

    public void notifyPriceDrop(String message) {
        log.info("Price Drop Alert sent: " + message);
    }
}