package journal.lab3_message.Core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class TestKafka {
    private static final String REQUEST_TOPIC = "request-topic";
    private static final String RESPONSE_TOPIC = "response-topic";

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    private String response;

    public String sendMessage(String requestMessage) {
        kafkaTemplate.send(REQUEST_TOPIC, requestMessage);

        synchronized (this) {
            try {
                this.wait(10000);
            } catch (InterruptedException e) {
                throw new RuntimeException("Interrupted while waiting for response");
            }
        }

        return response;
    }

    @KafkaListener(topics = RESPONSE_TOPIC, groupId = "message-service-group")
    public void listenToResponse(String responseMessage) {
        this.response = responseMessage;
        synchronized (this) {
            this.notify();
        }
    }
}