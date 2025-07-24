CREATE TABLE beer_type
(
    id          UUID NOT NULL,
    name        VARCHAR(255),
    description VARCHAR(255),
    created_at  TIMESTAMP WITHOUT TIME ZONE,
    updated_at  TIMESTAMP WITHOUT TIME ZONE,
    CONSTRAINT pk_beertype PRIMARY KEY (id)
);

ALTER TABLE beer_type
    ADD CONSTRAINT uc_beertype_name UNIQUE (name);