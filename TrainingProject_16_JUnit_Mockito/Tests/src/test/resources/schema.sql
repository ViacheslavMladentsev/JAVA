CREATE TABLE IF NOT EXISTS product
(
    id    int                NOT NULL,
    name  varchar(50) unique NOT NULL,
    price numeric            NOT NULL
);
