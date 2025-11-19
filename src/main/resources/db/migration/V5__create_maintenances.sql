CREATE TABLE maintenances
(
    id                UUID                        NOT NULL,
    created_at        TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    created_by        VARCHAR(100)                NOT NULL,
    updated_at        TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated_by        VARCHAR(100)                NOT NULL,
    equipment_id      UUID                        NOT NULL,
    responsible_id    UUID                        NOT NULL,
    type              VARCHAR(20)                 NOT NULL,
    status            VARCHAR(20)                 NOT NULL,
    description       VARCHAR(500),
    scheduled_date    date,
    start_date        TIMESTAMP WITHOUT TIME ZONE,
    completion_date   TIMESTAMP WITHOUT TIME ZONE,
    notes             VARCHAR(1000),
    approved_by_id    UUID,
    approved_at       TIMESTAMP WITHOUT TIME ZONE,
    requires_approval BOOLEAN                     NOT NULL,
    CONSTRAINT pk_maintenances PRIMARY KEY (id)
);

ALTER TABLE maintenances
    ADD CONSTRAINT FK_MAINTENANCES_ON_APPROVED_BY FOREIGN KEY (approved_by_id) REFERENCES users (id);

ALTER TABLE maintenances
    ADD CONSTRAINT FK_MAINTENANCES_ON_EQUIPMENT FOREIGN KEY (equipment_id) REFERENCES equipments (id);

ALTER TABLE maintenances
    ADD CONSTRAINT FK_MAINTENANCES_ON_RESPONSIBLE FOREIGN KEY (responsible_id) REFERENCES users (id);
