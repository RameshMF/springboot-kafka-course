package net.javaguides.springboot;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SpringBootProducerApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootProducerApplication.class);
    }

    @Bean
    ApplicationRunner applicationRunner(WikimediaChangesProducer wikimediaChangesProducer) {
        return args -> {
            wikimediaChangesProducer.sendMessage();
        };
    }
}
