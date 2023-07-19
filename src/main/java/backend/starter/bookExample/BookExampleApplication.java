package backend.starter.bookExample;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class BookExampleApplication {
    public static void main(String[] args) {
        SpringApplication.run(BookExampleApplication.class, args);
    }

}
