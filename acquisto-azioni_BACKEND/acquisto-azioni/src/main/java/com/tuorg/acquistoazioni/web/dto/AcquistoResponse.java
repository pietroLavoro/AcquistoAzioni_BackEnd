package com.tuorg.acquistoazioni.web.dto;

import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data @AllArgsConstructor
public class AcquistoResponse {
  private Long movimentoTitoloId;
  private Long titoloId;
  private LocalDate dataAcquisto;
  private Integer quantitaTotale;
  private BigDecimal importoTotale;
  private List<RipartoItem> riparti;

  @Data @AllArgsConstructor
  public static class RipartoItem {
    private Long agenteId;
    private Integer quantita;
    private BigDecimal importo;
    private Long movimentoId;
  }
}
