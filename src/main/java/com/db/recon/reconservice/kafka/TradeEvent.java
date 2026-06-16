package com.db.recon.reconservice.kafka;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TradeEvent {

    private Long tradeId;
    private Long instrumentId;
    private String timestamp;
    private BigDecimal quantity;
    private BigDecimal price;
}