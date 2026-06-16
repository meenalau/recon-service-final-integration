package com.db.recon.reconservice.model;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum TradeStatus {

    EXECUTED,
    MATCHED,
    SETTLED,
    CONFIRMED;

    @JsonCreator
    public static TradeStatus fromValue(String value) {
        return TradeStatus.valueOf(value.toUpperCase());
    }
}
