package com.db.recon.reconservice.kafka;

import com.db.recon.reconservice.model.ReconBreak;
import com.db.recon.reconservice.model.Trade;
import com.db.recon.reconservice.repository.ReconBreakRepository;
import com.db.recon.reconservice.repository.TradeRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class TradeEventConsumer {

    private final ObjectMapper objectMapper;
    private final TradeRepository tradeRepository;
    private final ReconBreakRepository reconBreakRepository;

    // Tolerance threshold for amount comparison (0.01 = 1 cent difference allowed)
    private static final BigDecimal TOLERANCE = new BigDecimal("0.01");

    // Simulated counterparty quantity factor (counterparty reports 95% of our qty)
    // In real world this comes from counterparty's settlement report
    private static final BigDecimal COUNTERPARTY_FACTOR = new BigDecimal("0.95");

    @KafkaListener(
            topics = "trades.settled",
            groupId = "recon-group"
    )
    public void consume(String message) {

        try {
            TradeEvent event = objectMapper.readValue(message, TradeEvent.class);
            log.info("📨 Received trade event for tradeId: {}", event.getTradeId());

            // Step 1 - Fetch trade from DB
            Optional<Trade> tradeOpt = tradeRepository.findById(event.getTradeId());

            if (tradeOpt.isEmpty()) {
                log.warn("⚠️ Trade not found for id: {}", event.getTradeId());
                return;
            }

            Trade trade = tradeOpt.get();

            // Step 2 - Our values (what we recorded)
            BigDecimal ourQty    = trade.getQuantity();
            BigDecimal ourAmount = ourQty.multiply(trade.getPrice())
                    .setScale(2, RoundingMode.HALF_UP);

            // Step 3 - Their values (simulated counterparty report)
            BigDecimal theirQty    = ourQty.multiply(COUNTERPARTY_FACTOR)
                    .setScale(2, RoundingMode.HALF_UP);
            BigDecimal theirAmount = theirQty.multiply(trade.getPrice())
                    .setScale(2, RoundingMode.HALF_UP);

            // Step 4 - Detect mismatches
            boolean qtyMismatch    = ourQty.subtract(theirQty).abs().compareTo(TOLERANCE) > 0;
            boolean amountMismatch = ourAmount.subtract(theirAmount).abs().compareTo(TOLERANCE) > 0;

            // Step 5 - Determine break type
            String breakType = null;

            if (qtyMismatch && amountMismatch) {
                breakType = "BOTH_MISMATCH";
            } else if (qtyMismatch) {
                breakType = "QTY_MISMATCH";
            } else if (amountMismatch) {
                breakType = "AMOUNT_MISMATCH";
            }

            // Step 6 - Create ReconBreak if mismatch found
            if (breakType != null) {
                ReconBreak reconBreak = new ReconBreak();
                reconBreak.setBreakId("BRK-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase());
                reconBreak.setTradeId(trade.getTradeId());
                reconBreak.setBreakDate(LocalDate.now());
                reconBreak.setBreakType(breakType);
                reconBreak.setOurQty(ourQty);
                reconBreak.setTheirQty(theirQty);
                reconBreak.setOurAmount(ourAmount);
                reconBreak.setTheirAmount(theirAmount);
                reconBreak.setCurrency("USD");
                reconBreak.setStatus("OPEN");

                reconBreakRepository.save(reconBreak);

                log.info("🚨 Break created! Type: {} | TradeId: {} | OurQty: {} | TheirQty: {}",
                        breakType, trade.getTradeId(), ourQty, theirQty);
            } else {
                log.info("✅ Trade {} matched! No break detected.", trade.getTradeId());
            }

        } catch (Exception e) {
            log.error("❌ Error processing kafka message: {}", e.getMessage(), e);
        }
    }
}