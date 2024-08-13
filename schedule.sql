create table schedule
(
    id       bigint       not null auto_increment,
    todo varchar(500) not null,
    username varchar(255) not null,
    password int not null,
    date DATETIME not null,
    primary key (id)
);