drop table if exists user_role;
drop table if exists role;
drop table if exists user_details;
drop table if exists "user";

create table "user"
(
    user_id  serial      not null unique primary key,
    username varchar(50) not null unique,
    password varchar(80) not null,
    enabled  boolean     not null default true
);

create table user_details
(
    user_details_id serial      not null unique primary key,
    user_id         int         not null unique references "user" (user_id),
    first_name      varchar(50) not null,
    last_name       varchar(50) not null,
    email           varchar(50) not null,
    phone           varchar(10) not null
);

create table role
(
    role_id serial      not null unique primary key,
    name    varchar(14) not null unique
-- ROLE_CLIENT, ROLE_DIETITIAN
);

create table user_role
(
    user_id int not null references "user" (user_id),
    role_id int not null references role (role_id),
    primary key (user_id)
-- A user can have only one authority (ROLE_CLIENT or ROLE_DIETITIAN)
);