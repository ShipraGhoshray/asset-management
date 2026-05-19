package com.selflearning.service;

import com.selflearning.messaging.events.ReportCommand;

public interface ReportService {
    public void report(ReportCommand request);
}