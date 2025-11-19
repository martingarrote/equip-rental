CREATE TABLE rentals
(
    id                   UUID                        NOT NULL,
    created_at           TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    created_by           VARCHAR(100)                NOT NULL,
    updated_at           TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated_by           VARCHAR(100)                NOT NULL,
    contract_id          UUID                        NOT NULL,
    equipment_id         UUID                        NOT NULL,
    customer_id          UUID                        NOT NULL,
    start_date           date                        NOT NULL,
    expected_end_date    date                        NOT NULL,
    actual_end_date      date,
    status               VARCHAR(20)                 NOT NULL,
    total_value          DECIMAL(10, 2)              NOT NULL,
    notes                VARCHAR(500),
    return_condition     SMALLINT,
    requires_maintenance BOOLEAN                     NOT NULL,
    CONSTRAINT pk_rentals PRIMARY KEY (id)
);

ALTER TABLE rentals
    ADD CONSTRAINT FK_RENTALS_ON_CONTRACT FOREIGN KEY (contract_id) REFERENCES contracts (id);

ALTER TABLE rentals
    ADD CONSTRAINT FK_RENTALS_ON_CUSTOMER FOREIGN KEY (customer_id) REFERENCES users (id);

ALTER TABLE rentals
    ADD CONSTRAINT FK_RENTALS_ON_EQUIPMENT FOREIGN KEY (equipment_id) REFERENCES equipments (id);
