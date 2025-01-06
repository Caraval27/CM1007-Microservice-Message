package journal.lab3_message.Core;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.test.context.ActiveProfiles;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class HealthServiceTest {
    @Mock
    private KafkaTemplate<String, String> kafkaTemplate;

    @Mock
    private JwtDecoder jwtDecoder;

    @InjectMocks
    private HealthService healthService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSendGeneralPractitionerRequest_withValidSenderId() {
        String senderId = "12345";
        String expectedResponse = "Dr. John Doe";
        Jwt mockJwt = mock(Jwt.class);
        when(mockJwt.getTokenValue()).thenReturn("mockToken");
        when(jwtDecoder.decode("mockToken")).thenReturn(mockJwt);
        when(mockJwt.getClaimAsString("preferred_username")).thenReturn(senderId);

        new Thread(() -> {
            try {
                Thread.sleep(100);
                healthService.listenToGeneralPractitionerResponse(expectedResponse, "Bearer mockToken");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).start();

        String response = healthService.sendGeneralPractitionerRequest(senderId, mockJwt);

        ArgumentCaptor<ProducerRecord<String, String>> captor = ArgumentCaptor.forClass(ProducerRecord.class);
        verify(kafkaTemplate).send(captor.capture());
        ProducerRecord<String, String> sentRecord = captor.getValue();

        assertEquals("request-general-practitioner-topic", sentRecord.topic());
        assertEquals(senderId, sentRecord.value());
        assertEquals("Bearer mockToken", new String(sentRecord.headers().lastHeader("Authorization").value()));

        assertEquals(expectedResponse, response);
    }

    @Test
    void testSendNameRequest_withValidId() {
        String id = "67890";
        String senderId = "12345";
        String expectedResponse = "Jane Doe";
        Jwt mockJwt = mock(Jwt.class);
        when(mockJwt.getTokenValue()).thenReturn("mockToken");
        when(jwtDecoder.decode("mockToken")).thenReturn(mockJwt);
        when(mockJwt.getClaimAsString("preferred_username")).thenReturn(senderId);

        new Thread(() -> {
            try {
                Thread.sleep(100);
                healthService.listenToNameResponse(expectedResponse, "Bearer mockToken");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).start();

        String response = healthService.sendNameRequest(id, senderId, mockJwt);

        ArgumentCaptor<ProducerRecord<String, String>> captor = ArgumentCaptor.forClass(ProducerRecord.class);
        verify(kafkaTemplate).send(captor.capture());
        ProducerRecord<String, String> sentRecord = captor.getValue();

        assertEquals("request-name-topic", sentRecord.topic());
        assertEquals(id, sentRecord.value());
        assertEquals("Bearer mockToken", new String(sentRecord.headers().lastHeader("Authorization").value()));

        assertEquals(expectedResponse, response);
    }
}