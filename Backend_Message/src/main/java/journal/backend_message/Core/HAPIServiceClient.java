package journal.backend_message.Core;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "hapi-service", url = "http://localhost:8080")
public interface HAPIServiceClient {
    @GetMapping("/get_general_practitioner_by_identifier")
    String getGeneralPractitionerByIdentifier(@RequestParam String id);
}