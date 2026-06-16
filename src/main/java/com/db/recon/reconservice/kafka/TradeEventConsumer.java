package com.db.recon.reconservice.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class TradeEventConsumer {

    private final ObjectMapper objectMapper;

    @KafkaListener(
            topics = "trades.settled",
            groupId = "recon-group"
    )
    public void consume(String message) {

        try {

            TradeEvent event =
                    objectMapper.readValue(
                            message,
                            TradeEvent.class);

            log.info("\n BREAK DETECTED for trade {} :-----:", event.getTradeId());


        } catch (Exception e) {

            log.error("Error processing kafka message", e);

        }
    }
}