-- auto-generated definition
create table NOTIFICATION
(
    NOTIFIER      INT,
    RECEIVER      INT,
    OUTER_ID      INT,
    TYPE          INT,
    GMT_CREATE    BIGINT,
    STATUS        INT default 0,
    NOTIFIER_NAME VARCHAR(100),
    OUTER_TITLE   VARCHAR(256),
    ID            INT auto_increment,
    constraint NOTIFICATION_PK
        primary key (ID)
);

comment on table NOTIFICATION is '通知';

comment on column NOTIFICATION.NOTIFIER is '通知者';

comment on column NOTIFICATION.RECEIVER is '接收者';

comment on column NOTIFICATION.OUTER_ID is '问题评论 ID 或 评论评论 Id
';

comment on column NOTIFICATION.TYPE is '1：问题评论，2：评论回复';

comment on column NOTIFICATION.STATUS is '0=未读，1=已读';

