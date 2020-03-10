create table poll (
    uuid        varchar(40)  primary key,
    title       varchar(200) not null,
    description varchar(2000),
    active      bit          not null,
    owner       varchar(100) default 'anonymous',
    inserted_at date         not null default current_time,
    updated_at  date,
);

create table poll_option (
    poll_uuid   varchar(40)  not null,
    order_nr    int          not null,
    option      varchar(200) not null
);

create table poll_response (
    poll_uuid   varchar(40) not null,
    owner       varchar(100) not null,
    responses   varchar(100) not null,
    inserted_at date         not null default current_time,
    updated_at  date,
    unique (poll_uuid, owner)
);
