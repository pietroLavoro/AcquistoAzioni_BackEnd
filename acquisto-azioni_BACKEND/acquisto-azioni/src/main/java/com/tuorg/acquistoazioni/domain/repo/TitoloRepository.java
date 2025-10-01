package com.tuorg.acquistoazioni.domain.repo;

import com.tuorg.acquistoazioni.domain.entity.Titolo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TitoloRepository extends JpaRepository<Titolo, Long> {
  boolean existsByCodice(String codice);
}
