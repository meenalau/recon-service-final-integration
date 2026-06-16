package com.db.recon.reconservice.kafka;


import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TradeEventPublisher {

    private static final String TOPIC = "trades.settled";

    //template -to produces Kafka object by Spring
    private final KafkaTemplate<String, String> kafkaTemplate;
    /*  App -> KafkaTemplate -> Kafka Broker
    Without KafkaTemplate: No way to send message to Kafka
    With KafkaTemplate: kafkaTemplate.send(...) message goes to Kafka.
     */

    //from Jackson -> converts Java Object to Json
    private final ObjectMapper objectMapper;

    public void publishTradeEvent(TradeEvent event) {

        try {
                           //Convert Object to String
            String json = objectMapper.writeValueAsString(event);

            kafkaTemplate.send(TOPIC, json);

            System.out.println("\n Published Event :) \t " + json);

        } catch (Exception e) {

            throw new RuntimeException(e);

        }
    }
}