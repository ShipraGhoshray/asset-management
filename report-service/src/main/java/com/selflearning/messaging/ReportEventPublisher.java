package com.selflearning.messaging;
import com.selflearning.config.KafkaTopicProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ReportEventPublisher {

    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final KafkaTopicProperties topicProps;

    public void publish(Object event) {
        kafkaTemplate.send(topicProps.getTopics().getReportEvents(), event);
        log.info("Published event {} to topic {}", event.getClass().getSimpleName(), topicProps.getTopics().getReportEvents());
        log.info("Publishing event object: {}", event);
    }
}