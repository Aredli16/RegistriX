CREATE TABLE registration
(
    id             VARCHAR(255) NOT NULL,
    last_name      VARCHAR(255),
    first_name     VARCHAR(255),
    email          VARCHAR(255),
    phone          VARCHAR(255),
    street_address VARCHAR(255),
    zip_code       VARCHAR(255),
    city           VARCHAR(255),
    country        VARCHAR(255),
    created_by     VARCHAR(255),
    created_at     timestamp,
    updated_at     timestamp,
    CONSTRAINT pk_registration PRIMARY KEY (id)
);
