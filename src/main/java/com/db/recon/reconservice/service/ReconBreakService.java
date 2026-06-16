package com.db.recon.reconservice.service;

import com.db.recon.reconservice.kafka.BreakResolvedEvent;
import com.db.recon.reconservice.kafka.BreakResolvedPublisher;
import com.db.recon.reconservice.model.ReconBreak;
import com.db.recon.reconservice.repository.ReconBreakRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReconBreakService {

    private final ReconBreakRepository repository;
    private final BreakResolvedPublisher breakResolvedPublisher;

    //  ADD THIS — called by controller POST
    public ReconBreak create(ReconBreak breakObj) {
        return repository.save(breakObj);
    }

    // ADD THIS — called by controller GET
    public List<ReconBreak> findAll() {
        return repository.findAll();
    }

    public Page<ReconBreak> getBreaks(String status, int page, int size) {
        return repository.findByStatus(status, PageRequest.of(page, size));
    }

   /* public void resolveBreak(String breakId) {
        ReconBreak reconBreak =
                repository.findById(breakId)
                        .orElseThrow(() -> new RuntimeException("Break not found"));
        reconBreak.setStatus("RESOLVED");
        repository.save(reconBreak);
    }*/
    @Transactional
    public void resolveBreak(String breakId) {

        ReconBreak reconBreak =
                repository.findById(breakId)
                        .orElseThrow(() ->
                                new RuntimeException("Break not found"));

        reconBreak.setStatus("RESOLVED");

        repository.save(reconBreak);

       BreakResolvedEvent event =
                new BreakResolvedEvent(
                        reconBreak.getBreakId(),
                        reconBreak.getTradeId(),
                        reconBreak.getStatus(),
                        java.time.LocalDateTime.now().toString());

        breakResolvedPublisher.publish(event);
    }


}

