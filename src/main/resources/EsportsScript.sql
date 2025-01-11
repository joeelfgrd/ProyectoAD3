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
    PRIMARY KEY (idLiga),

    UNIQUE (nombre)
);

CREATE TABLE equipo_liga (
    id BIGINT NOT NULL AUTO_INCREMENT,
    fecha_inscripcion DATE NOT NULL,
    precio_plaza DOUBLE NOT NULL,
    id_equipo BIGINT NOT NULL,
    id_liga BIGINT NOT NULL,
    PRIMARY KEY (id),

    UNIQUE (id_equipo, id_liga),
    CONSTRAINT FK_equipo_liga_liga FOREIGN KEY (id_liga) REFERENCES ligas (idLiga) ON DELETE CASCADE,
    CONSTRAINT FK_equipo_liga_equipo FOREIGN KEY (id_equipo) REFERENCES equipos (idEquipo) ON DELETE CASCADE
);

INSERT INTO equipos (fecha_creacion, nombre, region, tier) VALUES ('2021-01-01', 'G2 Esports', 'EU', '1');
INSERT INTO equipos (fecha_creacion, nombre, region, tier) VALUES ('2010-02-01', 'Fnatic', 'EU', '1');
INSERT INTO equipos (fecha_creacion, nombre, region, tier) VALUES ('2011-11-19', 'Team Liquid', 'NA', '1');
INSERT INTO equipos (fecha_creacion, nombre, region, tier) VALUES ('2012-12-13', 'T1', 'KR', '1');
INSERT INTO equipos (fecha_creacion, nombre, region, tier) VALUES ('2013-01-25', 'Cloud9', 'NA', '1');
INSERT INTO equipos (fecha_creacion, nombre, region, tier) VALUES ('2017-05-19', '100 Thieves', 'NA', '2');
INSERT INTO equipos (fecha_creacion, nombre, region, tier) VALUES ('2014-06-03', 'Evil Geniuses', 'NA', '1');
INSERT INTO equipos (fecha_creacion, nombre, region, tier) VALUES ('2017-07-08', 'PSG.LGD', 'CN', '1');
INSERT INTO equipos (fecha_creacion, nombre, region, tier) VALUES ('2015-04-21', 'DRX', 'KR', '2');
INSERT INTO equipos (fecha_creacion, nombre, region, tier) VALUES ('2004-03-16', 'Natus Vincere', 'CIS', '1');
INSERT INTO equipos (fecha_creacion, nombre, region, tier) VALUES ('2019-11-20', 'Centinelas', 'NA', '2');
INSERT INTO equipos (fecha_creacion, nombre, region, tier) VALUES ('2018-12-03', 'Gambit Esports', 'CIS', '1');


-- League of Legends
INSERT INTO ligas (fecha_creacion, nombre, region, tier) VALUES ('2021-01-01', 'LEC', 'EU', '1');
INSERT INTO ligas (fecha_creacion, nombre, region, tier) VALUES ('2013-01-01', 'LCS', 'NA', '1');
INSERT INTO ligas (fecha_creacion, nombre, region, tier) VALUES ('2012-03-07', 'LCK', 'KR', '1');
INSERT INTO ligas (fecha_creacion, nombre, region, tier) VALUES ('2013-03-07', 'LPL', 'CN', '1');
INSERT INTO ligas (fecha_creacion, nombre, region, tier) VALUES ('2018-12-20', 'CBLOL', 'BR', '2');
INSERT INTO ligas (fecha_creacion, nombre, region, tier) VALUES ('2013-02-01', 'PCS', 'SEA', '2');

-- Valorant
INSERT INTO ligas (fecha_creacion, nombre, region, tier) VALUES ('2023-02-27', 'VCT Americas', 'NA', '1');
INSERT INTO ligas (fecha_creacion, nombre, region, tier) VALUES ('2023-02-27', 'VCT EMEA', 'EU', '1');
INSERT INTO ligas (fecha_creacion, nombre, region, tier) VALUES ('2023-02-27', 'VCT Pacific', 'APAC', '1');
INSERT INTO ligas (fecha_creacion, nombre, region, tier) VALUES ('2021-09-10', 'Valorant Challengers Japan', 'JP', '2');


-- League of Legends Players
INSERT INTO personal (tipo_jugador, Nickname, apellidos, nombre, pais, Equipo) VALUES (1, 'Faker', 'Sang-hyeok', 'Lee', 'South Korea', 1);
INSERT INTO LolPlayers (EarlyShotcaller, LateShotcaller, Posicion, id_jugador) VALUES (1, 1, 'Mid', 1);

INSERT INTO personal (tipo_jugador, Nickname, apellidos, nombre, pais, Equipo) VALUES (1, 'Caps', 'Winther', 'Rasmus', 'Denmark', 2);
INSERT INTO LolPlayers (EarlyShotcaller, LateShotcaller, Posicion, id_jugador) VALUES (1, 0, 'Mid', 2);

INSERT INTO personal (tipo_jugador, Nickname, apellidos, nombre, pais, Equipo) VALUES (1, 'Perkz', 'Perkovic', 'Luka', 'Croatia', 3);
INSERT INTO LolPlayers (EarlyShotcaller, LateShotcaller, Posicion, id_jugador) VALUES (0, 1, 'Mid', 3);

INSERT INTO personal (tipo_jugador, Nickname, apellidos, nombre, pais, Equipo) VALUES (1, 'Jankos', 'Jankowski', 'Marcin', 'Poland', 2);
INSERT INTO LolPlayers (EarlyShotcaller, LateShotcaller, Posicion, id_jugador) VALUES (1, 0, 'Jungle', 4);

INSERT INTO personal (tipo_jugador, Nickname, apellidos, nombre, pais, Equipo) VALUES (1, 'ShowMaker', 'Su', 'Heo', 'South Korea', 4);
INSERT INTO LolPlayers (EarlyShotcaller, LateShotcaller, Posicion, id_jugador) VALUES (1, 1, 'Mid', 5);

INSERT INTO personal (tipo_jugador, Nickname, apellidos, nombre, pais, Equipo) VALUES (1, 'Chovy', 'Ji-hoon', 'Jeong', 'South Korea', 5);
INSERT INTO LolPlayers (EarlyShotcaller, LateShotcaller, Posicion, id_jugador) VALUES (0, 1, 'Mid', 6);

INSERT INTO personal (tipo_jugador, Nickname, apellidos, nombre, pais, Equipo) VALUES (1, 'Ruler', 'Jae-hyuk', 'Park', 'South Korea', 6);
INSERT INTO LolPlayers (EarlyShotcaller, LateShotcaller, Posicion, id_jugador) VALUES (0, 1, 'ADC', 7);

INSERT INTO personal (tipo_jugador, Nickname, apellidos, nombre, pais, Equipo) VALUES (1, 'Uzi', 'Zi-Hao', 'Jian', 'China', 7);
INSERT INTO LolPlayers (EarlyShotcaller, LateShotcaller, Posicion, id_jugador) VALUES (0, 1, 'ADC', 8);

INSERT INTO personal (tipo_jugador, Nickname, apellidos, nombre, pais, Equipo) VALUES (1, 'Ming', 'Qi', 'Shi', 'China', 7);
INSERT INTO LolPlayers (EarlyShotcaller, LateShotcaller, Posicion, id_jugador) VALUES (1, 0, 'Support', 9);

INSERT INTO personal (tipo_jugador, Nickname, apellidos, nombre, pais, Equipo) VALUES (1, 'CoreJJ', 'Jo', 'Yong-in', 'South Korea', 8);
INSERT INTO LolPlayers (EarlyShotcaller, LateShotcaller, Posicion, id_jugador) VALUES (1, 1, 'Support', 10);

INSERT INTO personal (tipo_jugador, Nickname, apellidos, nombre, pais, Equipo) VALUES (1, 'Bwipo', 'Gabriel', 'Rau', 'Belgium', 2);
INSERT INTO LolPlayers (EarlyShotcaller, LateShotcaller, Posicion, id_jugador) VALUES (0, 1, 'Top', 11);

INSERT INTO personal (tipo_jugador, Nickname, apellidos, nombre, pais, Equipo) VALUES (1, 'TheShy', 'Eui-seok', 'Kang', 'South Korea', 9);
INSERT INTO LolPlayers (EarlyShotcaller, LateShotcaller, Posicion, id_jugador) VALUES (0, 1, 'Top', 12);

INSERT INTO personal (tipo_jugador, Nickname, apellidos, nombre, pais, Equipo) VALUES (1, 'Impact', 'Eon-yeong', 'Jung', 'South Korea', 8);
INSERT INTO LolPlayers (EarlyShotcaller, LateShotcaller, Posicion, id_jugador) VALUES (1, 0, 'Top', 13);

INSERT INTO personal (tipo_jugador, Nickname, apellidos, nombre, pais, Equipo) VALUES (1, 'Zven', 'Ventura', 'Jesper', 'Denmark', 3);
INSERT INTO LolPlayers (EarlyShotcaller, LateShotcaller, Posicion, id_jugador) VALUES (0, 1, 'ADC', 14);

INSERT INTO personal (tipo_jugador, Nickname, apellidos, nombre, pais, Equipo) VALUES (1, 'Hans sama', 'Steven', 'Liv', 'France', 10);
INSERT INTO LolPlayers (EarlyShotcaller, LateShotcaller, Posicion, id_jugador) VALUES (0, 1, 'ADC', 15);

INSERT INTO personal (tipo_jugador, Nickname, apellidos, nombre, pais, Equipo) VALUES (1, 'Deft', 'Hyuk-kyu', 'Kim', 'South Korea', 5);
INSERT INTO LolPlayers (EarlyShotcaller, LateShotcaller, Posicion, id_jugador) VALUES (0, 1, 'ADC', 16);

INSERT INTO personal (tipo_jugador, Nickname, apellidos, nombre, pais, Equipo) VALUES (1, 'Lehends', 'Si-woo', 'Son', 'South Korea', 11);
INSERT INTO LolPlayers (EarlyShotcaller, LateShotcaller, Posicion, id_jugador) VALUES (1, 0, 'Support', 17);

INSERT INTO personal (tipo_jugador, Nickname, apellidos, nombre, pais, Equipo) VALUES (1, 'SwordArt', 'Shuo-Chieh', 'Hu', 'Taiwan', 10);
INSERT INTO LolPlayers (EarlyShotcaller, LateShotcaller, Posicion, id_jugador) VALUES (1, 1, 'Support', 18);

INSERT INTO personal (tipo_jugador, Nickname, apellidos, nombre, pais, Equipo) VALUES (1, 'Karsa', 'Hao-Hsuan', 'Hung', 'Taiwan', 9);
INSERT INTO LolPlayers (EarlyShotcaller, LateShotcaller, Posicion, id_jugador) VALUES (1, 0, 'Jungle', 19);

INSERT INTO personal (tipo_jugador, Nickname, apellidos, nombre, pais, Equipo) VALUES (1, 'Tarzan', 'Seung-yong', 'Lee', 'South Korea', 6);
INSERT INTO LolPlayers (EarlyShotcaller, LateShotcaller, Posicion, id_jugador) VALUES (0, 1, 'Jungle', 20);

INSERT INTO personal (tipo_jugador, Nickname, apellidos, nombre, pais, Equipo) VALUES (1, 'Clid', 'Tae-min', 'Kim', 'South Korea', 4);
INSERT INTO LolPlayers (EarlyShotcaller, LateShotcaller, Posicion, id_jugador) VALUES (1, 1, 'Jungle', 21);

INSERT INTO personal (tipo_jugador, Nickname, apellidos, nombre, pais, Equipo) VALUES (1, 'Xmithie', 'Joseph', 'Poincenot', 'Philippines', 8);
INSERT INTO LolPlayers (EarlyShotcaller, LateShotcaller, Posicion, id_jugador) VALUES (1, 0, 'Jungle', 22);

INSERT INTO personal (tipo_jugador, Nickname, apellidos, nombre, pais, Equipo) VALUES (1, 'Peanut', 'Jin-seong', 'Han', 'South Korea', 6);
INSERT INTO LolPlayers (EarlyShotcaller, LateShotcaller, Posicion, id_jugador) VALUES (1, 0, 'Jungle', 23);

INSERT INTO personal (tipo_jugador, Nickname, apellidos, nombre, pais, Equipo) VALUES (1, 'Canyon', 'Geon-bu', 'Kim', 'South Korea', 4);
INSERT INTO LolPlayers (EarlyShotcaller, LateShotcaller, Posicion, id_jugador) VALUES (1, 1, 'Jungle', 24);

INSERT INTO personal (tipo_jugador, Nickname, apellidos, nombre, pais, Equipo) VALUES (1, 'BeryL', 'Geon-hee', 'Cho', 'South Korea', 5);
INSERT INTO LolPlayers (EarlyShotcaller, LateShotcaller, Posicion, id_jugador) VALUES (1, 1, 'Support', 25);

INSERT INTO personal (tipo_jugador, Nickname, apellidos, nombre, pais, Equipo) VALUES (1, 'Rekkles', 'Larsson', 'Martin', 'Sweden', 1);
INSERT INTO LolPlayers (EarlyShotcaller, LateShotcaller, Posicion, id_jugador) VALUES (0, 1, 'ADC', 26);


-- Valorant Players
INSERT INTO personal (tipo_jugador, Nickname, apellidos, nombre, pais, Equipo) VALUES (0, 'yay', 'Robinson', 'Jaccob', 'USA', 1);
INSERT INTO ValorantPlayers (IGL, agente, rol, id_jugador) VALUES (1, 'Killjoy', 'Centinela', 27);

INSERT INTO personal (tipo_jugador, Nickname, apellidos, nombre, pais, Equipo) VALUES (0, 'cNed', 'Koprulu', 'Mehmet', 'Turkey', 2);
INSERT INTO ValorantPlayers (IGL, agente, rol, id_jugador) VALUES (0, 'Jett', 'Duelistaa', 28);

INSERT INTO personal (tipo_jugador, Nickname, apellidos, nombre, pais, Equipo) VALUES (0, 'Derke', 'Sirmitev', 'Nikita', 'Finland', 3);
INSERT INTO ValorantPlayers (IGL, agente, rol, id_jugador) VALUES (1, 'Killjoy', 'Centinela', 29);

INSERT INTO personal (tipo_jugador, Nickname, apellidos, nombre, pais, Equipo) VALUES (0, 'TenZ', 'Zheng', 'Tyson', 'Canada', 4);
INSERT INTO ValorantPlayers (IGL, agente, rol, id_jugador) VALUES (1, 'Astra', 'Controlador', 30);

INSERT INTO personal (tipo_jugador, Nickname, apellidos, nombre, pais, Equipo) VALUES (0, 'Aspas', 'Goncalves', 'Erick', 'Brazil', 5);
INSERT INTO ValorantPlayers (IGL, agente, rol, id_jugador) VALUES (0, 'Raze', 'Duelista', 31);

INSERT INTO personal (tipo_jugador, Nickname, apellidos, nombre, pais, Equipo) VALUES (0, 'Chronicle', 'Khafizov', 'Timofey', 'Russia', 6);
INSERT INTO ValorantPlayers (IGL, agente, rol, id_jugador) VALUES (1, 'Omen', 'Controlador', 32);

INSERT INTO personal (tipo_jugador, Nickname, apellidos, nombre, pais, Equipo) VALUES (0, 'Shroud', 'Greene', 'Michael', 'USA', 6);
INSERT INTO ValorantPlayers (IGL, agente, rol, id_jugador) VALUES (0, 'Cypher', 'Centinela', 33);

INSERT INTO personal (tipo_jugador, Nickname, apellidos, nombre, pais, Equipo) VALUES (0, 'Keloqz', 'Robinson', 'Matthew', 'France', 7);
INSERT INTO ValorantPlayers (IGL, agente, rol, id_jugador) VALUES (0, 'Phoenix', 'Duelista', 34);

INSERT INTO personal (tipo_jugador, Nickname, apellidos, nombre, pais, Equipo) VALUES (0, 'ScreaM', 'Aime', 'Adil', 'Belgium', 8);
INSERT INTO ValorantPlayers (IGL, agente, rol, id_jugador) VALUES (1, 'Viper', 'Controlador', 35);

INSERT INTO personal (tipo_jugador, Nickname, apellidos, nombre, pais, Equipo) VALUES (0, 'Mixwell', 'Fernandez', 'Oscar', 'Spain', 9);
INSERT INTO ValorantPlayers (IGL, agente, rol, id_jugador) VALUES (0, 'Fade', 'Iniciador', 36);

INSERT INTO personal (tipo_jugador, Nickname, apellidos, nombre, pais, Equipo) VALUES (0, 'Laz', 'Kobayashi', 'Shu', 'Japan', 10);
INSERT INTO ValorantPlayers (IGL, agente, rol, id_jugador) VALUES (1, 'Sova', 'Iniciador', 37);

INSERT INTO personal (tipo_jugador, Nickname, apellidos, nombre, pais, Equipo) VALUES (0, 'Foxz', 'Chanapai', 'Patiphan', 'Thailand', 11);
INSERT INTO ValorantPlayers (IGL, agente, rol, id_jugador) VALUES (0, 'Yoru', 'Duelista', 38);

INSERT INTO personal (tipo_jugador, Nickname, apellidos, nombre, pais, Equipo) VALUES (0, 'Nivera', 'Aime', 'Khalid', 'Belgium', 1);
INSERT INTO ValorantPlayers (IGL, agente, rol, id_jugador) VALUES (1, 'Reyna', 'Duelista', 39);

INSERT INTO personal (tipo_jugador, Nickname, apellidos, nombre, pais, Equipo) VALUES (0, 'Zyppan', 'Hidayat', 'Emil', 'Sweden', 2);
INSERT INTO ValorantPlayers (IGL, agente, rol, id_jugador) VALUES (1, 'Breach', 'Iniciador', 40);

INSERT INTO personal (tipo_jugador, Nickname, apellidos, nombre, pais, Equipo) VALUES (0, 'Boaster', 'Aleksi', 'Jake', 'UK', 3);
INSERT INTO ValorantPlayers (IGL, agente, rol, id_jugador) VALUES (0, 'Skye', 'Iniciador', 41);




INSERT INTO equipo_liga (fecha_inscripcion, precio_plaza, id_equipo, id_liga) VALUES ('2021-01-01', 1000000, 1, 1);
INSERT INTO equipo_liga (fecha_inscripcion, precio_plaza, id_equipo, id_liga) VALUES ('2010-02-01', 1000000, 2, 1);
INSERT INTO equipo_liga (fecha_inscripcion, precio_plaza, id_equipo, id_liga) VALUES ('2011-11-19', 1000000, 3, 2);
INSERT INTO equipo_liga (fecha_inscripcion, precio_plaza, id_equipo, id_liga) VALUES ('2012-12-13', 1000000, 4, 3);
INSERT INTO equipo_liga (fecha_inscripcion, precio_plaza, id_equipo, id_liga) VALUES ('2013-01-25', 1000000, 5, 4);
INSERT INTO equipo_liga (fecha_inscripcion, precio_plaza, id_equipo, id_liga) VALUES ('2017-05-19', 1000000, 6, 5);
INSERT INTO equipo_liga (fecha_inscripcion, precio_plaza, id_equipo, id_liga) VALUES ('2014-06-03', 1000000, 7, 6);
INSERT INTO equipo_liga (fecha_inscripcion, precio_plaza, id_equipo, id_liga) VALUES ('2017-07-08', 1000000, 8, 7);
INSERT INTO equipo_liga (fecha_inscripcion, precio_plaza, id_equipo, id_liga) VALUES ('2015-04-21', 1000000, 9, 8);
INSERT INTO equipo_liga (fecha_inscripcion, precio_plaza, id_equipo, id_liga) VALUES ('2004-03-16', 1000000, 10, 9);

