package com.db.recon.reconservice.controller;
// controller/InstrumentController.java

import com.db.recon.reconservice.model.Instrument;
import com.db.recon.reconservice.service.InstrumentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/instruments")
@CrossOrigin(origins = "http://localhost:5173")
public class InstrumentController {

    private final InstrumentService instrumentService;

    public InstrumentController(InstrumentService instrumentService) {
        this.instrumentService = instrumentService;
    }

    @GetMapping
    public ResponseEntity<List<Instrument>> getAll() {
        return ResponseEntity.ok(instrumentService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Instrument> getById(@PathVariable Long id) {
        return ResponseEntity.ok(instrumentService.findById(id));
    }

    @PostMapping
    public ResponseEntity<Instrument> create(
            @RequestBody Instrument instrument) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(instrumentService.save(instrument));
    }
/*  @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        instrumentService.delete(id);
        return ResponseEntity.noContent().build();
    }*/


    // GET /api/instruments/asset-class/BOND
 /*   @GetMapping("/asset-class/{assetClass}")
    public ResponseEntity<List<Instrument>> getByAssetClass(
            @PathVariable String assetClass) {
        return ResponseEntity.ok(
                instrumentService.findByAssetClass(assetClass));
    }*/

    // This endpoint triggers @Cacheable
   /* @GetMapping("/isin/{isin}")
    public ResponseEntity<Instrument> getByIsin(@PathVariable String isin) {
        return ResponseEntity.ok(instrumentService.findByIsin(isin));
    }*/

}