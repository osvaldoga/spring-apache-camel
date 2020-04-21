CREATE TABLE ITEMS
(
    ID          SERIAL,
    SKU         TEXT        NOT NULL,
    DESCRIPTION TEXT             DEFAULT NULL,
    PRICE       NUMERIC(5, 2),
    DATE        timestamptz NULL DEFAULT current_timestamp
);