INSERT INTO roles (id, name, created_date, modified_date)
VALUES(1, 'ADMIN', now(), now()), (2, 'MEMBER', now(), now());

INSERT INTO accounts (id, name, email, PASSWORD, created_date, modified_date)
VALUES
(1, 'test1', 'test1@test.com', '$2a$10$IK9hSCKnQfQmGa5gBKfc2.uG3WXzwjtqSKrw1lWpskaLVfKfgwWFO', now(), now()),
(2, 'test2', 'test2@test.com', '$2a$10$IK9hSCKnQfQmGa5gBKfc2.uG3WXzwjtqSKrw1lWpskaLVfKfgwWFO', now(), now());

INSERT INTO account_role_assignments (account_id, role_id)
VALUES(1, 1), (1, 2), (2, 2);

INSERT INTO boards (id, name, description)
VALUES (1, 'Board 1', 'First board'), (2, 'Board 2', 'Second board');

INSERT INTO posts (title, author_id, board_id, content, created_date, modified_date)
VALUES
       ('Test1', 1, 1, 'test content', now(), now()),
       ('Test2', 1, 2, 'test content', now(), now()),
       ('Test3', 2, 1, 'test content', now(), now()),
       ('Test4', 2, 2, 'test content', now(), now());