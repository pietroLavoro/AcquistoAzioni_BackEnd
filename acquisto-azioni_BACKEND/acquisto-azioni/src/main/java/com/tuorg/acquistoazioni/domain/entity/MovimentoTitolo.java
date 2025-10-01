package com.tuorg.acquistoazioni.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity @Table(name="movimento_titolo")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class MovimentoTitolo {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(optional=false) @JoinColumn(name="id_titolo")
  private Titolo titolo;

  @Column(name="data_investimento", nullable=false)
  private LocalDate dataInvestimento;

  @Column(nullable=false)
  private Integer quantita;

  @Column(name="importo_acquisto", nullable=false, precision=14, scale=2)
  private BigDecimal importoAcquisto;
}
