create table poll
(
    poll_id         bigserial   not null,
    poll_name       varchar(50) not null,
    start_date      date        not null,
    completion_date date        not null,
    is_active       boolean     not null,
    primary key (poll_id)
);

create table question
(
    question_id   bigserial not null,
    poll_id       bigint    not null references poll (poll_id),
    question_text text      not null,
    display_order bigint    not null,
    primary key (question_id)
);