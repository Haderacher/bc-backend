CREATE TABLE student_authorities
(
    student_id BIGINT       NOT NULL,
    `role`     VARCHAR(255) NULL
);

CREATE TABLE student_educations
(
    student_id     BIGINT       NOT NULL,
    school_name    VARCHAR(255) NULL,
    degree         VARCHAR(255) NULL,
    field_of_study VARCHAR(255) NULL,
    start_year     date         NULL,
    end_year       date         NULL
);

CREATE TABLE students
(
    id                BIGINT AUTO_INCREMENT NOT NULL,
    username          VARCHAR(50)           NOT NULL,
    password          VARCHAR(100)          NOT NULL,
    email             VARCHAR(100)          NULL,
    full_name         VARCHAR(50)           NULL,
    phone_number      VARCHAR(20)           NOT NULL,
    university        VARCHAR(100)          NULL,
    major             VARCHAR(50)           NULL,
    degree            VARCHAR(20)           NULL,
    graduation_year   INT                   NULL,
    self_introduction TEXT                  NULL,
    created_at        datetime              NULL,
    updated_at        datetime              NULL,
    CONSTRAINT pk_students PRIMARY KEY (id)
);

ALTER TABLE students
    ADD CONSTRAINT uc_students_email UNIQUE (email);

ALTER TABLE students
    ADD CONSTRAINT uc_students_username UNIQUE (username);

ALTER TABLE student_authorities
    ADD CONSTRAINT fk_student_authorities_on_student FOREIGN KEY (student_id) REFERENCES students (id);

ALTER TABLE student_educations
    ADD CONSTRAINT fk_student_educations_on_student FOREIGN KEY (student_id) REFERENCES students (id);