drop table if exists appointment;
drop type if exists appointment_status_enum;
drop table if exists announcement;
drop table if exists article_tag_association;
drop table if exists article;
drop table if exists diet_plan;
drop table if exists client_tag_association;
drop table if exists tag;
drop table if exists tag_category;
drop table if exists user_info;
drop type if exists gender_enum;
drop table if exists "user";
drop type if exists role_enum;

create type role_enum as enum ('CLIENT', 'DIETITIAN');

create table "user"
(
    id       serial       not null unique primary key,
    username varchar(102) not null unique,
    password varchar(60)  not null,
    enabled  boolean      not null default false,
    role     role_enum    not null
);

create type gender_enum as enum ('MALE', 'FEMALE');

create table user_info
(
    id            serial      not null unique primary key,
    user_id       int         not null unique references "user" (id),
    first_name    varchar(50) not null,
    last_name     varchar(50) not null,
    gender        gender_enum not null,
    date_of_birth date        not null,
    created_at    timestamp   not null default now(),
    email         varchar(50) not null,
    phone         varchar(20) not null
);

create table tag_category
(
    id   serial       not null unique primary key,
    name varchar(100) not null unique
);

create table tag
(
    id              serial       not null unique primary key,
    tag_category_id int          not null references tag_category (id),
    name            varchar(100) not null unique
);

create table client_tag_association
(
    user_info_id int not null references user_info (id),
    tag_id       int not null references tag (id),
    primary key (user_info_id, tag_id)
);

create table diet_plan
(
    id           serial      not null unique primary key,
    user_info_id int         not null references user_info (id),
    name         varchar(50) not null unique,
    created_on   date        not null default current_date,
    unique (user_info_id, created_on)
);

create table article
(
    id         serial       not null unique primary key,
    title      varchar(100) not null unique,
    content    text         not null,
    created_at timestamp    not null default now()
);

create table article_tag_association
(
    article_id int not null references article (id),
    tag_id     int not null references tag (id),
    primary key (article_id, tag_id)
);

create table announcement
(
    id         serial       not null unique primary key,
    title      varchar(100) not null unique,
    content    text         not null,
    created_at timestamp    not null default now()
);

create type appointment_status_enum as enum ('PENDING', 'SCHEDULED', 'COMPLETED', 'CANCELLED', 'DECLINED');

create table appointment
(
    id                  serial                  not null unique primary key,
    client_user_info_id int                     not null references user_info (id),
    title               varchar(100)            not null,
    description         text,
    scheduled_at        timestamp               not null,
    status              appointment_status_enum not null
);