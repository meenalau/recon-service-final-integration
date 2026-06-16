package com.db.recon.reconservice.kafka;

public record BreakResolvedEvent(
        String breakId,
        Long tradeId,
        String status,
        String timestamp
) {
}