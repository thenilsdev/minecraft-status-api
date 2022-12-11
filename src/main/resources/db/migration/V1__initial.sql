CREATE TABLE data_collector
(
    id         BIGINT AUTO_INCREMENT NOT NULL,
    name       VARCHAR(255)          NOT NULL,
    email      VARCHAR(255)          NOT NULL,
    token      VARCHAR(255)          NOT NULL,
    created_at datetime              NOT NULL,
    updated_at datetime              NOT NULL,
    CONSTRAINT pk_datacollector PRIMARY KEY (id)
);

CREATE TABLE ping
(
    id                BIGINT AUTO_INCREMENT NOT NULL,
    server_id         BIGINT                NOT NULL,
    data_collector_id BIGINT                NOT NULL,
    count             BIGINT                NOT NULL,
    created_at        datetime              NOT NULL,
    updated_at        datetime              NOT NULL,
    CONSTRAINT pk_ping PRIMARY KEY (id)
);

CREATE TABLE server
(
    id         BIGINT AUTO_INCREMENT NOT NULL,
    name       VARCHAR(255)          NOT NULL,
    address    VARCHAR(255)          NOT NULL,
    type       VARCHAR(255)          NOT NULL,
    created_at datetime              NOT NULL,
    updated_at datetime              NOT NULL,
    CONSTRAINT pk_server PRIMARY KEY (id)
);

ALTER TABLE data_collector
    ADD CONSTRAINT uc_3d2077aa36f3e2e59fa11372a UNIQUE (name);

ALTER TABLE server
    ADD CONSTRAINT uc_70ded0e1af380e38e88f92ad4 UNIQUE (name);

ALTER TABLE ping
    ADD CONSTRAINT FK_PING_ON_DATACOLLECTOR FOREIGN KEY (data_collector_id) REFERENCES data_collector (id);

ALTER TABLE ping
    ADD CONSTRAINT FK_PING_ON_SERVER FOREIGN KEY (server_id) REFERENCES server (id);