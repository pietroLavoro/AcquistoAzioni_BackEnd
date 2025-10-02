-- V3__seed_saldo_50000.sql
-- Deja un saldo TOTAL inicial de 50.000,00 € entre todos los agentes
-- insertando un movimiento de ABONO por agente. La distribución usa
-- centésimos para que la suma sea EXACTA (= 50.000,00 €).

DO $$
DECLARE
  n int;
  total_cents int := 50000 * 100; -- 50.000,00 € expresado en centésimos
BEGIN
  SELECT COUNT(*) INTO n FROM agente;

  IF n > 0 THEN
    WITH base AS (
      SELECT
        id,
        (total_cents / n)::int              AS cents_each,
        (total_cents % n)::int              AS rem,
        ROW_NUMBER() OVER (ORDER BY id)     AS rn
      FROM agente
    ),
    alloc AS (
      SELECT
        id,
        -- reparte el sobrante de 1 céntimo a los primeros 'rem' agentes (por id)
        (cents_each + CASE WHEN rn <= rem THEN 1 ELSE 0 END) AS cents_value
      FROM base
    )
    INSERT INTO movimento (data, importo, id_agente)
    SELECT
      CURRENT_DATE,                          -- cambia si quieres una fecha fija
      (cents_value::numeric / 100.0),        -- NUMERIC(14,2) exacto
      id
    FROM alloc
    ORDER BY id;
  END IF;
END $$;
