package dev.trendio_back.configuration;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

public class KafkaConfiguration {

    @Value("${topic.name}")
    private String topicName;

    @Bean
    public NewTopic newtopic() {
        return new NewTopic(topicName, 1, (short) 1);
    }
}
