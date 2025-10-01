package com.tuorg.acquistoazioni.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity @Table(name="movimento")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Movimento {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable=false) private LocalDate data;

  @Column(nullable=false, precision=14, scale=2)
  private BigDecimal importo;

  @ManyToOne(optional=false) @JoinColumn(name="id_agente")
  private Agente agente;
}
