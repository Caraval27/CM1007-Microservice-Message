package journal.lab3_message.Core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class HealthProducer {
    private final KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    public HealthProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendGeneralPractitionerRequest(String id) {
        String topic = "request-general-practitioner-topic";
        kafkaTemplate.send(topic, id);
    }
}