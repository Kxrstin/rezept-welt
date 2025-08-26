create table rezept_form
(
    id          serial primary key,
    kategorie   varchar(50),
    name        varchar(255),
    zutaten     text,
    zubereitung text,
    bild        BYTEA
);