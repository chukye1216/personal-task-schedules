create table memo
(
    id       bigint       not null auto_increment,
    todo varchar(500) not null,
    username varchar(255) not null,
    password varchar(15) not null,
    date varchar(8) not null,

    primary key (id)
);