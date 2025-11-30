CREATE TABLE histories
(
    id           UUID                        NOT NULL,
    equipment_id UUID                        NOT NULL,
    user_id      UUID                        NOT NULL,
    action       VARCHAR(30)                 NOT NULL,
    timestamp    TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    description  VARCHAR(500),
    CONSTRAINT pk_histories PRIMARY KEY (id)
);

ALTER TABLE histories
    ADD CONSTRAINT FK_HISTORIES_ON_EQUIPMENT FOREIGN KEY (equipment_id) REFERENCES equipments (id);

ALTER TABLE histories
    ADD CONSTRAINT FK_HISTORIES_ON_USER FOREIGN KEY (user_id) REFERENCES users (id);
