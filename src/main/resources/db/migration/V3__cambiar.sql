CREATE TABLE authors(
                        id serial PRIMARY KEY,
                        first_name varchar(255),
                        last_name varchar(255)
);


ALTER TABLE books
DROP COLUMN author,
	ADD COLUMN author_id int,
	ADD constraint fk_authors
	foreign key (author_id)
	references authors(id);