package com.db.recon.reconservice.kafka;


import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class BreakResolvedConsumer {

    @KafkaListener(
            topics = "recon.breaks.resolved",
            groupId = "recon-group")
    public void consume(String message) {
        log.info("\n BREAK RESOLVED EVENT RECEIVED  : {} :) \t ", message);
    }
}