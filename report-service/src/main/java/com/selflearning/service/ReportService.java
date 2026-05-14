package com.selflearning.service;

import com.selflearning.dto.ReportRequest;
import com.selflearning.messaging.events.OrderCreatedEvent;

public interface ReportService {
    void report(OrderCreatedEvent event);
    public void reportCat(ReportRequest request);
}