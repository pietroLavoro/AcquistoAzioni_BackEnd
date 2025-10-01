CREATE TABLE IF NOT EXISTS agente (
  id BIGSERIAL PRIMARY KEY,
  codice_fiscale VARCHAR(32) NOT NULL UNIQUE,
  nome VARCHAR(80) NOT NULL,
  cognome VARCHAR(80) NOT NULL,
  data_nascita DATE NOT NULL,
  data_cessazione DATE,
  data_liquidazione DATE
);

CREATE TABLE IF NOT EXISTS titolo (
  id BIGSERIAL PRIMARY KEY,
  codice VARCHAR(50) NOT NULL UNIQUE,
  descrizione VARCHAR(150) NOT NULL,
  data_emissione DATE NOT NULL
);

CREATE TABLE IF NOT EXISTS movimento (
  id BIGSERIAL PRIMARY KEY,
  data DATE NOT NULL,
  importo NUMERIC(14,2) NOT NULL,
  id_agente BIGINT NOT NULL REFERENCES agente(id)
);

CREATE TABLE IF NOT EXISTS movimento_titolo (
  id BIGSERIAL PRIMARY KEY,
  id_titolo BIGINT NOT NULL REFERENCES titolo(id),
  data_investimento DATE NOT NULL,
  quantita INTEGER NOT NULL,
  importo_acquisto NUMERIC(14,2) NOT NULL
);

CREATE TABLE IF NOT EXISTS movimento_titolo_agente (
  id BIGSERIAL PRIMARY KEY,
  id_movimento_titolo BIGINT NOT NULL REFERENCES movimento_titolo(id),
  id_agente BIGINT NOT NULL REFERENCES agente(id),
  quantita INTEGER NOT NULL,
  importo_acquisto NUMERIC(14,2) NOT NULL,
  id_movimento BIGINT REFERENCES movimento(id)
);

CREATE INDEX IF NOT EXISTS idx_movimento_agente ON movimento(id_agente);
CREATE INDEX IF NOT EXISTS idx_mta_agente ON movimento_titolo_agente(id_agente);
CREATE INDEX IF NOT EXISTS idx_mvt_titolo ON movimento_titolo(id_titolo);
