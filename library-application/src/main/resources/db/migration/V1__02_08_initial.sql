create table t_user
(
    id         varchar(36) primary key,
    name       varchar(64) not null,
    phone      varchar(64) not null,
    email      varchar(64) not null,
    password   varchar(64) not null,
    role       varchar(64) not null,

    created    timestamp   not null default current_timestamp,
    updated    timestamp   not null default current_timestamp,
    deleted    timestamp   null,

    constraint uk_user_id_deleted unique (id, deleted),
    constraint uk_email_deleted unique (email, deleted)
);

create table t_address
(
    id            varchar(36) primary key,
    country       varchar(64) not null,
    postalZip     varchar(64) not null,
    house         varchar(64) not null,

    user_id       varchar(36) references t_user (id) on update cascade on delete cascade,

    created       timestamp   not null default current_timestamp,
    updated       timestamp   not null default current_timestamp,
    deleted       timestamp   null,

    constraint fk_address_user_id foreign key (user_id) references t_user (id),
    constraint uk_address_id_deleted unique (id, deleted)
);

create table t_payment_card
(
    id              varchar(36) primary key,
    pan             varchar(64) not null,
    expirationDate  timestamp not null,
    cvv             varchar(64) not null,

    user_id         varchar(36) references t_user (id) on update cascade on delete cascade,

    created    timestamp   not null default current_timestamp,
    updated    timestamp   not null default current_timestamp,
    deleted    timestamp   null,

    constraint fk_payment_card_user_id foreign key (user_id) references t_user (id),
    constraint uk_payment_card_id_deleted unique (id, deleted)
);

create table t_user_preferences
(
    id         varchar(36) primary key,
    preference varchar(64) not null,
    old        timestamp not null,

    user_id    varchar(36) references t_user (id) on update cascade on delete cascade,

    created    timestamp   not null default current_timestamp,
    updated    timestamp   not null default current_timestamp,
    deleted    timestamp   null,

    constraint fk_user_preference_user_id foreign key (user_id) references t_user (id),
    constraint uk_user_preference_id_deleted unique (id, deleted)
);

create table t_book
(
    id            varchar(36) primary key,
    title         varchar(64) not null,
    author        varchar(64) not null,
    genre         varchar(64) not null,
    description   varchar(512) not null,
    isbn          varchar(64) not null,
    image         varchar(64) not null,
    published     varchar(64) not null,
    publisher     varchar(64) not null,

    created    timestamp   not null default current_timestamp,
    updated    timestamp   not null default current_timestamp,
    deleted    timestamp   null,

    constraint uk_book_id_deleted unique (id, deleted)
);
