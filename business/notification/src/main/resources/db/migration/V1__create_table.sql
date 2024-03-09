CREATE TABLE notification
(
    id         VARCHAR(255) NOT NULL,
    title      VARCHAR(255) NOT NULL,
    message    VARCHAR(255),
    sender     VARCHAR(255) NOT NULL,
    receiver   VARCHAR(255) NOT NULL,
    type       VARCHAR(255) NOT NULL,
    status     VARCHAR(255) NOT NULL,
    created_at TIMESTAMP WITHOUT TIME ZONE,
    updated_at TIMESTAMP WITHOUT TIME ZONE,
    CONSTRAINT pk_notification PRIMARY KEY (id)
);
