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
	CONSTRAINT pk_user_id_user PRIMARY KEY (id_user),
	CONSTRAINT uq_user_username UNIQUE (username),
	CONSTRAINT uq_user_email UNIQUE (email)
);
-- ddl-end --

-- ddl-end --


