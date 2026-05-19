package com.selflearning.messaging;
import com.selflearning.messaging.events.ReportCommand;
import com.selflearning.service.ReportService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ReportCommandListener {

    private final ReportService reportService;

    @KafkaListener(
            topics = "${kafka.topics.report-command}",
            groupId = "${spring.kafka.consumer.group-id}"
    )
    public void onReportCommand(ReportCommand command) {
        log.info("Received Order Created Report Command for orderId={}", command.orderId());
        reportService.report(command);
    }
}