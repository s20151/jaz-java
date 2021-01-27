DROP TABLE users CASCADE;
DROP TABLE category CASCADE;
DROP TABLE section CASCADE;
DROP TABLE auction CASCADE;
DROP TABLE auction_photo CASCADE;
DROP TABLE parameter CASCADE;
DROP TABLE auction_parameter CASCADE;

CREATE TABLE users
(
	id BIGSERIAL NOT NULL PRIMARY KEY,
	username varchar(40) not null,
	password char(60) not null,
	authorities varchar(200)
);
CREATE TABLE section
(
    id BIGSERIAL NOT NULL PRIMARY KEY,
    name varchar(40) not null
);

CREATE TABLE category
(
	id BIGSERIAL NOT NULL PRIMARY KEY,
	section_id BIGINT not null,
	name varchar(40) not null,
    FOREIGN KEY (section_id) REFERENCES section(id)
);

CREATE TABLE auction
(
    id BIGSERIAL NOT NULL PRIMARY KEY,
	creator_id BIGINT not null,
	category_id    BIGINT   not null,
    title       varchar(100)   not null,
    description varchar(100)   not null,
    price       int   not null,
    version       int   not null,
    FOREIGN KEY (category_id) REFERENCES category(id),
	FOREIGN KEY (creator_id) REFERENCES users(id)
);

CREATE TABLE auction_photo
(
    id BIGSERIAL NOT NULL PRIMARY KEY,
    auction_id BIGINT not null,
	position int not null,
	link varchar(100) not null,
	FOREIGN KEY (auction_id) REFERENCES auction(id)
);

CREATE TABLE parameter
(
    id BIGSERIAL NOT NULL PRIMARY KEY,
    name varchar(100)   not null
);

CREATE TABLE auction_parameter
(
    auction_id BIGINT not null,
	parameter_id BIGINT not null,
	value varchar(100) not null,
	FOREIGN KEY (auction_id) REFERENCES auction(id),
	FOREIGN KEY (parameter_id) REFERENCES parameter(id)
);