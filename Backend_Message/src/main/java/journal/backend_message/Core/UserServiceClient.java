package journal.backend_message.Core;

import journal.backend_message.Core.Model.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

//@FeignClient(name = "user-service", url = "http://localhost:8082")
@FeignClient(name = "user-service", url = "https://journal-app-user.app.cloud.cbh.kth.se/")
public interface UserServiceClient {
    @GetMapping("/get_user_by_id")
    User getUserById(@RequestParam String id);
}