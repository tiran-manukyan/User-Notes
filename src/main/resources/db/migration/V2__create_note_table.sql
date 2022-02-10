create table note
(
    id            BIGSERIAL PRIMARY KEY,
    title         VARCHAR(50) NOT NULL,
    note          VARCHAR(1000) NULL,
    user_id       BIGINT      NOT NULL REFERENCES "user" (id),
    creation_date TIMESTAMP DEFAULT now(),
    update_date   TIMESTAMP
);
