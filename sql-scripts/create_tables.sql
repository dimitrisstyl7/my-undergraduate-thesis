drop table if exists user_info;
drop table if exists "user";
drop table if exists role;

create table role
(
    id   serial     not null unique primary key,
    name varchar(9) not null unique -- CLIENT, DIETITIAN
);

create table "user"
(
    id       serial       not null unique primary key,
    username varchar(102) not null unique,
    password varchar(60)  not null,
    enabled  boolean      not null default false,
    role_id  int          not null references role (id)
);

create table user_info
(
    id            serial      not null unique primary key,
    user_id       int         not null unique references "user" (id),
    first_name    varchar(50) not null,
    last_name     varchar(50) not null,
    gender        char(1)     not null, -- M (Male), F (Female)
    date_of_birth date        not null,
    created_on    timestamp   not null default now(),
    email         varchar(50) not null,
    phone         varchar(20) not null
);