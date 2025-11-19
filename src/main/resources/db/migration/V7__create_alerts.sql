CREATE TABLE alerts
(
    id             UUID                        NOT NULL,
    created_at     TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    created_by     VARCHAR(100)                NOT NULL,
    updated_at     TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated_by     VARCHAR(100)                NOT NULL,
    target_user_id UUID                        NOT NULL,
    type           VARCHAR(30)                 NOT NULL,
    priority       VARCHAR(20)                 NOT NULL,
    message        VARCHAR(500)                NOT NULL,
    is_read        BOOLEAN                     NOT NULL,
    read_at        TIMESTAMP WITHOUT TIME ZONE,
    CONSTRAINT pk_alerts PRIMARY KEY (id)
);

ALTER TABLE alerts
    ADD CONSTRAINT FK_ALERTS_ON_TARGET_USER FOREIGN KEY (target_user_id) REFERENCES users (id);
