package com.tuorg.acquistoazioni.domain.repo;

import com.tuorg.acquistoazioni.domain.entity.Movimento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public interface MovimentoRepository extends JpaRepository<Movimento, Long> {
  @Query("select coalesce(sum(m.importo), 0) from Movimento m where m.agente.id = :agenteId")
  BigDecimal saldoByAgente(@Param("agenteId") Long agenteId);
}
