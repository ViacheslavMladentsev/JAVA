DROP SCHEMA IF EXISTS public CASCADE;
CREATE SCHEMA public;

CREATE TABLE IF NOT EXISTS "user"
(
    id            serial PRIMARY KEY,
    login         varchar(50) unique NOT NULL,
    password      varchar(50)        NOT NULL
);

CREATE TABLE IF NOT EXISTS chatroom
(
    id       serial PRIMARY KEY,
    name     varchar(50) unique NOT NULL,
    owner_id integer            NOT NULL,

    CONSTRAINT chatroom_owner_id_fkey FOREIGN KEY (owner_id) REFERENCES "user" (id)
);

CREATE TABLE IF NOT EXISTS message
(
    id        serial PRIMARY KEY,
    author_id integer,
    room_id   integer,
    message   text,
    date      timestamp,

    CONSTRAINT message_author_id_fkey FOREIGN KEY (author_id) REFERENCES "user" (id),
    CONSTRAINT message_room_id_fkey FOREIGN KEY (room_id) REFERENCES chatroom (id)
);
