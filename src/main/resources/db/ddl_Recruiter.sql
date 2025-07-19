CREATE TABLE recruiter
(
    id                BIGINT AUTO_INCREMENT NOT NULL,
    username          VARCHAR(50)           NOT NULL,
    password          VARCHAR(100)          NOT NULL,
    email             VARCHAR(100)          NOT NULL,
    full_name         VARCHAR(50)           NOT NULL,
    phone_number      VARCHAR(20)           NULL,
    self_introduction TEXT                  NULL,
    created_at        datetime              NULL,
    updated_at        datetime              NULL,
    CONSTRAINT pk_recruiter PRIMARY KEY (id)
);

ALTER TABLE recruiter
    ADD CONSTRAINT uc_recruiter_email UNIQUE (email);

ALTER TABLE recruiter
    ADD CONSTRAINT uc_recruiter_username UNIQUE (username);