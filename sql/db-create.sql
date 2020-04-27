SET NAMES utf8;

DROP DATABASE IF EXISTS airline;
CREATE DATABASE airline CHARACTER SET utf8 COLLATE utf8_bin;


USE airline;

DROP TABLE IF EXISTS flight_statuses;
DROP TABLE IF EXISTS flights;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS roles;
DROP TABLE IF EXISTS workers_rank;
DROP TABLE IF EXISTS workers;
DROP TABLE IF EXISTS brigades;
DROP TABLE IF EXISTS application_statuses;
DROP TABLE IF EXISTS application;

CREATE TABLE roles
(
    id   INT         NOT NULL PRIMARY KEY,
    name VARCHAR(10) NOT NULL UNIQUE
) ENGINE = InnoDB;

INSERT INTO roles
VALUES (0, 'admin');
INSERT INTO roles
VALUES (1, 'dispatcher');
INSERT INTO roles
VALUES (2, 'user');

CREATE TABLE workers_rank
(
    id   INT         NOT NULL PRIMARY KEY,
    name VARCHAR(20) NOT NULL UNIQUE
) ENGINE = InnoDB;

INSERT INTO workers_rank
VALUES (1, 'pilot');
INSERT INTO workers_rank
VALUES (2, 'navigator');
INSERT INTO workers_rank
VALUES (3, 'operator');
INSERT INTO workers_rank
VALUES (4, 'stewardess');


CREATE TABLE workers
(
    id         INT         NOT NULL AUTO_INCREMENT PRIMARY KEY,
    firstName  VARCHAR(45) NOT NULL,
    lastName   VARCHAR(45) NOT NULL,
    rank_id    INT,
    brigade_id INT REFERENCES brigades (id)
        ON DELETE CASCADE
        ON UPDATE RESTRICT,
    FOREIGN KEY (rank_id)
        REFERENCES workers_rank (id)
        ON DELETE CASCADE
        ON UPDATE RESTRICT

) ENGINE = InnoDB;

INSERT INTO workers
VALUES (DEFAULT, 'Petr', 'Petrov', 1, NULL);
INSERT INTO workers
VALUES (DEFAULT, 'Corey', 'Taylor', 1, NULL);
INSERT INTO workers
VALUES (DEFAULT, 'Ivan', 'Ivanov', 2, NULL);
INSERT INTO workers
VALUES (DEFAULT, 'Kirill', 'Lermontov', 2, NULL);
INSERT INTO workers
VALUES (DEFAULT, 'Sergey', 'Ivanchenko', 3, NULL);
INSERT INTO workers
VALUES (DEFAULT, 'Nikolay ', 'Sobolev', 3, NULL);
INSERT INTO workers
VALUES (DEFAULT, 'Maria', 'Kostenko', 4, NULL);
INSERT INTO workers
VALUES (DEFAULT, 'Andrey', 'Ognev', 4, NULL);

CREATE TABLE flight_statuses
(
    id     INT         NOT NULL PRIMARY KEY,
    status VARCHAR(20) NOT NULL UNIQUE
);

INSERT INTO flight_statuses
VALUES (1, 'open');
INSERT INTO flight_statuses
VALUES (2, 'done');
INSERT INTO flight_statuses
VALUES (3, 'canceled');


CREATE TABLE flights
(
    id        INT         NOT NULL AUTO_INCREMENT PRIMARY KEY,
    number    INT         NOT NULL,
    isFrom    VARCHAR(45) NOT NULL,
    whereTo   VARCHAR(45) NOT NULL,
    date      DATE NOT NULL,
    name      VARCHAR(45) NOT NULL,
    brigade   INT REFERENCES brigades (id)
        ON DELETE CASCADE
        ON UPDATE RESTRICT,
    status_id INT         NOT NULL,
    FOREIGN KEY (status_id) REFERENCES flight_statuses (id)
        ON DELETE CASCADE
        ON UPDATE RESTRICT
) ENGINE = InnoDB;

INSERT INTO flights
VALUES (DEFAULT, '118', 'Kharkov', 'Kiev', '15.05.2020', 'Kharkov-Kiev', NULL, 1);
INSERT INTO flights
VALUES (DEFAULT, '228', 'Kiev', 'Istanbul', '15.05.2020', 'Kiev-Istanbul', NULL, 1);
INSERT INTO flights
VALUES (DEFAULT, '1488', 'New York', 'San Francisco', '20.08.2020', 'New York-San Francisco', NULL,
        1);
INSERT INTO flights
VALUES (DEFAULT, '118', 'Kharkov', 'Kiev', '16.05.2020', 'Kharkov-Kiev', NULL, 1);
INSERT INTO flights
VALUES (DEFAULT, '365', 'Kharkov', 'Lvov', '20.05.2020', 'Kharkov-Lvov', NULL, 1);
INSERT INTO flights
VALUES (DEFAULT, '255', 'New York', 'Kiev', '30.06.2020', 'New York-Kiev', NULL, 1);
INSERT INTO flights
VALUES (DEFAULT, '8841', 'San Francisco', 'New York', '21.08.2020', 'San Francisco-New York', NULL,
        1);

CREATE TABLE brigades
(
    id            INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    pilot         INT UNIQUE,
    navigator     INT UNIQUE,
    operator      INT UNIQUE,
    stewardess    INT UNIQUE,
    flight_number INT UNIQUE
) ENGINE = InnoDB;

CREATE TABLE application_statuses
(
    id     INT         NOT NULL,
    status VARCHAR(20) NOT NULL UNIQUE,
    PRIMARY KEY (id)
) ENGINE = InnoDB;

INSERT INTO application_statuses
VALUES (1, 'open');
INSERT INTO application_statuses
VALUES (2, 'reject');

CREATE TABLE applications
(
    id        INT          NOT NULL AUTO_INCREMENT PRIMARY KEY,
    subject   VARCHAR(30)  NOT NULL,
    message   VARCHAR(100) NOT NULL,
    status_id INT          NOT NULL,
    FOREIGN KEY (status_id) references application_statuses (id)
        ON DELETE CASCADE
        ON UPDATE RESTRICT
) ENGINE = InnoDB;

CREATE TABLE users
(
    id       INT         NOT NULL auto_increment PRIMARY KEY,
    login    VARCHAR(20) NOT NULL UNIQUE,
    password VARCHAR(20) NOT NULL,
    role_id  INTEGER     NOT NULL REFERENCES roles (id)
        ON DELETE CASCADE
        ON UPDATE RESTRICT
) ENGINE = InnoDB;

INSERT INTO users
VALUES (DEFAULT, 'admin', 'admin', 0);
INSERT INTO users
VALUES (DEFAULT, 'dispatcher', 'dispatcherpass', 1);
INSERT INTO users
VALUES (DEFAULT, 'user', 'user', 2);