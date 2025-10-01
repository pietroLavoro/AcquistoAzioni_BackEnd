INSERT INTO agente (codice_fiscale, nome, cognome, data_nascita)
VALUES
 ('ABCDEF01G23H456I','Marco','Rossi','1985-04-12'),
 ('LMNOPQ01R23S456T','Luca','Bianchi','1990-09-01'),
 ('UVWXYZ01A23B456C','Giulia','Verdi','1992-12-25');

INSERT INTO movimento (data, importo, id_agente) VALUES
 ('2025-01-01', 6000.00, 1),
 ('2025-01-01', 3000.00, 2),
 ('2025-01-01', 1000.00, 3);

INSERT INTO titolo (codice, descrizione, data_emissione)
VALUES ('TIT-001', 'Fondo Obbligazionario Europa', '2024-10-01');
