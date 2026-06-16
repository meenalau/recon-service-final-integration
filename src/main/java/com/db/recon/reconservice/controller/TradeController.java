package com.db.recon.reconservice.controller;

import com.db.recon.reconservice.model.Trade;
import com.db.recon.reconservice.model.TradeCreateRequest;
import com.db.recon.reconservice.service.InstrumentService;
import com.db.recon.reconservice.service.TradeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/api/v1/trades")
public class TradeController {

    private final TradeService tradeService;
    private static final Logger log =LoggerFactory.getLogger(InstrumentService.class);

    @PostMapping
    public ResponseEntity<Void> createTrade(@Valid @RequestBody TradeCreateRequest request) {
        log.info("1 . In controller Request body received \t"+request	);
       // System.out.println("\t 1 . In controller Request body received \t "+request);

        Trade trade = tradeService.createTrade(request);

        URI location = URI.create("/api/v1/trades/" + trade.getTradeId());

        return ResponseEntity.created(location).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Trade> getTrade(@PathVariable Long id) {

        return tradeService.getTrade(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
    //http://localhost:8080/api/trades
    @GetMapping
    public List<Trade> getAllTrades() {

        //for profile demo step 2:
        System.out.println(" \n \n inside controller \n\n ");

        return tradeService.getAllTrades();
    }

}
/*
POST :
{
"tradeId":11,
  "traderName": "Farhan",
  "tradingDesk": "Fixed Income",
  "instrumentId": 101,
  "counterpartyId": 10011,
  "tradeType": "BUY",
  "quantity": 1000.00,
  "price": 102.50,
  "tradeReference": "TRD-20260530-001",
  "status": "PENDING",
  "tradeTimestamp": "2026-05-30T10:15:30"
}
*/
// PUT
// http://localhost:8080/api/trades/11
    /*
    {
  "traderName": "Meenal Updated",
  "tradingDesk": "Fixed Income",
  "instrumentId": 103,
  "counterpartyId": 10011,
  "tradeType": "SELL",
  "quantity": 250000.00,
  "price": 84.1200,
  "tradeReference": "TRD2026003",
  "status": "SETTLED",
  "tradeTimestamp": "2026-01-15T10:15:00"
}
     */