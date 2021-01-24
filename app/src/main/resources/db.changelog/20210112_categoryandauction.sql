CREATE TABLE category
(
	id BIGSERIAL NOT NULL PRIMARY KEY,
	name varchar(40) not null
);

CREATE TABLE auction
(
    id BIGSERIAL NOT NULL PRIMARY KEY,
	user_id BIGINT not null,
	category_id    BIGINT   not null,
    title       varchar(100)   not null,
    description varchar(100)   not null,
    price       int(100)   not null,
    FOREIGN KEY (category_id) REFERENCES category(id),
	FOREIGN KEY (user_id) REFERENCES users(id)
);