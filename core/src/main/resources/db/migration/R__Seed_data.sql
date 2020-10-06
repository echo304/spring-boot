INSERT INTO roles (name, created_date, modified_date)
VALUES('ADMIN', now(), now()), ('MEMBER', now(), now());

INSERT INTO accounts (name, email, PASSWORD, created_date, modified_date)
VALUES
('test1', 'test1@test.com', '$2a$10$IK9hSCKnQfQmGa5gBKfc2.uG3WXzwjtqSKrw1lWpskaLVfKfgwWFO', now(), now()),
('test2', 'test2@test.com', '$2a$10$IK9hSCKnQfQmGa5gBKfc2.uG3WXzwjtqSKrw1lWpskaLVfKfgwWFO', now(), now());

INSERT INTO account_role_assignments (account_id, role_id)
VALUES(1, 1), (1, 2), (2, 2);

INSERT INTO boards (name, description)
VALUES ('Board 1', 'First board'), ('Board 2', 'Second board');