package journal.lab3_message.Core;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.stereotype.Service;

@Service
public class HealthService {
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;
    private String senderId;
    private String generalPractitioner;
    private String name;
    @Autowired
    private JwtDecoder jwtDecoder;

    public String sendGeneralPractitionerRequest(String senderId, Jwt token) {
        if (senderId == null || senderId.trim().isEmpty()) {
            throw new IllegalArgumentException("Request message must not be null or empty");
        }

        generalPractitioner = null;
        this.senderId = senderId;

        ProducerRecord<String, String> record = new ProducerRecord<>("request-general-practitioner-topic", senderId);
        record.headers().add("Authorization", ("Bearer " + token.getTokenValue()).getBytes());

        kafkaTemplate.send(record);

        synchronized (this) {
            try {
                this.wait(10000);
            } catch (InterruptedException e) {
                return null;
            }
        }

        return generalPractitioner;
    }

    @KafkaListener(topics = "response-general-practitioner-topic", groupId = "message-service-group")
    public void listenToGeneralPractitionerResponse(@Payload String generalPractitioner, @Header("Authorization") String authorizationHeader) {
        if (!authorizationHeader.startsWith("Bearer ")) {
            return;
        }
        String tokenString = authorizationHeader.substring(7);
        try {
            Jwt token = jwtDecoder.decode(tokenString);
            if (isAuthorizedById(token)) {
                this.generalPractitioner = generalPractitioner;
            }
            synchronized (this) {
                this.notify();
            }
        }
        catch (JwtException e) {

        }
    }

    public String sendNameRequest(String id, String senderId, Jwt token) {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("Request message must not be null or empty");
        }
        name = null;
        this.senderId = senderId;

        ProducerRecord<String, String> record = new ProducerRecord<>("request-name-topic", id);
        record.headers().add("Authorization", ("Bearer " + token.getTokenValue()).getBytes());

        kafkaTemplate.send(record);

        synchronized (this) {
            try {
                this.wait(10000);
            } catch (InterruptedException e) {
                return null;
            }
        }

        return name;
    }

    @KafkaListener(topics = "response-name-topic", groupId = "message-service-group")
    public void listenToNameResponse(@Payload String name, @Header("Authorization") String authorizationHeader) {
        if (!authorizationHeader.startsWith("Bearer ")) {
            return;
        }
        String tokenString = authorizationHeader.substring(7);
        try {
            Jwt token = jwtDecoder.decode(tokenString);
            if (isAuthorizedById(token)) {
                this.name = name;
            }
            synchronized (this) {
                this.notify();
            }
        }
        catch (JwtException e) {

        }
    }

    private boolean isAuthorizedById(Jwt token) {
        String userId = token.getClaimAsString("preferred_username").toUpperCase();
        return userId.equals(senderId);
    }
}