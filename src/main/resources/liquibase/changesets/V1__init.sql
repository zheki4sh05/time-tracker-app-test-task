
--

CREATE SCHEMA IF NOT EXISTS public AUTHORIZATION postgres;


ALTER SCHEMA public OWNER TO pg_database_owner;

--
-- TOC entry 4894 (class 0 OID 0)
-- Dependencies: 4
-- Name: SCHEMA public; Type: COMMENT; Schema: -; Owner: pg_database_owner
--

COMMENT ON SCHEMA public IS 'standard public schema';

--
-- TOC entry 222 (class 1259 OID 17163)
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
-- TOC entry 223 (class 1259 OID 17168)
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
-- TOC entry 4895 (class 0 OID 0)
-- Dependencies: 223
-- Name: activity_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.activity_id_seq OWNED BY public.activity.id;


--
-- TOC entry 220 (class 1259 OID 17153)
-- Name: databasechangelog; Type: TABLE; Schema: public; Owner: postgres
--


--
-- TOC entry 224 (class 1259 OID 17169)
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
-- TOC entry 4896 (class 0 OID 0)
-- Dependencies: 224
-- Name: order_user_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.order_user_seq OWNED BY public.activity.worker;


--
-- TOC entry 225 (class 1259 OID 17170)
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
-- TOC entry 215 (class 1259 OID 16911)
-- Name: participation_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.participation_seq
    START WITH 1
    INCREMENT BY 50
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.participation_seq OWNER TO postgres;

--
-- TOC entry 226 (class 1259 OID 17173)
-- Name: project; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.project (
    id bigint NOT NULL,
    name character varying NOT NULL,
    description character varying
);


ALTER TABLE public.project OWNER TO postgres;

--
-- TOC entry 216 (class 1259 OID 16912)
-- Name: project_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.project_seq
    START WITH 1
    INCREMENT BY 50
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.project_seq OWNER TO postgres;

--
-- TOC entry 227 (class 1259 OID 17178)
-- Name: role; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.role (
    id bigint NOT NULL,
    name character varying NOT NULL,
    priority integer NOT NULL
);


ALTER TABLE public.role OWNER TO postgres;

--
-- TOC entry 217 (class 1259 OID 16913)
-- Name: role_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.role_seq
    START WITH 1
    INCREMENT BY 50
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.role_seq OWNER TO postgres;

--
-- TOC entry 228 (class 1259 OID 17183)
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
-- TOC entry 218 (class 1259 OID 16914)
-- Name: token_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.token_seq
    START WITH 1
    INCREMENT BY 50
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.token_seq OWNER TO postgres;

--
-- TOC entry 219 (class 1259 OID 16915)
-- Name: user_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.user_seq
    START WITH 1
    INCREMENT BY 50
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.user_seq OWNER TO postgres;

--
-- TOC entry 229 (class 1259 OID 17188)
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
-- TOC entry 230 (class 1259 OID 17193)
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
-- TOC entry 4897 (class 0 OID 0)
-- Dependencies: 230
-- Name: worker_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.worker_id_seq OWNED BY public.worker.id;


--
-- TOC entry 4723 (class 2604 OID 17194)
-- Name: activity worker; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.activity ALTER COLUMN worker SET DEFAULT nextval('public.order_user_seq'::regclass);


--
-- TOC entry 4724 (class 2604 OID 17195)
-- Name: activity id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.activity ALTER COLUMN id SET DEFAULT nextval('public.activity_id_seq'::regclass);


--
-- TOC entry 4725 (class 2604 OID 17196)
-- Name: worker id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.worker ALTER COLUMN id SET DEFAULT nextval('public.worker_id_seq'::regclass);


--
-- TOC entry 4737 (class 2606 OID 17198)
-- Name: token Token_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.token
    ADD CONSTRAINT "Token_pkey" PRIMARY KEY (id);


--
-- TOC entry 4729 (class 2606 OID 17200)
-- Name: activity activity_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.activity
    ADD CONSTRAINT activity_pkey PRIMARY KEY (id);


--
-- TOC entry 4727 (class 2606 OID 17162)
-- Name: databasechangeloglock databasechangeloglock_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--




--
-- TOC entry 4731 (class 2606 OID 17202)
-- Name: participation participation_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.participation
    ADD CONSTRAINT participation_pkey PRIMARY KEY (id);


--
-- TOC entry 4733 (class 2606 OID 17204)
-- Name: project project_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.project
    ADD CONSTRAINT project_pkey PRIMARY KEY (id);


--
-- TOC entry 4735 (class 2606 OID 17206)
-- Name: role role_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.role
    ADD CONSTRAINT role_pkey PRIMARY KEY (id);


--
-- TOC entry 4739 (class 2606 OID 17208)
-- Name: worker worker_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.worker
    ADD CONSTRAINT worker_pkey PRIMARY KEY (id);


--
-- TOC entry 4742 (class 2606 OID 17209)
-- Name: participation participation_role_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.participation
    ADD CONSTRAINT participation_role_fkey FOREIGN KEY (role) REFERENCES public.role(id) NOT VALID;


--
-- TOC entry 4740 (class 2606 OID 17214)
-- Name: activity project_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.activity
    ADD CONSTRAINT project_fk FOREIGN KEY (project) REFERENCES public.project(id) NOT VALID;


--
-- TOC entry 4743 (class 2606 OID 17219)
-- Name: participation project_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.participation
    ADD CONSTRAINT project_fk FOREIGN KEY (project) REFERENCES public.project(id) ON UPDATE CASCADE ON DELETE CASCADE NOT VALID;


--
-- TOC entry 4745 (class 2606 OID 17224)
-- Name: token user-fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.token
    ADD CONSTRAINT "user-fk" FOREIGN KEY (user_id) REFERENCES public.worker(id) ON UPDATE CASCADE ON DELETE CASCADE NOT VALID;


--
-- TOC entry 4744 (class 2606 OID 17229)
-- Name: participation user_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.participation
    ADD CONSTRAINT user_fk FOREIGN KEY (worker) REFERENCES public.worker(id) ON UPDATE CASCADE ON DELETE CASCADE NOT VALID;


--
-- TOC entry 4741 (class 2606 OID 17234)
-- Name: activity worker_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.activity
    ADD CONSTRAINT worker_fk FOREIGN KEY (worker) REFERENCES public.worker(id) NOT VALID;


-- Completed on 2024-10-26 15:20:49

--
-- PostgreSQL database dump complete
--

