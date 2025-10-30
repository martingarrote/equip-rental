CREATE TABLE equipments
(
    id                          UUID                        NOT NULL,
    created_at                  TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    created_by                  VARCHAR(100)                NOT NULL,
    updated_at                  TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated_by                  VARCHAR(100)                NOT NULL,
    name                        VARCHAR(100)                NOT NULL,
    type                        VARCHAR(50)                 NOT NULL,
    serial_number               VARCHAR(50)                 NOT NULL,
    status                      VARCHAR(20)                 NOT NULL,
    acquisition_date            date                        NOT NULL,
    acquisition_value           DECIMAL(12, 2)              NOT NULL,
    next_preventive_maintenance date,
    maintenance_period_days     INTEGER,
    notes                       VARCHAR(500),
    CONSTRAINT pk_equipments PRIMARY KEY (id)
);

ALTER TABLE equipments
    ADD CONSTRAINT uc_equipments_serial_number UNIQUE (serial_number);