package com.db.recon.reconservice.model;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "recon_breaks")
public class ReconBreak {

    @Id
    @Column(name = "break_id")
    private String breakId;

    @Column(name = "trade_id")
    private Long tradeId;

    @Column(name = "break_date")
    private LocalDate breakDate;

    @Column(name = "break_type")
    private String breakType;

    @Column(name = "our_qty")
    private BigDecimal ourQty;

    @Column(name = "their_qty")
    private BigDecimal theirQty;

    @Column(name = "our_amount")
    private BigDecimal ourAmount;

    @Column(name = "their_amount")
    private BigDecimal theirAmount;

    private String currency;

    private String status;

    public String getBreakId() {
        return breakId;
    }

    public void setBreakId(String breakId) {
        this.breakId = breakId;
    }

    public Long getTradeId() {
        return tradeId;
    }

    public void setTradeId(Long tradeId) {
        this.tradeId = tradeId;
    }

    public LocalDate getBreakDate() {
        return breakDate;
    }

    public void setBreakDate(LocalDate breakDate) {
        this.breakDate = breakDate;
    }

    public String getBreakType() {
        return breakType;
    }

    public void setBreakType(String breakType) {
        this.breakType = breakType;
    }

    public BigDecimal getOurQty() {
        return ourQty;
    }

    public void setOurQty(BigDecimal ourQty) {
        this.ourQty = ourQty;
    }

    public BigDecimal getTheirQty() {
        return theirQty;
    }

    public void setTheirQty(BigDecimal theirQty) {
        this.theirQty = theirQty;
    }

    public BigDecimal getOurAmount() {
        return ourAmount;
    }

    public void setOurAmount(BigDecimal ourAmount) {
        this.ourAmount = ourAmount;
    }

    public BigDecimal getTheirAmount() {
        return theirAmount;
    }

    public void setTheirAmount(BigDecimal theirAmount) {
        this.theirAmount = theirAmount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}