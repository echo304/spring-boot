CREATE TABLE posts (
    id BIGINT NOT NULL AUTO_INCREMENT,
    created_date DATETIME,
    modified_date DATETIME,
    content TEXT NOT NULL,
    deleted BOOLEAN DEFAULT false,
    title VARCHAR(500) NOT NULL,
    author_id BIGINT NOT NULL,
    board_id BIGINT NOT NULL,
    PRIMARY KEY (id)
);