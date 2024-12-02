package journal.backend_message;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class BackendMessageApplication {
	public static void main(String[] args) {
		SpringApplication.run(BackendMessageApplication.class, args);
	}
}