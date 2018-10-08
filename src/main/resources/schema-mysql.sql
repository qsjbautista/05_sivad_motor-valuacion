DROP TABLE IF EXISTS journal_event_data;
DROP TABLE IF EXISTS journal_entity_event;
DROP TABLE IF EXISTS journal_custom_event;
DROP TABLE IF EXISTS journal_event;

DROP TABLE IF EXISTS tc_politica_castigo_pieza_factores;
DROP TABLE IF EXISTS tc_politica_castigo_pieza;

CREATE TABLE journal_event
(
    id BIGINT AUTO_INCREMENT NOT NULL,
    principal VARCHAR(50) NOT NULL,
    date TIMESTAMP,
    type VARCHAR(255),
    primary KEY (id)
);


CREATE TABLE journal_entity_event
(
    id BIGINT NOT NULL,
    class VARCHAR(255) NOT NULL,
    primary KEY (id)
);
ALTER TABLE journal_entity_event ADD FOREIGN KEY (id) REFERENCES journal_event (id);


CREATE TABLE journal_custom_event
(
    id BIGINT NOT NULL,
    comment VARCHAR(255) NOT NULL,
    PRIMARY KEY (id)
);
ALTER TABLE journal_custom_event ADD FOREIGN KEY (id) REFERENCES journal_event (id);


CREATE TABLE journal_event_data
(
    id BIGINT AUTO_INCREMENT NOT NULL,
    event BIGINT NOT NULL,
    property VARCHAR(255) NOT NULL,
    value VARCHAR(255) NOT NULL,
    PRIMARY KEY (id)
);
ALTER TABLE journal_event_data ADD FOREIGN KEY (event) REFERENCES journal_event (id);



-- ----------------------------------------------------------------------------------------------------------------------
-- INICIA - TABLAS: POLITICAS DE CASTIGO
-- ----------------------------------------------------------------------------------------------------------------------

CREATE TABLE tc_politica_castigo_pieza
(
    id BIGINT AUTO_INCREMENT NOT NULL,
    fecha_listado TIMESTAMP NOT NULL,
    PRIMARY KEY (id)
);

CREATE INDEX idx_tc_politica_castigo_pieza_id ON tc_politica_castigo_pieza(id);
CREATE INDEX idx_tc_politica_castigo_pieza_fecha_listado ON tc_politica_castigo_pieza(fecha_listado);

CREATE TABLE tc_politica_castigo_pieza_factores
(
    id BIGINT AUTO_INCREMENT NOT NULL,
    politica BIGINT NOT NULL,
    pieza VARCHAR(255) NOT NULL,
    factor DECIMAL(5, 4) NOT NULL,
    PRIMARY KEY (id)
);
ALTER TABLE tc_politica_castigo_pieza_factores ADD FOREIGN KEY (politica) REFERENCES tc_politica_castigo_pieza (id);

-- ----------------------------------------------------------------------------------------------------------------------
-- TERMINA - TABLAS: POLITICAS DE CASTIGO
-- ----------------------------------------------------------------------------------------------------------------------



-- ----------------------------------------------------------------------------------------------------------------------
-- INICIA - TABLAS: MODIFICADOR CONDICION PRENDA
-- ----------------------------------------------------------------------------------------------------------------------

--
-- HISTÓRICOS
--

DROP TABLE IF EXISTS hist_cfg_diamante_modificador_condicion_prenda;
DROP TABLE IF EXISTS hist_cfg_diamante_listado_modificador_condicion_prenda;
DROP TABLE IF EXISTS cfg_diamante_modificador_condicion_prenda;
DROP TABLE IF EXISTS cfg_diamante_listado_modificador_condicion_prenda;

CREATE TABLE hist_cfg_diamante_listado_modificador_condicion_prenda
(
    id BIGINT AUTO_INCREMENT NOT NULL,
    ultima_actualizacion TIMESTAMP NOT NULL,
    fecha_listado DATE NOT NULL,
    PRIMARY KEY(id)
);

CREATE INDEX idx_hist_cfg_diamante_listado_modificador_condicion_prenda_id
    ON hist_cfg_diamante_listado_modificador_condicion_prenda(id);



CREATE TABLE hist_cfg_diamante_modificador_condicion_prenda
(
    id BIGINT AUTO_INCREMENT NOT NULL,
    condicion_prenda VARCHAR(20) NOT NULL,
    factor DECIMAL(6, 2) NOT NULL,
    listado BIGINT,
    PRIMARY KEY(id)
);

CREATE INDEX idx_hist_cfg_diamante_modificador_condicion_prenda_id
    ON hist_cfg_diamante_modificador_condicion_prenda(id);
ALTER TABLE hist_cfg_diamante_modificador_condicion_prenda
    ADD CONSTRAINT fk_hist_cfg_diamante_modificador_condicion_prenda
FOREIGN KEY(listado) REFERENCES hist_cfg_diamante_listado_modificador_condicion_prenda(id);


--
-- VIGENTES
--

CREATE TABLE cfg_diamante_listado_modificador_condicion_prenda
(
    id BIGINT AUTO_INCREMENT NOT NULL,
    ultima_actualizacion TIMESTAMP NOT NULL,
    fecha_listado DATE NOT NULL,
    PRIMARY KEY(id)
);

CREATE INDEX idx_cfg_diamante_listado_modificador_condicion_prenda_id
    ON cfg_diamante_listado_modificador_condicion_prenda(id);


CREATE TABLE cfg_diamante_modificador_condicion_prenda
(
    id BIGINT AUTO_INCREMENT NOT NULL,
    condicion_prenda VARCHAR(20) NOT NULL,
    factor DECIMAL(6, 2) NOT NULL,
    listado BIGINT,
    PRIMARY KEY(id)
);

CREATE INDEX idx_cfg_diamante_modificador_condicion_prenda_id
    ON cfg_diamante_modificador_condicion_prenda(id);
ALTER TABLE cfg_diamante_modificador_condicion_prenda
    ADD CONSTRAINT fk_cfg_diamante_modificador_condicion_prenda
FOREIGN KEY(listado) REFERENCES cfg_diamante_listado_modificador_condicion_prenda(id);

-- ----------------------------------------------------------------------------------------------------------------------
-- TERMINA - TABLAS: MODIFICADOR CONDICION PRENDA
-- ----------------------------------------------------------------------------------------------------------------------
