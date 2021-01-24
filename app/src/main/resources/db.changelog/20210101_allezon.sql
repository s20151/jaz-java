DROP TABLE users CASCADE;
CREATE TABLE users
(
	id BIGSERIAL NOT NULL PRIMARY KEY,
	username varchar(40) not null,
	password char(60) not null,
	authorities varchar(200)
);

CREATE TABLE listing
(
    id BIGSERIAL NOT NULL PRIMARY KEY,
	user_id BIGSERIAL not null,
    title       varchar(100)   not null,
    description varchar(100)   not null,
    price       varchar(100)   not null,
    category    varchar(100)   not null,
	FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE TABLE photos
(
    listing_id BIGSERIAL not null,
	position varchar(2) not null,
	photo varchar(100) not null,
	FOREIGN KEY (listing_id) REFERENCES listing(id)
);

CREATE TABLE all_parameters
(
    id BIGSERIAL NOT NULL PRIMARY KEY,
    parameter_name varchar(100)   not null
);

CREATE TABLE listing_parameters
(
    listing_id BIGSERIAL not null,
	parameter_id BIGSERIAL not null,
	parameter_value varchar(100) not null,
	FOREIGN KEY (listing_id) REFERENCES listing(id),
	FOREIGN KEY (parameter_id) REFERENCES all_parameters(id)
);
