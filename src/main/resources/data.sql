INSERT INTO department (name)
VALUES ('PRODUCTION'),
       ('ACCOUNTING'),
       ('LEGAL'),
       ('HR');

INSERT INTO location (street_address, postal_code, city, state_province)
VALUES ('Vladimirsky prospect, 23', '191002', 'Saint Petersburg', 'Leningrad region'),
       ('1st Tverskaya-Yamskaya street, 29 building 1', '125047', 'Moscow', 'Moscow region'),
       ('Koltsovskaya, 35', '394006', 'Voronezh', 'Voronezh region');

INSERT INTO job (title, description)
VALUES ('java developer', 'senior'),
       ('accountant', 'middle'),
       ('hr recruiter', 'middle');

INSERT INTO contract (salary, start_date, end_date, last_revision_date, description)
VALUES (10000, '2015-05-05', '2025-05-05', '2023-01-01', 'contract'),
       (10000, '2015-05-05', '2025-05-05', '2023-01-01', 'contract');

INSERT INTO employee ("name", surname, middle_name, email, birthday, status, hire_date, manager_id, location_id,
                      department_id, job_id, contract_id)
VALUES ('Ivan', 'Ivanov', 'Ivanovich', 'ivan.ivanov@email.ru', '1990-01-01', 'ACTIVE', '2015-05-05', 2, 1, 1, 1, 1),
       ('Manager', 'Manager', 'Manager', 'manager.manager@email.ru', '1990-01-01', 'ACTIVE', '2015-05-05', null, 1, 1,
        1, 1),
       ('Petr', 'Petrov', 'Petrovich', 'petr.petrov@email.ru', '1990-01-01', 'INACTIVE', '2015-05-05', 2, 1, 1, 1, 1);

INSERT INTO project (project_type, area, status, start_date, end_date, owner_id)
VALUES ('VERY EXPENSIVE PROJECT', 'FINTECH', 'ACTIVE', '2022-01-01', '2025-01-01', 2);

INSERT INTO employee_project (project_id, employee_id, employee_project_role, employee_start_date, employee_end_date)
VALUES (1, 2, 'OWNER', '2022-01-01', '2025-01-01');

INSERT INTO employee_contact ("type", account_name, description, employee_id)
VALUES ('TELEGRAM', 'ivanovIvan', 'telegram', 1),
       ('TELEGRAM', 'managerManager', 'telegram', 2);




