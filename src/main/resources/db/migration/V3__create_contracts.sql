CREATE TABLE contracts
(
    id          UUID                        NOT NULL,
    created_at  TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    created_by  VARCHAR(100)                NOT NULL,
    updated_at  TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated_by  VARCHAR(100)                NOT NULL,
    customer_id UUID                        NOT NULL,
    number      BIGINT                      NOT NULL,
    start_date  date                        NOT NULL,
    end_date    date                        NOT NULL,
    status      VARCHAR(20)                 NOT NULL,
    total_value DECIMAL(10, 2)              NOT NULL,
    notes       VARCHAR(500),
    CONSTRAINT pk_contracts PRIMARY KEY (id)
);

CREATE SEQUENCE contract_number_seq START WITH 1 INCREMENT BY 1;

ALTER TABLE contracts
    ADD CONSTRAINT uc_contracts_number UNIQUE (number);

ALTER TABLE contracts
    ADD CONSTRAINT FK_CONTRACTS_ON_CUSTOMER FOREIGN KEY (customer_id) REFERENCES users (id);
