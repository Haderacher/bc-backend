CREATE TABLE resume_educations
(
    resume_id      BIGINT       NOT NULL,
    school_name    VARCHAR(255) NULL,
    degree         VARCHAR(255) NULL,
    field_of_study VARCHAR(255) NULL,
    start_year     date         NULL,
    end_year       date         NULL
);

CREATE TABLE resumes
(
    id         BIGINT AUTO_INCREMENT NOT NULL,
    student_id BIGINT                NOT NULL,
    title      VARCHAR(255)          NOT NULL,
    file_url   VARCHAR(255)          NULL,
    summary    TEXT                  NULL,
    created_at datetime              NULL,
    updated_at datetime              NULL,
    CONSTRAINT pk_resumes PRIMARY KEY (id)
);

ALTER TABLE resumes
    ADD CONSTRAINT FK_RESUMES_ON_STUDENT FOREIGN KEY (student_id) REFERENCES students (id);

ALTER TABLE resume_educations
    ADD CONSTRAINT fk_resume_educations_on_resume FOREIGN KEY (resume_id) REFERENCES resumes (id);