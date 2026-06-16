package com.db.recon.reconservice.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "trades")
public class Trade {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tradeId;
    private String traderName;
    private String tradingDesk;
    private Long instrumentId;
    private Long counterpartyId;
    private String tradeType;
    private BigDecimal quantity;
    private BigDecimal price;
    private String tradeReference;
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private TradeStatus status;
    private LocalDateTime tradeTimestamp;

}

