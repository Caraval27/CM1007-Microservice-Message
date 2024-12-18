package journal.lab3_message.Core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class HealthService {
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    private String generalPractitioner;
    private String name;

    public String sendGeneralPractitionerRequest(String senderId) {
        if (senderId == null || senderId.trim().isEmpty()) {
            throw new IllegalArgumentException("Request message must not be null or empty");
        }

        kafkaTemplate.send("request-general-practitioner-topic", senderId);

        synchronized (this) {
            try {
                this.wait(10000);
            } catch (InterruptedException e) {
                throw new RuntimeException("Interrupted while waiting for response");
            }
        }

        return generalPractitioner;
    }

    @KafkaListener(topics = "response-general-practitioner-topic", groupId = "message-service-group")
    public void listenToGeneralPractitionerResponse(String generalPractitioner) {
        this.generalPractitioner = generalPractitioner;
        synchronized (this) {
            this.notify();
        }
    }

    public String sendNameRequest(String id) {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("Request message must not be null or empty");
        }

        kafkaTemplate.send("request-name-topic", id);

        synchronized (this) {
            try {
                this.wait(10000);
            } catch (InterruptedException e) {
                throw new RuntimeException("Interrupted while waiting for response");
            }
        }

        return name;
    }

    @KafkaListener(topics = "response-name-topic", groupId = "message-service-group")
    public void listenToNameResponse(String name) {
        this.name = name;
        synchronized (this) {
            this.notify();
        }
    }
}