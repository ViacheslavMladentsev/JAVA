CREATE SEQUENCE seq_user_id START WITH 1;

CREATE TABLE users
(
    id       bigint PRIMARY KEY,
    email    varchar(50),
    password varchar(50)
);

CREATE TRIGGER trigg
    BEFORE INSERT
    ON users
    REFERENCING NEW ROW AS newrow
    FOR EACH ROW
    SET newrow.id = NEXT VALUE FOR seq_user_id;