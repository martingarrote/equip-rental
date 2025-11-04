CREATE TABLE users
(
    id           UUID                        NOT NULL,
    created_at   TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    created_by   VARCHAR(100)                NOT NULL,
    updated_at   TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated_by   VARCHAR(100)                NOT NULL,
    name         VARCHAR(100)                NOT NULL,
    email        VARCHAR(100)                NOT NULL,
    password     VARCHAR(255)                NOT NULL,
    role         VARCHAR(20)                 NOT NULL,
    phone        VARCHAR(20)                 NOT NULL,
    company_name VARCHAR(100),
    company_cnpj VARCHAR(18),
    department   VARCHAR(50),
    position     VARCHAR(50),
    is_active    BOOLEAN                     NOT NULL,
    CONSTRAINT pk_users PRIMARY KEY (id)
);

ALTER TABLE users
    ADD CONSTRAINT uc_users_email UNIQUE (email);