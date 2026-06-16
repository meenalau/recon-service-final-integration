package com.db.recon.reconservice.model;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

import java.math.BigDecimal;
import java.time.LocalDateTime;
// @NotNull - Value must exist.
//@NotBlank - For strings.
//@Min  - For integer values. eg @Min(1)  "quantity":0 will be Invalid
// @DecimalMin = For BigDecimal. @DecimalMin("0.01")  Invalid: "price":-10  valid "price":99.50
//@Size(min = 5,max = 20)  length validation , valid: TRD12345 , Invalid: A
//@Pattern - Regular expression validation.
            // @Pattern( regexp = "BUY|SELL", message = "Trade type must be BUY or SELL"  Invalid: "tradeType":"PURCHASE"

//DTO (Data Transfer Object) used to receive trade data from the frontend
// and Transfer data safely  from Controller to the backend service layer.
//Accept trade creation data from React/UI.Act as a container for request fields.
//Validation annotations can be added:Spring validates automatically before service execution.

//Loose Coupling Frontend is not directly tied to the database entity.
//if extra field is added , no changes in method parameters -Its just TradeCreateRequest not individual parameters .

public record TradeCreateRequest(

        @NotBlank(message = "Trader name is required")
        String traderName,

        @NotBlank(message = "Trading desk is required")
        String tradingDesk,

        @NotNull(message = "Instrument id is required")
        Long instrumentId,

        @NotNull(message = "Counterparty id is required")
        Long counterpartyId,

        @NotBlank(message = "Trade type is required")
        String tradeType,

        @NotNull(message = "Quantity is required")
        @Positive(message = "quantity must be greater than 0")
        @DecimalMin(value = "0.01", message = "Quantity must be greater than zero")
        BigDecimal quantity,

        @NotNull(message = "Price is required")
        @DecimalMin(value = "0.01", message = "Price must be greater than zero")
        BigDecimal price,

        @NotBlank(message = "Trade reference is required")
        String tradeReference,

        @NotNull(message = "Status is required")
        TradeStatus status,

        @NotNull(message = "Trade timestamp is required")
        LocalDateTime tradeTimestamp


) {
}