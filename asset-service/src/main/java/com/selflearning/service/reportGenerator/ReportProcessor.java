package com.selflearning.service.reportGenerator;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;

import java.util.concurrent.CompletableFuture;

@Slf4j
public class ReportProcessor {

    @Async
    public static CompletableFuture<String> nettingReport(){
        try {
            System.out.println("Generating Netting Report...");
            Thread.sleep(3000); // simulate heavy work
            return CompletableFuture.completedFuture("Netting Report ready");
        } catch (InterruptedException e) {
            // Restore interrupt flag so the executor knows this task was interrupted
            Thread.currentThread().interrupt();
            return CompletableFuture.completedFuture("Report generation interrupted");
        }
    }
}