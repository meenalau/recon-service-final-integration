package com.db.recon.reconservice.exception;

// exception/InvalidAssetClassException.java

public class InvalidAssetClassException extends RuntimeException {

    public InvalidAssetClassException(String assetClass) {
        super("Invalid asset class: '" + assetClass
                + "'. Allowed values: BOND, FX, EQUITY");
    }
}