DROP TABLE IF EXISTS department CASCADE;
DROP TABLE IF EXISTS location CASCADE;
DROP TABLE IF EXISTS job CASCADE;
DROP TABLE IF EXISTS contract CASCADE;
DROP TABLE IF EXISTS employee CASCADE;
DROP TABLE IF EXISTS project CASCADE;
DROP TABLE IF EXISTS employee_project CASCADE;
DROP TABLE IF EXISTS employee_contact CASCADE;
DROP TABLE IF EXISTS activity CASCADE;
DROP TABLE IF EXISTS employee_activity CASCADE;
DROP TABLE IF EXISTS users CASCADE;
DROP TABLE IF EXISTS roles CASCADE;
DROP TABLE IF EXISTS users_roles CASCADE;

-- -----------------------------------------------------
-- Table department
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS department
(
    id   BIGSERIAL,
    name VARCHAR(50) NOT NULL,
    PRIMARY KEY (id)
);

-- -----------------------------------------------------
-- Table location
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS location
(
    id             BIGSERIAL,
    street_address VARCHAR(256) NOT NULL,
    postal_code    VARCHAR(50)  NOT NULL,
    city           VARCHAR(50)  NOT NULL,
    state_province VARCHAR(50),
    PRIMARY KEY (id)
);

-- -----------------------------------------------------
-- Table job
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS job
(
    id          BIGSERIAL,
    title       VARCHAR(50)   NOT NULL,
    description VARCHAR(2048) NULL,
    PRIMARY KEY (id)
);

-- -----------------------------------------------------
-- Table contract
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS contract
(
    id                 BIGSERIAL,
    salary             INT           NOT NULL,
    start_date         DATE          NOT NULL,
    end_date           DATE          NULL,
    last_revision_date DATE          NULL,
    description        VARCHAR(2048) NULL,
    PRIMARY KEY (id)
);

-- -----------------------------------------------------
-- Table employee
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS employee
(
    id            BIGSERIAL,
    name          VARCHAR(50) NOT NULL,
    surname       VARCHAR(50) NOT NULL,
    middle_name   VARCHAR(50) NULL,
    email         VARCHAR(50) NOT NULL,
    birthday      DATE        NOT NULL,
    status        VARCHAR(50) NULL,
    hire_date     DATE        NULL,
    manager_id    INT         NULL REFERENCES employee (id),
    location_id   INT         NOT NULL REFERENCES location (id),
    department_id INT         NOT NULL REFERENCES department (id),
    job_id        INT         NOT NULL REFERENCES job (id),
    contract_id   INT         NOT NULL REFERENCES contract (id),
    PRIMARY KEY (id)
);

-- -----------------------------------------------------
-- Table project
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS project
(
    id           BIGSERIAL,
    project_type VARCHAR(50) NULL,
    area         VARCHAR(50) NULL,
    status       VARCHAR(50) NOT NULL,
    start_date   DATE        NOT NULL,
    end_date     DATE        NOT NULL,
    owner_id     INT         NOT NULL REFERENCES employee (id),
    PRIMARY KEY (id)
);

-- -----------------------------------------------------
-- Table employee_project
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS employee_project
(
    project_id            INT         NOT NULL REFERENCES project (id),
    employee_id           INT         NOT NULL REFERENCES employee (id),
    employee_project_role VARCHAR(50) NOT NULL,
    employee_start_date   DATE        NOT NULL,
    employee_end_date     DATE        NULL
);

-- -----------------------------------------------------
-- Table employee_contact
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS employee_contact
(
    id           BIGSERIAL,
    type         VARCHAR(50)   NOT NULL,
    account_name VARCHAR(50)   NOT NULL,
    description  VARCHAR(2048) NULL,
    employee_id  BIGINT        NOT NULL REFERENCES employee (id),
    PRIMARY KEY (id)
);

-- -----------------------------------------------------
-- Table activity
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS activity
(
    id          BIGSERIAL,
    name        VARCHAR(50)   NOT NULL,
    description VARCHAR(2048) NULL,
    status      VARCHAR(50)   NOT NULL,
    start_date  DATE          NOT NULL,
    end_date    DATE          NULL,
    PRIMARY KEY (id)
);

-- -----------------------------------------------------
-- Table employee_activity
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS employee_activity
(
    employee_id   BIGINT      NOT NULL REFERENCES employee (id),
    activity_id   BIGINT      NOT NULL REFERENCES activity (id),
    activity_role VARCHAR(50) NOT NULL
);

-- -----------------------------------------------------
-- Security tables
-- -----------------------------------------------------

CREATE TABLE users
(
    id          BIGSERIAL,
    employee_id BIGINT REFERENCES employee (id),
    username    VARCHAR(50)  NOT NULL,
    password    VARCHAR(250) NOT NULL,
    PRIMARY KEY (id),
    constraint uq1 unique (username)
);

CREATE TABLE roles
(
    id   BIGSERIAL,
    name VARCHAR(50) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE users_roles
(
    user_id BIGINT NOT NULL REFERENCES users (id),
    role_id BIGINT NOT NULL REFERENCES roles (id)
);


