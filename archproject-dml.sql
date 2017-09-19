-- Make sure to run archproject-ddl.sql before running the queries below.
-- The following queries assume you're running it on a fresh created database
-- with the serial column values starting at 1 as Postgres' default.

-- Creates the basic roles on the database. 
INSERT INTO SECURITY.ROLE (NAME) VALUES ('USER'), ('GUEST'), ('ADMIN');

-- Creates the 'admin' user with 'administrator' password's value encrypted by
-- BCrypt.
INSERT INTO SECURITY.USER
	(USERNAME, PASSWORD, EMAIL, REGISTRATION_DATE)
VALUES
	('admin', 
	 '$2a$10$fIIuYZuFBw0Krpa/E2H.9..vdGM9yelNgpluRKzq6ZYTz0H2QxtUW', 
	 'admin@domain.com', 
	 '2017-09-15 00:00:00');

-- Associates the admin user to the ADMIN role.
INSERT INTO SECURITY.USER_ROLE (ID_USER, ID_ROLE) VALUES (1, 3);