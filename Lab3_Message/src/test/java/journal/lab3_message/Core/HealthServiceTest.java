package journal.lab3_message.Core;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.context.ActiveProfiles;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class HealthServiceTest {
    @Mock
    private KafkaTemplate<String, String> kafkaTemplate;

    @InjectMocks
    private HealthService healthService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /*@Test
    void testSendGeneralPractitionerRequest_withValidSenderId() {
        String senderId = "12345";
        String expectedResponse = "Dr. John Doe";

        new Thread(() -> {
            try {
                Thread.sleep(100);
                healthService.listenToGeneralPractitionerResponse(expectedResponse);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).start();

        String response = healthService.sendGeneralPractitionerRequest(senderId);

        verify(kafkaTemplate).send(eq("request-general-practitioner-topic"), eq(senderId));
        assertEquals(expectedResponse, response);
    }

    @Test
    void testSendGeneralPractitionerRequest_withEmptySenderId() {
        String senderId = "";

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                healthService.sendGeneralPractitionerRequest(senderId));
        assertEquals("Request message must not be null or empty", exception.getMessage());
        verifyNoInteractions(kafkaTemplate);
    }

    @Test
    void testSendGeneralPractitionerRequest_withNullSenderId() {
        String senderId = null;

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                healthService.sendGeneralPractitionerRequest(senderId));
        assertEquals("Request message must not be null or empty", exception.getMessage());
        verifyNoInteractions(kafkaTemplate);
    }

    @Test
    void testSendNameRequest_withValidId() {
        String id = "67890";
        String expectedResponse = "Jane Doe";

        new Thread(() -> {
            try {
                Thread.sleep(100);
                healthService.listenToNameResponse(expectedResponse);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).start();

        String response = healthService.sendNameRequest(id);

        verify(kafkaTemplate).send(eq("request-name-topic"), eq(id));
        assertEquals(expectedResponse, response);
    }

    @Test
    void testSendNameRequest_withEmptyId() {
        String id = "";

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                healthService.sendNameRequest(id));
        assertEquals("Request message must not be null or empty", exception.getMessage());
        verifyNoInteractions(kafkaTemplate);
    }

    @Test
    void testSendNameRequest_withNullId() {
        String id = null;

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                healthService.sendNameRequest(id));
        assertEquals("Request message must not be null or empty", exception.getMessage());
        verifyNoInteractions(kafkaTemplate);
    }*/
}