package journal.lab3_message.Core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class HealthConsumer {
    @Autowired
    private QueueService queueService;

    @KafkaListener(topics = "receive-general-practitioner-topic", groupId = "message-service-group")
    public void receiveGeneralPractitionerResponse(String generalPractitioner) {
        try {
            queueService.putResponse(generalPractitioner);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Failed to put message in the queue", e);
        }
    }
}