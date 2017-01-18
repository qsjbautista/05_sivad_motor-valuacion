--
-- Utilizado para poblar la BD (h2) utilizada con el perfil de desarrollo.
--



------------------------------------------------------------------------------------------------------------------------
-- INICIA - DATOS: POLÍTICAS DE CASTIGO
------------------------------------------------------------------------------------------------------------------------
INSERT INTO tc_politica_castigo_pieza (id, fecha_listado)
VALUES (1, '2016-12-20 10:00:00.521-06:00');

INSERT INTO tc_politica_castigo_pieza_factores (id, politica, pieza, factor)
VALUES (1, 1, 'mx.com.nmp.ms.sivad.valuacion.dominio.modelo.Diamante', 1.3000);
INSERT INTO tc_politica_castigo_pieza_factores (id, politica, pieza, factor)
VALUES (2, 1, 'mx.com.nmp.ms.sivad.valuacion.dominio.modelo.Alhaja', 1.2000);
INSERT INTO tc_politica_castigo_pieza_factores (id, politica, pieza, factor)
VALUES (3, 1, 'mx.com.nmp.ms.sivad.valuacion.dominio.modelo.Complementario', 1.1000);
------------------------------------------------------------------------------------------------------------------------
-- TERMINA - DATOS: POLÍTICAS DE CASTIGO
------------------------------------------------------------------------------------------------------------------------
