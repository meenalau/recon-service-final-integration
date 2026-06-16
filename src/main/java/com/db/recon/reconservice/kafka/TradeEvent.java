package com.db.recon.reconservice.kafka;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TradeEvent {

    private Long tradeId;

    private Long instrumentId;

    private String timestamp;
}