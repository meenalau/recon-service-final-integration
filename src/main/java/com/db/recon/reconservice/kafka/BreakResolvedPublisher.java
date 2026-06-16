package com.db.recon.reconservice.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class BreakResolvedPublisher {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    public void publish(BreakResolvedEvent event) {

        try {

            String json = objectMapper.writeValueAsString(event);

            kafkaTemplate.send(
                    "recon.breaks.resolved",
                    json);

            log.info("\n Published Break Resolved Event : {} : ) \t)", json);

        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}