create table if not exists country
(
    id            bigint generated by default as identity not null
        constraint country_pk primary key,
    version       bigint    default 0                     not null,
    created_date  timestamp default now()                 not null,
    modified_date timestamp default now()                 not null,
    name          varchar(30)                             not null,
    constraint country_name_unique unique (name)
);