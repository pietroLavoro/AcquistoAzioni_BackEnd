package com.tuorg.acquistoazioni.web.dto;

import jakarta.validation.constraints.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class AcquistoRequest {
  @NotNull private LocalDate dataAcquisto;            // JSON: yyyyMMdd
  @NotNull @Positive private Integer quantitaTotale;
  @NotNull @DecimalMin("0.01") private BigDecimal importoTotale;
  @NotNull private Long titoloId;
}
