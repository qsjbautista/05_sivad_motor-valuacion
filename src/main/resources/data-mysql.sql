--
-- Utilizado para poblar la BD (MySQL) utilizada por el ambiente del equipo de "Testing".
--



-- ---------------------------------------------------------------------------------------------------------------------
-- INICIA - DATOS: POLÍTICAS DE CASTIGO
-- ---------------------------------------------------------------------------------------------------------------------
INSERT INTO tc_politica_castigo_pieza (id, fecha_listado)
VALUES (1, '2016-12-20 10:00:00.521');

INSERT INTO tc_politica_castigo_pieza_factores (id, politica, pieza, factor)
VALUES (1, 1, 'mx.com.nmp.ms.sivad.valuacion.dominio.modelo.Diamante', 0.8000);
INSERT INTO tc_politica_castigo_pieza_factores (id, politica, pieza, factor)
VALUES (2, 1, 'mx.com.nmp.ms.sivad.valuacion.dominio.modelo.Alhaja', 0.6000);
INSERT INTO tc_politica_castigo_pieza_factores (id, politica, pieza, factor)
VALUES (3, 1, 'mx.com.nmp.ms.sivad.valuacion.dominio.modelo.Complementario', 0.3000);
-- ---------------------------------------------------------------------------------------------------------------------
-- TERMINA - DATOS: POLÍTICAS DE CASTIGO
-- ---------------------------------------------------------------------------------------------------------------------



-- ---------------------------------------------------------------------------------------------------------------------
-- INICIA - DATOS: MODIFICADOR CONDICION PRENDA
-- ---------------------------------------------------------------------------------------------------------------------
INSERT INTO cfg_diamante_listado_modificador_condicion_prenda (id, ultima_actualizacion, fecha_listado)
VALUES (1, '2017-02-24 10:00:00.521', '2017-02-24');

INSERT INTO cfg_diamante_modificador_condicion_prenda (id, condicion_prenda, factor, listado)
VALUES (1, 'EX', 1.00, 1);
INSERT INTO cfg_diamante_modificador_condicion_prenda (id, condicion_prenda, factor, listado)
VALUES (2, 'BN', 1.00, 1);
INSERT INTO cfg_diamante_modificador_condicion_prenda (id, condicion_prenda, factor, listado)
VALUES (3, 'RE', 1.00, 1);
-- ---------------------------------------------------------------------------------------------------------------------
-- TERMINA - DATOS: MODIFICADOR CONDICION PRENDA
-- ---------------------------------------------------------------------------------------------------------------------
