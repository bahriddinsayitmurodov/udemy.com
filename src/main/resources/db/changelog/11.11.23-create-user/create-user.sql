/*create type user_type as enum('ADMIN','STUDENT, ASSISTANT', 'MENTOR');*/

create table "users"(
    id uuid  primary key ,
    first_name varchar(20) not null ,
    middle_name varchar(20) ,
    last_name varchar(20) not null ,
    user_type varchar not null ,
    phone_number varchar(20) not null unique,
    is_phone_number_verified boolean  default false,
    email varchar(320) not null unique,
    sms_code INT,
    password varchar(128) not null
);


create table "group"(
     id uuid  primary key ,
     name varchar(50) not null ,
     group_type varchar not null
);
