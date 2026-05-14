package com.selflearning.config;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "kafka")
public class KafkaTopicProperties {

    private Topics topics;
    private int partitions;
    private short replicationFactor;

    @Getter @Setter
    public static class Topics {
        //private String reportCommand;
        private String orderCreated;
        //private String tradeExecuted;
        private String reportEvents;
    }
}