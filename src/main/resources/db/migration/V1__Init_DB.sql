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
    employee_id  INT           NOT NULL REFERENCES employee (id),
    PRIMARY KEY (id)
);



