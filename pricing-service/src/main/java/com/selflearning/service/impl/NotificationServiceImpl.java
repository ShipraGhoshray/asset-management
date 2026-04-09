package com.selflearning.service.impl;
import com.selflearning.service.NotificationService;
import org.springframework.stereotype.Service;

@Service
public class NotificationServiceImpl implements NotificationService {

    public void notifyPriceDrop(String message) {
        System.out.println("Price Drop Alert sent: " + message);
    }
}