drop table if exists user_info;
drop table if exists "user";
drop table if exists role;

create table role
(
    id   serial      not null unique primary key,
    name varchar(14) not null unique
-- ROLE_CLIENT, ROLE_DIETITIAN
);

create table "user"
(
    id       serial      not null unique primary key,
    username varchar(50) not null unique,
    password varchar(80) not null,
    enabled  boolean     not null default true,
    role_id  int         not null references role (id)
);

create table user_info
(
    id         serial      not null unique primary key,
    user_id    int         not null unique references "user" (id),
    first_name varchar(50) not null,
    last_name  varchar(50) not null,
    email      varchar(50) not null,
    phone      varchar(10) not null
);