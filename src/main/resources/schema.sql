create table prefecture (
    pref_id varchar(2) primary key,
    pref_name varchar(10)
);

create table city (
    city_code varchar(6) primary key,
    pref_id varchar(2) not null,
    city_name varchar(12) default ''
);
alter table city add constraint city_fk1 foreign key (pref_id) references prefecture(pref_id);