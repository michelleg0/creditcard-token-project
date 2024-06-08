create table if not exists customer
(
    id         int auto_increment
        primary key,
    first_name varchar(100) not null,
    last_name  varchar(100) not null,
    email      varchar(50)  not null
);

create table if not exists payment_processor
(
    id   int auto_increment
        primary key,
    name varchar(100) not null
);

create table if not exists credit_card
(
    id                   int auto_increment
        primary key,
    credit_card_token    varchar(100) not null,
    last_four_cc_digits  varchar(4)   not null,
    expiration_month     varchar(2)   not null,
    expiration_year      varchar(2)   not null,
    customer_id          int          null,
    payment_processor_id int          null,
    constraint credit_card_customer_id_fk
        foreign key (customer_id) references customer (id),
    constraint credit_card_payment_processor_id_fk
        foreign key (payment_processor_id) references payment_processor (id)
);
