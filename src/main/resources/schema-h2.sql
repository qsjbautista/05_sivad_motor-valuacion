DROP TABLE IF EXISTS JOURNAL_EVENT;
CREATE TABLE JOURNAL_EVENT
(
    ID BIGINT AUTO_INCREMENT NOT NULL,
    PRINCIPAL VARCHAR(50) NOT NULL,
    DATE TIMESTAMP,
    TYPE VARCHAR(255),
    PRIMARY KEY (ID)
);


DROP TABLE IF EXISTS JOURNAL_ENTITY_EVENT;
CREATE TABLE JOURNAL_ENTITY_EVENT
(
    ID BIGINT NOT NULL,
    CLASS VARCHAR(255) NOT NULL,
    PRIMARY KEY (ID)
);
ALTER TABLE JOURNAL_ENTITY_EVENT ADD FOREIGN KEY (ID) REFERENCES JOURNAL_EVENT (ID);


DROP TABLE IF EXISTS JOURNAL_CUSTOM_EVENT;
CREATE TABLE JOURNAL_CUSTOM_EVENT
(
    ID BIGINT NOT NULL,
    COMMENT VARCHAR(255) NOT NULL,
    PRIMARY KEY (ID)
);
ALTER TABLE JOURNAL_CUSTOM_EVENT ADD FOREIGN KEY (ID) REFERENCES JOURNAL_EVENT (ID);


DROP TABLE IF EXISTS JOURNAL_EVENT_DATA;
CREATE TABLE JOURNAL_EVENT_DATA
(
    ID BIGINT AUTO_INCREMENT NOT NULL,
    EVENT BIGINT NOT NULL,
    PROPERTY VARCHAR(255) NOT NULL,
    VALUE VARCHAR(255) NOT NULL,
    PRIMARY KEY (ID)
);
ALTER TABLE JOURNAL_EVENT_DATA ADD FOREIGN KEY (EVENT) REFERENCES JOURNAL_EVENT (ID);



------------------------------------------------------------------------------------------------------------------------
-- INICIA - TABLAS: POLITICAS DE CASTIGO
------------------------------------------------------------------------------------------------------------------------

DROP TABLE IF EXISTS TC_POLITICA_CASTIGO_PIEZA;
CREATE TABLE TC_POLITICA_CASTIGO_PIEZA
(
    ID BIGINT AUTO_INCREMENT NOT NULL,
    FECHA_LISTADO TIMESTAMP NOT NULL
);
ALTER TABLE TC_POLITICA_CASTIGO_PIEZA ADD CONSTRAINT PK_TC_POLITICA_CASTIGO_PIEZA_ID PRIMARY KEY(ID);

CREATE INDEX IDX_TC_POLITICA_CASTIGO_PIEZA_ID ON TC_POLITICA_CASTIGO_PIEZA(ID);
CREATE INDEX IDX_TC_POLITICA_CASTIGO_PIEZA_FECHA_LISTADO ON TC_POLITICA_CASTIGO_PIEZA(FECHA_LISTADO);

DROP TABLE IF EXISTS TC_POLITICA_CASTIGO_PIEZA_FACTORES;
CREATE TABLE TC_POLITICA_CASTIGO_PIEZA_FACTORES
(
    ID BIGINT AUTO_INCREMENT NOT NULL,
    POLITICA BIGINT NOT NULL,
    PIEZA VARCHAR(255) NOT NULL,
    FACTOR DECIMAL(5, 4) NOT NULL
);
ALTER TABLE TC_POLITICA_CASTIGO_PIEZA_FACTORES ADD CONSTRAINT PK_TC_POLITICA_CASTIGO_PIEZA_FACTORES_ID PRIMARY KEY(ID);

ALTER TABLE TC_POLITICA_CASTIGO_PIEZA_FACTORES ADD FOREIGN KEY (POLITICA) REFERENCES TC_POLITICA_CASTIGO_PIEZA (ID);

------------------------------------------------------------------------------------------------------------------------
-- TERMINA - TABLAS: POLITICAS DE CASTIGO
------------------------------------------------------------------------------------------------------------------------



------------------------------------------------------------------------------------------------------------------------
-- INICIA - TABLAS: MODIFICADOR CONDICION PRENDA
------------------------------------------------------------------------------------------------------------------------

--
-- HISTÓRICOS
--

DROP TABLE IF EXISTS HIST_CFG_DIAMANTE_LISTADO_MODIFICADOR_CONDICION_PRENDA;
CREATE TABLE HIST_CFG_DIAMANTE_LISTADO_MODIFICADOR_CONDICION_PRENDA
(
    ID BIGINT AUTO_INCREMENT NOT NULL,
    ULTIMA_ACTUALIZACION TIMESTAMP NOT NULL,
    FECHA_LISTADO DATE NOT NULL,
    PRIMARY KEY(ID)
);

CREATE INDEX IDX_HIST_CFG_DIAMANTE_LISTADO_MODIFICADOR_CONDICION_PRENDA_ID
ON HIST_CFG_DIAMANTE_LISTADO_MODIFICADOR_CONDICION_PRENDA(ID);


DROP TABLE IF EXISTS HIST_CFG_DIAMANTE_MODIFICADOR_CONDICION_PRENDA;
CREATE TABLE HIST_CFG_DIAMANTE_MODIFICADOR_CONDICION_PRENDA
(
    ID BIGINT AUTO_INCREMENT NOT NULL,
    CONDICION_PRENDA VARCHAR(20) NOT NULL,
    FACTOR DECIMAL(6, 2) NOT NULL,
    LISTADO BIGINT,
    PRIMARY KEY(ID)
);

CREATE INDEX IDX_HIST_CFG_DIAMANTE_MODIFICADOR_CONDICION_PRENDA_ID
ON HIST_CFG_DIAMANTE_MODIFICADOR_CONDICION_PRENDA(ID);
ALTER TABLE HIST_CFG_DIAMANTE_MODIFICADOR_CONDICION_PRENDA
ADD CONSTRAINT FK_HIST_CFG_DIAMANTE_MODIFICADOR_CONDICION_PRENDA
FOREIGN KEY(LISTADO) REFERENCES HIST_CFG_DIAMANTE_LISTADO_MODIFICADOR_CONDICION_PRENDA(ID);


--
-- VIGENTES
--

DROP TABLE IF EXISTS CFG_DIAMANTE_LISTADO_MODIFICADOR_CONDICION_PRENDA;
CREATE TABLE CFG_DIAMANTE_LISTADO_MODIFICADOR_CONDICION_PRENDA
(
    ID BIGINT AUTO_INCREMENT NOT NULL,
    ULTIMA_ACTUALIZACION TIMESTAMP NOT NULL,
    FECHA_LISTADO DATE NOT NULL,
    PRIMARY KEY(ID)
);

CREATE INDEX IDX_CFG_DIAMANTE_LISTADO_MODIFICADOR_CONDICION_PRENDA_ID
ON CFG_DIAMANTE_LISTADO_MODIFICADOR_CONDICION_PRENDA(ID);


DROP TABLE IF EXISTS CFG_DIAMANTE_MODIFICADOR_CONDICION_PRENDA;
CREATE TABLE CFG_DIAMANTE_MODIFICADOR_CONDICION_PRENDA
(
    ID BIGINT AUTO_INCREMENT NOT NULL,
    CONDICION_PRENDA VARCHAR(20) NOT NULL,
    FACTOR DECIMAL(6, 2) NOT NULL,
    LISTADO BIGINT,
    PRIMARY KEY(ID)
);

CREATE INDEX IDX_CFG_DIAMANTE_MODIFICADOR_CONDICION_PRENDA_ID
ON CFG_DIAMANTE_MODIFICADOR_CONDICION_PRENDA(ID);
ALTER TABLE CFG_DIAMANTE_MODIFICADOR_CONDICION_PRENDA
ADD CONSTRAINT FK_CFG_DIAMANTE_MODIFICADOR_CONDICION_PRENDA
FOREIGN KEY(LISTADO) REFERENCES CFG_DIAMANTE_LISTADO_MODIFICADOR_CONDICION_PRENDA(ID);

------------------------------------------------------------------------------------------------------------------------
-- TERMINA - TABLAS: MODIFICADOR CONDICION PRENDA
------------------------------------------------------------------------------------------------------------------------
