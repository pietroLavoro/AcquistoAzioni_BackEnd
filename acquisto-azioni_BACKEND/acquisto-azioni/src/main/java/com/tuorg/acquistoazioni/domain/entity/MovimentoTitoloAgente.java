package com.tuorg.acquistoazioni.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

@Entity @Table(name="movimento_titolo_agente")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class MovimentoTitoloAgente {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(optional=false) @JoinColumn(name="id_movimento_titolo")
  private MovimentoTitolo movimentoTitolo;

  @ManyToOne(optional=false) @JoinColumn(name="id_agente")
  private Agente agente;

  @Column(nullable=false)
  private Integer quantita;

  @Column(name="importo_acquisto", nullable=false, precision=14, scale=2)
  private BigDecimal importoAcquisto;

  @Column(name="id_movimento")
  private Long idMovimento; // referencia al movimiento debitado
}
