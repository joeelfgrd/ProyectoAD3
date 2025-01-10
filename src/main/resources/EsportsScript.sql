-- Crear la base de datos
DROP DATABASE IF EXISTS EsportsDB;
CREATE DATABASE IF NOT EXISTS EsportsDB CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci;
USE EsportsDB;

CREATE TABLE equipos (
    idEquipo BIGINT NOT NULL AUTO_INCREMENT,
    fecha_creacion DATE NOT NULL,
    nombre VARCHAR(255) NOT NULL,
    region VARCHAR(255) NOT NULL,
    tier VARCHAR(255) NOT NULL,
    PRIMARY KEY (idEquipo)
);

CREATE TABLE personal (
    tipo_jugador INT NOT NULL,
    id_jugador BIGINT NOT NULL AUTO_INCREMENT,
    Nickname VARCHAR(255) NOT NULL UNIQUE,
    apellidos VARCHAR(255),
    nombre VARCHAR(255),
    pais VARCHAR(255),
    Equipo BIGINT NOT NULL,
    PRIMARY KEY (id_jugador),
    CONSTRAINT FK_personal_equipo FOREIGN KEY (Equipo) REFERENCES equipos (idEquipo)
);

CREATE TABLE LolPlayers (
    EarlyShotcaller BIT(1),
    LateShotcaller BIT(1),
    Posicion VARCHAR(255) NOT NULL,
    id_jugador BIGINT NOT NULL,
    PRIMARY KEY (id_jugador),
    CONSTRAINT FK_LolPlayers_personal FOREIGN KEY (id_jugador) REFERENCES personal (id_jugador)
);

CREATE TABLE ValorantPlayers (
    IGL BIT(1),
    agente VARCHAR(255),
    rol VARCHAR(255) NOT NULL,
    id_jugador BIGINT NOT NULL,
    PRIMARY KEY (id_jugador),
    CONSTRAINT FK_ValorantPlayers_personal FOREIGN KEY (id_jugador) REFERENCES personal (id_jugador)
);

CREATE TABLE ligas (
    idLiga BIGINT NOT NULL AUTO_INCREMENT,
    fecha_creacion DATE NOT NULL,
    nombre VARCHAR(255) NOT NULL,
    region VARCHAR(255) NOT NULL,
    tier VARCHAR(255) NOT NULL,
    PRIMARY KEY (idLiga)
);

CREATE TABLE equipo_liga (
    id BIGINT NOT NULL AUTO_INCREMENT,
    fecha_inscripcion DATE NOT NULL,
    precio_plaza DOUBLE NOT NULL,
    id_equipo BIGINT NOT NULL,
    id_liga BIGINT NOT NULL,
    PRIMARY KEY (id),
    UNIQUE (id_equipo, id_liga),
    CONSTRAINT FK_equipo_liga_liga FOREIGN KEY (id_liga) REFERENCES ligas (idLiga),
    CONSTRAINT FK_equipo_liga_equipo FOREIGN KEY (id_equipo) REFERENCES equipos (idEquipo)
);

INSERT INTO equipos (fecha_creacion, nombre, region, tier) VALUES ('2021-01-01', 'G2 Esports', 'EU', 'S');
INSERT INTO ligas (fecha_creacion, nombre, region, tier) VALUES ('2021-01-01', 'LEC', 'EU', 'S');
INSERT INTO equipo_liga (fecha_inscripcion, precio_plaza, id_equipo, id_liga) VALUES ('2021-01-01', 1000000, 1, 1);
INSERT INTO personal (tipo_jugador, Nickname, apellidos, nombre, pais, Equipo) VALUES (1, 'Rekkles', 'Larsson', 'Martin', 'Sweden', 1);
INSERT INTO personal (tipo_jugador, Nickname, apellidos, nombre, pais, Equipo) VALUES (1, 'Perkz', 'Perkovic', 'Luka', 'Croatia', 1);
INSERT INTO LolPlayers (EarlyShotcaller, LateShotcaller, Posicion, id_jugador) VALUES (1, 1, 'Mid', 1);
INSERT INTO ValorantPlayers (IGL, agente, rol, id_jugador) VALUES (1, 'Sova', 'Duelist', 2);