drop table if exists user;
create table user (
  id bigint auto_increment primary key,
  username varchar(64) not null,
  `password` varchar(128),
  name varchar(64),
  res_address_id bigint comment 'residence address reference table address',
  unique key uq_username(username)
) engine = 'InnoDB' default charset 'UTF8MB4' comment 'user';

DROP  table if exists address;
create table address (
  id bigint auto_increment primary key,
  country varchar(128),
  province varchar(128),
  city varchar(128),
  region varchar(128),
  street varchar(128),
  zip_code varchar(128),
  details varchar(256)
) engine = 'InnoDB' default charset 'UTF8MB4' comment 'address';