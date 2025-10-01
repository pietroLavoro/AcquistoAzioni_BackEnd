package com.tuorg.acquistoazioni.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity @Table(name="titolo")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Titolo {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable=false, unique=true, length=50)
  private String codice;

  @Column(nullable=false, length=150)
  private String descrizione;

  @Column(name="data_emissione", nullable=false)
  private LocalDate dataEmissione;
}
