package com.tuorg.acquistoazioni.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity @Table(name="agente")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Agente {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name="codice_fiscale", nullable=false, unique=true, length=32)
  private String codiceFiscale;

  @Column(nullable=false, length=80) private String nome;
  @Column(nullable=false, length=80) private String cognome;

  @Column(name="data_nascita", nullable=false) private LocalDate dataNascita;
  @Column(name="data_cessazione") private LocalDate dataCessazione;
  @Column(name="data_liquidazione") private LocalDate dataLiquidazione;

  @Transient
  public boolean isAttivoAlla(LocalDate data) {
    if (dataCessazione != null && !data.isBefore(dataCessazione)) return false;
    if (dataLiquidazione != null && !data.isBefore(dataLiquidazione)) return false;
    return true;
  }
}
