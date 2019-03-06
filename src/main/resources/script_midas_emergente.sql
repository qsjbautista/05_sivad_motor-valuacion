-- ----------------------------------------------------------------------------------------------------------------------
-- INICIO - TABLAS SISTEMA DE OPERACION PRENDARIA EMERGENTE
-- ----------------------------------------------------------------------------------------------------------------------
DROP TABLE IF EXISTS tc_politicas_castigo_subramo;
CREATE TABLE tc_politicas_castigo_subramo
(
    id BIGINT AUTO_INCREMENT NOT NULL,
    politica BIGINT NOT NULL,
    subramo VARCHAR(20) NOT NULL,
    PRIMARY KEY (id)
);

CREATE INDEX idx_tc_politicas_castigo_subramo ON tc_politicas_castigo_subramo(subramo);

ALTER TABLE tc_politicas_castigo_subramo ADD CONSTRAINT fk_tc_politicas_castigo_subramo_politica
FOREIGN KEY (politica) REFERENCES tc_politica_castigo_pieza(id);

ALTER TABLE tc_politicas_castigo_subramo ADD CONSTRAINT uk_tc_politicas_castigo_subramo
UNIQUE (politica, subramo);


INSERT INTO tc_politicas_castigo_subramo (id, politica, subramo) VALUES (1, 1, 'DI');
-- ----------------------------------------------------------------------------------------------------------------------
-- FIN - TABLAS SISTEMA DE OPERACION PRENDARIA EMERGENTE
-- ----------------------------------------------------------------------------------------------------------------------

