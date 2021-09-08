create table question
(
    id int not null,
    title varchar(50),
    description text,
    gmt_create bigint,
    gmt_modied bigint,
    creator int,
    comment_count int default 0,
    view_count int default 0,
    like_count int default 0,
    tag varchar(256),
    constraint QUESTION_PK
        primary key (id)
);

