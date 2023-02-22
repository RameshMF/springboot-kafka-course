package net.javaguides.springboot;

import com.launchdarkly.eventsource.EventHandler;
import com.launchdarkly.eventsource.EventSource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
@RequiredArgsConstructor
public class WikimediaChangesProducer {
    @Value("${source.stream.url}")
    private String sourceStreamUrl;
    private final KafkaTemplate<String, String> kafkaTemplate;

    public void sendMessage() throws InterruptedException {
        // to read real time stream data from wikimedia, we use event source
        log.info("Sending data from {} stream", sourceStreamUrl);
        EventHandler eventHandler = new WikimediaChangesHandler(kafkaTemplate, kafkaTemplate.getDefaultTopic());

        try(EventSource eventSource = new EventSource.Builder(eventHandler, URI.create(sourceStreamUrl)).build()) {
            eventSource.start();
            TimeUnit.MINUTES.sleep(10);
        }
    }
}
