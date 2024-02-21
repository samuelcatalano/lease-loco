DROP TABLE IF EXISTS lease_offer;
CREATE TABLE lease_offer (
    id          BIGINT PRIMARY KEY,
    name        VARCHAR(255),
    type        VARCHAR(255),
    price       DOUBLE,
    term        INT,
    mileage     BIGINT,
    description VARCHAR(1000)
);