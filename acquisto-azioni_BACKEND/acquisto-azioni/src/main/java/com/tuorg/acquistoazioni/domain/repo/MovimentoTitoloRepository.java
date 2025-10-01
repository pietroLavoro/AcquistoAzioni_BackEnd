package com.tuorg.acquistoazioni.domain.repo;

import com.tuorg.acquistoazioni.domain.entity.MovimentoTitolo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovimentoTitoloRepository extends JpaRepository<MovimentoTitolo, Long> {}
