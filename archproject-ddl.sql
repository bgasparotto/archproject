-- Database generated with pgModeler (PostgreSQL Database Modeler).
-- PostgreSQL version: 9.2
-- Project Site: pgmodeler.com.br
-- Model Author: ---

SET check_function_bodies = false;
-- ddl-end --

-- -- object: postgres | type: ROLE --
-- CREATE ROLE postgres WITH ;
-- -- ddl-end --
-- 

-- Database creation must be done outside an multicommand file.
-- These commands were put in this file only for convenience.
-- -- object: archproject | type: DATABASE --
-- CREATE DATABASE archproject
-- 	ENCODING = 'UTF8'
-- 	OWNER = postgres
-- ;
-- -- ddl-end --
-- 

-- object: security | type: SCHEMA --
CREATE SCHEMA security;
ALTER SCHEMA security OWNER TO postgres;
-- ddl-end --

-- object: security.user | type: TABLE --
CREATE TABLE security.user(
	id_user serial NOT NULL,
	username character varying(64) NOT NULL,
	password character varying(60) NOT NULL,
	email character varying(254) NOT NULL,
	registration_date timestamp NOT NULL DEFAULT NOW(),
	CONSTRAINT pk_user_id_user PRIMARY KEY (id_user),
	CONSTRAINT uq_user_username UNIQUE (username),
	CONSTRAINT uq_user_email UNIQUE (email)
);
-- ddl-end --

-- ddl-end --

-- object: security.role | type: TABLE --
CREATE TABLE security.role(
	id_role serial NOT NULL,
	name character varying(16) NOT NULL,
	CONSTRAINT pk_role_id_role PRIMARY KEY (id_role),
	CONSTRAINT uq_role_name UNIQUE (name)
);
-- ddl-end --

-- ddl-end --

-- object: security.user_role | type: TABLE --
CREATE TABLE security.user_role(
	id_user integer,
	id_role integer,
	CONSTRAINT pk_user_role PRIMARY KEY (id_user,id_role)
);
-- ddl-end --

-- ddl-end --

-- object: fk_user_role_user | type: CONSTRAINT --
ALTER TABLE security.user_role ADD CONSTRAINT fk_user_role_user FOREIGN KEY (id_user)
REFERENCES security.user (id_user) MATCH FULL
ON DELETE RESTRICT ON UPDATE CASCADE NOT DEFERRABLE;
-- ddl-end --


-- object: fk_user_role_role | type: CONSTRAINT --
ALTER TABLE security.user_role ADD CONSTRAINT fk_user_role_role FOREIGN KEY (id_role)
REFERENCES security.role (id_role) MATCH FULL
ON DELETE RESTRICT ON UPDATE CASCADE NOT DEFERRABLE;
-- ddl-end --



