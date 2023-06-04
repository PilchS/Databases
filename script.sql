DROP TABLE dbproject.nodes;
DROP TABLE dbproject.relations;
DROP SCHEMA dbproject;

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;
SET default_tablespace = '';
SET default_table_access_method = heap;


CREATE SCHEMA dbproject;
ALTER SCHEMA dbproject OWNER TO postgres;


CREATE TABLE dbproject.nodes (
    id_node integer NOT NULL,
    node character varying(255)
);

ALTER TABLE dbproject.nodes OWNER TO postgres;

--
-- Name: nodes_id_seq; Type: SEQUENCE; Schema: dbproject; Owner: postgres
--

CREATE SEQUENCE dbproject.nodes_id_node_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE dbproject.nodes_id_node_seq OWNER TO postgres;

--
-- Name: nodes_id_seq; Type: SEQUENCE OWNED BY; Schema: dbproject; Owner: postgres
--

ALTER SEQUENCE dbproject.nodes_id_node_seq OWNED BY dbproject.nodes.id_node;


--
-- Name: relations; Type: TABLE; Schema: dbproject; Owner: postgres
--

CREATE TABLE dbproject.relations (
    id_relation integer NOT NULL,
    parent character varying(255),
    child character varying(255)
);


ALTER TABLE dbproject.relations OWNER TO postgres;

--
-- Name: relations_id_seq; Type: SEQUENCE; Schema: dbproject; Owner: postgres
--

CREATE SEQUENCE dbproject.relations_id_relation_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE dbproject.relations_id_relation_seq OWNER TO postgres;

--
-- Name: relations_id_seq; Type: SEQUENCE OWNED BY; Schema: dbproject; Owner: postgres
--

ALTER SEQUENCE dbproject.relations_id_relation_seq OWNED BY dbproject.relations.id_relation;


--
-- Data for Name: nodes; Type: TABLE DATA; Schema: dbproject; Owner: postgres
--

ALTER TABLE ONLY dbproject.nodes ALTER COLUMN id_node SET DEFAULT nextval('dbproject.nodes_id_node_seq'::regclass);

ALTER TABLE ONLY dbproject.relations ALTER COLUMN id_relation SET DEFAULT nextval('dbproject.relations_id_relation_seq'::regclass);

COPY dbproject.nodes (id_node, node) FROM stdin;
\.


--
-- Data for Name: relations; Type: TABLE DATA; Schema: dbproject; Owner: postgres
--

COPY dbproject.relations (id_relation, parent, child) FROM stdin;
\.


--
-- Name: nodes_id_seq; Type: SEQUENCE SET; Schema: dbproject; Owner: postgres
--

SELECT pg_catalog.setval('dbproject.nodes_id_node_seq', 1, false);


--
-- Name: relations_id_seq; Type: SEQUENCE SET; Schema: dbproject; Owner: postgres
--

SELECT pg_catalog.setval('dbproject.relations_id_relation_seq', 1, false);


--
-- Name: nodes nodes_pkey; Type: CONSTRAINT; Schema: dbproject; Owner: postgres
--

ALTER TABLE ONLY dbproject.nodes
    ADD CONSTRAINT nodes_pkey PRIMARY KEY (id_node);


--
-- Name: relations relations_pkey; Type: CONSTRAINT; Schema: dbproject; Owner: postgres
--

ALTER TABLE ONLY dbproject.relations
    ADD CONSTRAINT relations_pkey PRIMARY KEY (id_relation);


--
-- PostgreSQL database dump complete
--

