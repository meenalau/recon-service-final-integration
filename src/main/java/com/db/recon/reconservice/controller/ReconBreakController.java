package com.db.recon.reconservice.controller;

import com.db.recon.reconservice.model.ReconBreak;
import com.db.recon.reconservice.model.ResolveBreakRequest;
import com.db.recon.reconservice.service.ReconBreakService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/recon/breaks")
@CrossOrigin(origins = "http://localhost:5173")
public class ReconBreakController {

    private final ReconBreakService service;

    public ReconBreakController(ReconBreakService service) {
        this.service = service;
    }

    @PostMapping                          // ← handles POST /api/v1/recon/breaks
    public ResponseEntity<ReconBreak> createBreak(@RequestBody @Valid ReconBreak breakObj) {
        ReconBreak saved = service.create(breakObj);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @GetMapping                           // ← handles GET /api/v1/recon/breaks
    public ResponseEntity<List<ReconBreak>> getAllBreaks() {
        return ResponseEntity.ok(service.findAll());
    }

    @PutMapping("/{breakId}/resolve")
    public ResponseEntity<Void> resolveBreak(@PathVariable String breakId) {
        service.resolveBreak(breakId);
        return ResponseEntity.ok().build();
    }
}