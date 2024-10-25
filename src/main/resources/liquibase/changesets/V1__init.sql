--
-- PostgreSQL database dump
--

-- Dumped from database version 16.3
-- Dumped by pg_dump version 16.3

-- Started on 2024-10-25 19:41:54

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'WIN1253';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- TOC entry 219 (class 1259 OID 16862)
-- Name: activity; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.activity (
    started timestamp without time zone,
    finished timestamp without time zone,
    name character varying NOT NULL,
    worker integer,
    project bigint,
    id integer NOT NULL
);


ALTER TABLE public.activity OWNER TO postgres;

--
-- TOC entry 228 (class 1259 OID 16971)
-- Name: activity_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.activity_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.activity_id_seq OWNER TO postgres;

--
-- TOC entry 4878 (class 0 OID 0)
-- Dependencies: 228
-- Name: activity_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.activity_id_seq OWNED BY public.activity.id;


--
-- TOC entry 227 (class 1259 OID 16943)
-- Name: order_user_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.order_user_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.order_user_seq OWNER TO postgres;

--
-- TOC entry 4879 (class 0 OID 0)
-- Dependencies: 227
-- Name: order_user_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.order_user_seq OWNED BY public.activity.worker;


--
-- TOC entry 216 (class 1259 OID 16816)
-- Name: participation; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.participation (
    id bigint NOT NULL,
    role bigint NOT NULL,
    worker bigint NOT NULL,
    project bigint NOT NULL
);


ALTER TABLE public.participation OWNER TO postgres;

--
-- TOC entry 218 (class 1259 OID 16838)
-- Name: project; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.project (
    id bigint NOT NULL,
    name character varying NOT NULL,
    description character varying
);


ALTER TABLE public.project OWNER TO postgres;

--
-- TOC entry 215 (class 1259 OID 16809)
-- Name: role; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.role (
    id bigint NOT NULL,
    name character varying NOT NULL,
    priority integer NOT NULL
);


ALTER TABLE public.role OWNER TO postgres;

--
-- TOC entry 220 (class 1259 OID 16869)
-- Name: token; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.token (
    id integer NOT NULL,
    token character varying NOT NULL,
    revoked boolean NOT NULL,
    expired boolean NOT NULL,
    user_id bigint NOT NULL
);


ALTER TABLE public.token OWNER TO postgres;

--
-- TOC entry 217 (class 1259 OID 16821)
-- Name: worker; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.worker (
    name character varying NOT NULL,
    lastname character varying,
    email character varying NOT NULL,
    password character varying NOT NULL,
    id integer NOT NULL,
    priority integer
);


ALTER TABLE public.worker OWNER TO postgres;

--
-- TOC entry 226 (class 1259 OID 16916)
-- Name: worker_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.worker_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.worker_id_seq OWNER TO postgres;

--
-- TOC entry 4880 (class 0 OID 0)
-- Dependencies: 226
-- Name: worker_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.worker_id_seq OWNED BY public.worker.id;


--
-- TOC entry 4710 (class 2604 OID 16944)
-- Name: activity worker; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.activity ALTER COLUMN worker SET DEFAULT nextval('public.order_user_seq'::regclass);


--
-- TOC entry 4711 (class 2604 OID 16972)
-- Name: activity id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.activity ALTER COLUMN id SET DEFAULT nextval('public.activity_id_seq'::regclass);


--
-- TOC entry 4709 (class 2604 OID 16917)
-- Name: worker id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.worker ALTER COLUMN id SET DEFAULT nextval('public.worker_id_seq'::regclass);


--
-- TOC entry 4723 (class 2606 OID 16904)
-- Name: token Token_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.token
    ADD CONSTRAINT "Token_pkey" PRIMARY KEY (id);


--
-- TOC entry 4721 (class 2606 OID 16979)
-- Name: activity activity_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.activity
    ADD CONSTRAINT activity_pkey PRIMARY KEY (id);


--
-- TOC entry 4715 (class 2606 OID 16820)
-- Name: participation participation_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.participation
    ADD CONSTRAINT participation_pkey PRIMARY KEY (id);


--
-- TOC entry 4719 (class 2606 OID 16844)
-- Name: project project_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.project
    ADD CONSTRAINT project_pkey PRIMARY KEY (id);


--
-- TOC entry 4713 (class 2606 OID 16891)
-- Name: role role_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.role
    ADD CONSTRAINT role_pkey PRIMARY KEY (id);


--
-- TOC entry 4717 (class 2606 OID 16924)
-- Name: worker worker_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.worker
    ADD CONSTRAINT worker_pkey PRIMARY KEY (id);


--
-- TOC entry 4724 (class 2606 OID 16892)
-- Name: participation participation_role_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.participation
    ADD CONSTRAINT participation_role_fkey FOREIGN KEY (role) REFERENCES public.role(id) NOT VALID;


--
-- TOC entry 4727 (class 2606 OID 16956)
-- Name: activity project_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.activity
    ADD CONSTRAINT project_fk FOREIGN KEY (project) REFERENCES public.project(id) NOT VALID;


--
-- TOC entry 4725 (class 2606 OID 16961)
-- Name: participation project_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.participation
    ADD CONSTRAINT project_fk FOREIGN KEY (project) REFERENCES public.project(id) ON UPDATE CASCADE ON DELETE CASCADE NOT VALID;


--
-- TOC entry 4729 (class 2606 OID 16936)
-- Name: token user-fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.token
    ADD CONSTRAINT "user-fk" FOREIGN KEY (user_id) REFERENCES public.worker(id) ON UPDATE CASCADE ON DELETE CASCADE NOT VALID;


--
-- TOC entry 4726 (class 2606 OID 16966)
-- Name: participation user_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.participation
    ADD CONSTRAINT user_fk FOREIGN KEY (worker) REFERENCES public.worker(id) ON UPDATE CASCADE ON DELETE CASCADE NOT VALID;


--
-- TOC entry 4728 (class 2606 OID 16951)
-- Name: activity worker_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.activity
    ADD CONSTRAINT worker_fk FOREIGN KEY (worker) REFERENCES public.worker(id) NOT VALID;


-- Completed on 2024-10-25 19:41:54

--
-- PostgreSQL database dump complete
--

