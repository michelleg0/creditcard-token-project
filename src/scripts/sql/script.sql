create table credit_card
(
    id                  int auto_increment
        primary key,
    credit_card_token   varchar(100) not null,
    last_four_cc_digits varchar(4)   not null,
    expiration_month    varchar(2)   not null,
    expiration_year     varchar(2)   not null
);

create table payment_processor
(
    id   int auto_increment
        primary key,
    name varchar(100) not null
);

create table customer
(
    id                   int auto_increment
        primary key,
    first_name           varchar(100) not null,
    last_name            varchar(100) not null,
    email                varchar(50)  not null,
    credit_card_id       int          null,
    payment_processor_id int          null,
    constraint customer_credit_card_id_fk
        foreign key (credit_card_id) references credit_card (id),
    constraint customer_payment_processor_id_fk
        foreign key (payment_processor_id) references payment_processor (id)
);


