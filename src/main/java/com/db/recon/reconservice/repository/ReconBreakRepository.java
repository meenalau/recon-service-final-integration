package com.db.recon.reconservice.repository;

import com.db.recon.reconservice.model.ReconBreak;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReconBreakRepository  extends JpaRepository<ReconBreak, String> {

  //  List<ReconBreak> findByStatus(String status);
    //if assignment Pagination
      Page<ReconBreak> findByStatus( String status,Pageable pageable);
  }

