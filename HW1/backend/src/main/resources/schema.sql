CREATE TABLE Travel
(
    id        BIGINT NOT NULL auto_increment,
    fromcity  VARCHAR(255),
    tocity    VARCHAR(255),
    departure TIMESTAMP,
    arrive    TIMESTAMP,
    numseats  INT,
    price     DOUBLE PRECISION,
    CONSTRAINT pk_travel PRIMARY KEY (id)
);