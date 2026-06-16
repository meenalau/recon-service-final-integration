package com.db.recon.reconservice.service;

import io.micrometer.tracing.Tracer;
import io.micrometer.tracing.Span;
import com.db.recon.reconservice.kafka.TradeEvent;
import com.db.recon.reconservice.kafka.TradeEventPublisher;
import com.db.recon.reconservice.model.Trade;
import com.db.recon.reconservice.model.TradeCreateRequest;
import com.db.recon.reconservice.model.TradeStatus;
import com.db.recon.reconservice.repository.TradeRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TradeService {

    private final TradeRepository tradeRepository;
    private final TradeEventPublisher tradeEventPublisher;
    private final Tracer tracer;
    private static final Logger log =   LoggerFactory.getLogger(InstrumentService.class);

    public Trade createTrade(TradeCreateRequest request) {
        log.info("ENTER Service");
        //System.out.println("\t 2. In TradeService: Request object recieved \t "+ request);
        Trade trade = new Trade();

        trade.setTraderName(request.traderName());
        trade.setTradingDesk(request.tradingDesk());
        trade.setInstrumentId(request.instrumentId());
        trade.setCounterpartyId(request.counterpartyId());
        trade.setTradeType(request.tradeType());
        trade.setTradeReference(request.tradeReference());
        trade.setQuantity(request.quantity());
        trade.setPrice(request.price());
        trade.setStatus(request.status());
        trade.setTradeTimestamp(LocalDateTime.now());
        /*In a real bank, there may be 3 different timestamps for conv we r having just one so .now()
        1. Trade Execution Time-When trader actually executed trade.
        2.Trade Capture Time-When application received trade.
        3.Settlement Time- When settlement completed.T+2
            */

        Trade savedTrade = tradeRepository.save(trade);
       // System.out.println("\t 3. Saved trade in db \t"+savedTrade);
        log.info("3. Saved trade in db \"+savedTrade");

        TradeEvent event = new TradeEvent(savedTrade.getTradeId(), savedTrade.getInstrumentId(), savedTrade.getTradeTimestamp().toString());
        tradeEventPublisher.publishTradeEvent(event);

        //A Span represents a single unit of work in a request flow.
        //Think of it as a stopwatch that starts -> uses some time (single  unit of work)-> ends
        Span span = tracer.nextSpan().name("create-trade-service").start(); // just we start timer

        try (Tracer.SpanInScope ws = tracer.withSpan(span)) { //  active span for the current thread.
                                                                // log.info("Saving trade"); and tradeEventPublisher.publishTradeEvent(event);
            return savedTrade;                                  // both are activities within this span
        } finally {
            span.end();
        }
        /* because of tracing:we can track time like
        create-trade-service      30 ms
        publish-kafka-event       10 ms
        save-settlement         7000 ms
        database-query           950 ms
        */
    }

    public Optional<Trade> getTrade(Long id) {
        return tradeRepository.findById(id);
    }

    public List<Trade> getAllTrades() {
        return tradeRepository.findAll();
    }
}/*Simple Analogy of Span
Imagine a courier package.
    Trace = the entire package journey.
    Span = one stop in the journey.
Trace: Package Delivery
        Span 1: Warehouse
        Span 2: Truck
        Span 3: Sorting Center
        Span 4: Delivery Agent
        Span 5: Customer
Similarly:Trace: Trade Lifecycle
        Span 1: Create Trade
        Span 2: Save DB
        Span 3: Publish Kafka
        Span 4: Reconciliation
        Span 5: Settlement
*/