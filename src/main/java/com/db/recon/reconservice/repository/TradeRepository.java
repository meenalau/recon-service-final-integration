package com.db.recon.reconservice.repository;



import com.db.recon.reconservice.model.Trade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TradeRepository extends JpaRepository<Trade, Long> {
}

/*
Spring automatically provides:

save() , findById(),findAll() ,deleteById(), count()

No SQL required.
 */