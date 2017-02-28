-- ---------------------------------------------------------------------------------------------------------------------
-- INICIA - DATOS DE PRUEBA: MODIFICADOR CONDICION PRENDA
-- ---------------------------------------------------------------------------------------------------------------------

--
-- HISTÓRICOS
--
INSERT INTO hist_cfg_diamante_listado_modificador_condicion_prenda (id, ultima_actualizacion, fecha_listado)
VALUES (1, '2017-02-20 10:00:00.521-06:00', '2017-02-20');
INSERT INTO hist_cfg_diamante_listado_modificador_condicion_prenda (id, ultima_actualizacion, fecha_listado)
VALUES (2, '2017-02-21 10:00:00.521-06:00', '2017-02-21');
INSERT INTO hist_cfg_diamante_listado_modificador_condicion_prenda (id, ultima_actualizacion, fecha_listado)
VALUES (3, '2017-02-21 11:00:00.521-06:00', '2017-02-21');

INSERT INTO hist_cfg_diamante_modificador_condicion_prenda (id, condicion_prenda, factor, listado)
VALUES (1, 'EX', 1.50, 1);
INSERT INTO hist_cfg_diamante_modificador_condicion_prenda (id, condicion_prenda, factor, listado)
VALUES (2, 'EX', 1.00, 2);
INSERT INTO hist_cfg_diamante_modificador_condicion_prenda (id, condicion_prenda, factor, listado)
VALUES (3, 'BN', 0.99, 2);
INSERT INTO hist_cfg_diamante_modificador_condicion_prenda (id, condicion_prenda, factor, listado)
VALUES (4, 'EX', 1.03, 3);
INSERT INTO hist_cfg_diamante_modificador_condicion_prenda (id, condicion_prenda, factor, listado)
VALUES (5, 'BN', 1.02, 3);
INSERT INTO hist_cfg_diamante_modificador_condicion_prenda (id, condicion_prenda, factor, listado)
VALUES (6, 'RE', 1.01, 3);


--
-- VIGENTES
--
INSERT INTO cfg_diamante_listado_modificador_condicion_prenda (id, ultima_actualizacion, fecha_listado)
VALUES (4, '2017-02-24 10:00:00.521-06:00', '2017-02-24');

INSERT INTO cfg_diamante_modificador_condicion_prenda (id, condicion_prenda, factor, listado)
VALUES (1, 'EX', 1.10, 4);
INSERT INTO cfg_diamante_modificador_condicion_prenda (id, condicion_prenda, factor, listado)
VALUES (2, 'BN', 1.05, 4);
INSERT INTO cfg_diamante_modificador_condicion_prenda (id, condicion_prenda, factor, listado)
VALUES (3, 'RE', 1.00, 4);
-- ---------------------------------------------------------------------------------------------------------------------
-- TERMINA - DATOS DE PRUEBA: MODIFICADOR CONDICION PRENDA
-- ---------------------------------------------------------------------------------------------------------------------
