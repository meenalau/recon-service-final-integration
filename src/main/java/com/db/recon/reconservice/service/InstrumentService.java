package com.db.recon.reconservice.service;
// service/InstrumentService.java

import com.db.recon.reconservice.exception.InvalidAssetClassException;
import com.db.recon.reconservice.exception.InstrumentNotFoundException;
import com.db.recon.reconservice.model.Instrument;
import com.db.recon.reconservice.repository.InstrumentRepository;
import org.jboss.logging.MDC;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class InstrumentService {


    // Allowed values — mirrors the DB CHECK constraint
    private static final Set<String> VALID_ASSET_CLASSES =
            Set.of("BOND", "FX", "EQUITY");

    private final InstrumentRepository instrumentRepository;
    private static final Logger log =
            LoggerFactory.getLogger(InstrumentService.class);

    public InstrumentService(InstrumentRepository instrumentRepository) {
        this.instrumentRepository = instrumentRepository;
    }
    // ---- @Cacheable: Sprint 6 requirement ----
    /*
    @Cacheable(value = "instruments", key = "#isin")
    means: use the cache named instruments, and use the isin method parameter as the cache key.
    On a cache hit, Spring returns the stored value and never enters the method body
    — so the log line and the DB call are both skipped entirely.*/

    @Cacheable(value = "instruments", key = "#isin")
    public Instrument findByIsin(String isin) {

            MDC.put("isin", isin);
            MDC.put("action", "FIND_BY_ISIN");

            log.info("Cache MISS - querying DB");

            try {
                return instrumentRepository.findByIsin(isin)
                        .orElseThrow(() -> new InstrumentNotFoundException(isin));
            } finally {
                MDC.clear();
            }
        }

    public Instrument findById(Long id) {
        return instrumentRepository.findById(id)
                .orElseThrow(() -> new InstrumentNotFoundException(id));
    }

    public List<Instrument> findAll() {
        return instrumentRepository.findAll();
    }

    public List<Instrument> findByAssetClass(String assetClass) {
        validateAssetClass(assetClass);
        return instrumentRepository.findByAssetClass(assetClass.toUpperCase());
    }

    public Instrument save(Instrument instrument) {
        validateAssetClass(instrument.getAssetClass());
        // Normalize to uppercase before saving
        instrument.setAssetClass(instrument.getAssetClass().toUpperCase());


        return instrumentRepository.save(instrument);
    }

    public void delete(Long id) {
        Instrument existing = findById(id);
        instrumentRepository.delete(existing);
    }

    // ---- Private validation ----
    private void validateAssetClass(String assetClass) {
        if (assetClass == null
                || !VALID_ASSET_CLASSES.contains(assetClass.toUpperCase())) {
            throw new InvalidAssetClassException(assetClass);
        }
    }
}