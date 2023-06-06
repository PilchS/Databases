DROP TABLE dbproject.relations;
DROP SCHEMA dbproject;

SET client_encoding = 'UTF8';

CREATE SCHEMA dbproject;

ALTER SCHEMA dbproject OWNER TO postgres;

CREATE TABLE dbproject.relations (parent character varying(255), child character varying(255));

ALTER TABLE dbproject.relations OWNER TO postgres;
