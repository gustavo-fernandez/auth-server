CREATE TABLE user
(
  id identity,
  username varchar(20) not null,
  password varchar(20) not null
);

CREATE TABLE roles
(
  user_id bigint,
  name    varchar(20) not null
);

ALTER TABLE roles
  add foreign key (user_id) references user (id);