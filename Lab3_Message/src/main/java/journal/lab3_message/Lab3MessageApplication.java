package journal.lab3_message;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class Lab3MessageApplication {
	public static void main(String[] args) {
		SpringApplication.run(Lab3MessageApplication.class, args);
	}
}