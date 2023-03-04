DROP TABLE IF EXISTS users_list;

CREATE TABLE users_list (
  id       serial       PRIMARY KEY,
  username varchar(32)  UNIQUE NOT NULL,
  password varchar(102) NOT NULL,
  email    varchar(40)  NOT NULL
);


DROP TABLE IF EXISTS is_day_off;

CREATE TABLE is_day_off (
  id     serial     PRIMARY KEY,
  result varchar(5) NOT NULL
);


DROP TABLE IF EXISTS cat_facts;

CREATE TABLE cat_facts (
  id     serial       PRIMARY KEY,
  length integer      NOT NULL,
  fact   varchar(100) NOT NULL
);
