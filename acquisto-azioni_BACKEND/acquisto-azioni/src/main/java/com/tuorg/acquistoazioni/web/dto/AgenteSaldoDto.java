package com.tuorg.acquistoazioni.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class AgenteSaldoDto {
    private Long id;
    private String codiceFiscale;
    private BigDecimal saldoDisponibile;
    private Integer quantitaTitoli; // opcional (de momento 0)
}
