package com.db.recon.reconservice.repository;

// repository/InstrumentRepository.java

import com.db.recon.reconservice.model.Instrument;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InstrumentRepository extends JpaRepository<Instrument, Long> {

    Optional<Instrument> findByIsin(String isin);

    List<Instrument> findByAssetClass(String assetClass);

    List<Instrument> findByCurrency(String currency);

    boolean existsByIsin(String isin);
}
