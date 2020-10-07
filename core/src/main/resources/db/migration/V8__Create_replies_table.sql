create table replies (
    id bigint not null auto_increment,
    created_date datetime,
    modified_date datetime,
    content TEXT not null,
    deleted boolean default false,
    author_id bigint not null,
    post_id bigint not null,
    primary key (id)
);