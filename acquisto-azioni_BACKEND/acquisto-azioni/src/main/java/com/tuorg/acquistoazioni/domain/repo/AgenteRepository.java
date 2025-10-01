package com.tuorg.acquistoazioni.domain.repo;

import com.tuorg.acquistoazioni.domain.entity.Agente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AgenteRepository extends JpaRepository<Agente, Long> {}
