package com.db.recon.reconservice.exception;

// exception/InstrumentNotFoundException.java

public class InstrumentNotFoundException extends RuntimeException {

    public InstrumentNotFoundException(String isin) {
        super("Instrument not found for ISIN: " + isin);
    }

    public InstrumentNotFoundException(Long id) {
        super("Instrument not found for ID: " + id);
    }
}