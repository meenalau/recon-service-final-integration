package com.db.recon.reconservice.model;
// model/Instrument.java
// model/Instrument.java

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "instruments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Instrument {

    /*GenerationType.IDENTITY tells JPA to rely on the database's identity column to generate the ID.
    When you call save(), JPA inserts without providing instrument_id,
    PostgreSQL fires the sequence starting at 101,
    and JPA reads the generated value back and sets it on your object.
    Do not set instrumentId manually when creating a new instrument.
    Pass null — JPA and PostgreSQL handle it.*/

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "instrument_id")
    @EqualsAndHashCode.Include
    private Long instrumentId;

    @Column(name = "isin", unique = true, nullable = false, length = 15)
    private String isin;

    @Column(name = "instrument_name", nullable = false, length = 20)
    private String instrumentName;

    // No enum — plain String, validated at service layer
    @Column(name = "asset_class", nullable = false, length = 10)
    private String assetClass;

    @Column(name = "currency", nullable = false, length = 3)
    private String currency;

    @Column(name = "issuer", nullable = false, length = 20)
    private String issuer;

    // Nullable — only bonds have coupon rates
    @Column(name = "coupon_rate", precision = 5, scale = 2)
    private BigDecimal couponRate;

    // Nullable — equities and FX have no maturity
    @Column(name = "maturity_date")
    private LocalDate maturityDate;
}