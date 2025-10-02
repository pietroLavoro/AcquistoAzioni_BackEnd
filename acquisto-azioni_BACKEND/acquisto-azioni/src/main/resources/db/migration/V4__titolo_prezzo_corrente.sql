-- V4__titolo_prezzo_corrente.sql
ALTER TABLE titolo
  ADD COLUMN IF NOT EXISTS prezzo_corrente NUMERIC(14,2) NOT NULL DEFAULT 0;

-- Si quieres dar un valor por defecto inicial a todos:
UPDATE titolo SET prezzo_corrente = 100.00 WHERE prezzo_corrente = 0;

-- O ajusta individualmente si tienes códigos concretos:
-- UPDATE titolo SET prezzo_corrente = 105.50 WHERE codice = 'FONDO_OBBL_EUROPA';
-- UPDATE titolo SET prezzo_corrente = 98.20  WHERE codice = '...';
