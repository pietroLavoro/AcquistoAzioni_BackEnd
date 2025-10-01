package com.tuorg.acquistoazioni.domain.repo;

import com.tuorg.acquistoazioni.domain.entity.MovimentoTitoloAgente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovimentoTitoloAgenteRepository extends JpaRepository<MovimentoTitoloAgente, Long> {}
