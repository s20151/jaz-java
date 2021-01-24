CREATE TABLE users
(
	id BIGSERIAL NOT NULL PRIMARY KEY,
	username varchar(40) not null,
	password char(60) not null,
	authorities varchar(200)
);