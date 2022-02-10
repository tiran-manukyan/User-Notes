CREATE TABLE "user"
(
    id            BIGSERIAL PRIMARY KEY,
    email         VARCHAR(255) NOT NULL UNIQUE,
    password      VARCHAR(255) NOT NULL,
    creation_date TIMESTAMP DEFAULT now(),
    update_date   TIMESTAMP
);
