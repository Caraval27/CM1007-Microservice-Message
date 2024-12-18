package journal.lab3_message.Core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class HealthService {
    private static final String REQUEST_TOPIC = "request-general-practitioner-topic";
    private static final String RESPONSE_TOPIC = "response-general-practitioner-topic";

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    private String generalPractitioner;

    public String sendGeneralPractitionerRequest(String senderId) {
        if (senderId == null || senderId.trim().isEmpty()) {
            throw new IllegalArgumentException("Request message must not be null or empty");
        }

        kafkaTemplate.send(REQUEST_TOPIC, senderId);

        synchronized (this) {
            try {
                this.wait(10000);
            } catch (InterruptedException e) {
                throw new RuntimeException("Interrupted while waiting for response");
            }
        }

        return generalPractitioner;
    }

    @KafkaListener(topics = RESPONSE_TOPIC, groupId = "message-service-group")
    public void listenToGeneralPractitionerResponse(String generalPractitioner) {
        this.generalPractitioner = generalPractitioner;
        synchronized (this) {
            this.notify();
        }
    }
}