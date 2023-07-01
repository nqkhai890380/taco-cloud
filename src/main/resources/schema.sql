create table if not exists taco_order (
    id varchar(255) primary key,
    placed_at timestamp not null
);

create table if not exists taco (
    id varchar(255) primary key,
    name varchar(50) not null,
    taco_order_id varchar(255) not null,
    created_at timestamp not null
);

create table if not exists ingredient_ref (
    taco_id varchar(255) not null,
    ingredient_id varchar(4) not null,
    primary key (taco_id, ingredient_id)
);

create table if not exists ingredient (
    id varchar(255) primary key,
    name varchar(25) not null,
    type varchar(10) not null
);

create table if not exists delivery_address (
    id varchar(255) primary key,
    name varchar(255) not null,
    street varchar(255) not null,
    city varchar(255) not null,
    state varchar(255) not null,
    zip varchar(10) not null,
    taco_order_id varchar(255) not null
);

create table if not exists payment (
    id varchar(255) primary key,
    cc_number varchar(255) not null,
    cc_expiration varchar(255) not null,
    cc_cvv varchar(255) not null,
    taco_order_id varchar(255) not null
);


alter table taco add foreign key (taco_order_id) references taco_order(id);
alter table ingredient_ref add foreign key (taco_id) references taco(id);
alter table ingredient_ref add foreign key (ingredient_id) references ingredient(id);
alter table delivery_address add foreign key (taco_order_id) references taco_order(id);
alter table payment add foreign key (taco_order_id) references taco_order(id);