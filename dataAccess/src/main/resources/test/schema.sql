CREATE TABLE public.certificates (
    certificate_id bigint NOT NULL,
    name text DEFAULT ''::text NOT NULL,
    description text DEFAULT ''::text NOT NULL,
    price numeric(8,2) DEFAULT 0 NOT NULL,
    duration smallint DEFAULT 0 NOT NULL,
    create_date timestamp with time zone DEFAULT CURRENT_TIMESTAMP,
    update_date timestamp with time zone DEFAULT CURRENT_TIMESTAMP
);


--
-- TOC entry 209 (class 1259 OID 16395)
-- Name: certificates_certificate_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

ALTER TABLE public.certificates ALTER COLUMN certificate_id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.certificates_certificate_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- TOC entry 213 (class 1259 OID 16418)
-- Name: certificates_tags; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.certificates_tags (
    certificate_id bigint NOT NULL,
    tag_id bigint NOT NULL
);


--
-- TOC entry 217 (class 1259 OID 16445)
-- Name: orders; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.orders (
    order_id bigint NOT NULL,
    user_id bigint NOT NULL,
    certificate_id bigint NOT NULL,
    purchase_timestamp timestamp with time zone DEFAULT CURRENT_TIMESTAMP NOT NULL,
    purchase_price numeric(8,2) NOT NULL,
    create_date timestamp with time zone DEFAULT CURRENT_TIMESTAMP,
    update_date timestamp with time zone DEFAULT CURRENT_TIMESTAMP
);


--
-- TOC entry 216 (class 1259 OID 16444)
-- Name: orders_order_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

ALTER TABLE public.orders ALTER COLUMN order_id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.orders_order_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- TOC entry 212 (class 1259 OID 16410)
-- Name: tags; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.tags (
    tag_id bigint NOT NULL,
    name text DEFAULT ''::text NOT NULL
);


--
-- TOC entry 211 (class 1259 OID 16409)
-- Name: tags_tag_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

ALTER TABLE public.tags ALTER COLUMN tag_id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.tags_tag_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- TOC entry 219 (class 1259 OID 16483)
-- Name: user_roles; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.user_roles (
    role_id smallint NOT NULL,
    name text NOT NULL
);


--
-- TOC entry 218 (class 1259 OID 16482)
-- Name: user_roles_role_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

ALTER TABLE public.user_roles ALTER COLUMN role_id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.user_roles_role_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- TOC entry 215 (class 1259 OID 16435)
-- Name: users; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.users (
    user_id bigint NOT NULL,
    username text NOT NULL,
    password text DEFAULT 'DEFAULT'::text NOT NULL,
    role_id smallint DEFAULT 2 NOT NULL
);


--
-- TOC entry 214 (class 1259 OID 16434)
-- Name: users_user_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

ALTER TABLE public.users ALTER COLUMN user_id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.users_user_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- TOC entry 3207 (class 2606 OID 16443)
-- Name: users Username; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT "Username" UNIQUE (username);


--
-- TOC entry 3201 (class 2606 OID 16408)
-- Name: certificates certificates_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.certificates
    ADD CONSTRAINT certificates_pkey PRIMARY KEY (certificate_id);


--
-- TOC entry 3205 (class 2606 OID 16422)
-- Name: certificates_tags certificates_tags_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.certificates_tags
    ADD CONSTRAINT certificates_tags_pkey PRIMARY KEY (certificate_id, tag_id);


--
-- TOC entry 3211 (class 2606 OID 16450)
-- Name: orders orders_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.orders
    ADD CONSTRAINT orders_pkey PRIMARY KEY (order_id);


--
-- TOC entry 3203 (class 2606 OID 16417)
-- Name: tags tags_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.tags
    ADD CONSTRAINT tags_pkey PRIMARY KEY (tag_id);


--
-- TOC entry 3213 (class 2606 OID 16489)
-- Name: user_roles user_roles_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.user_roles
    ADD CONSTRAINT user_roles_pkey PRIMARY KEY (role_id);


--
-- TOC entry 3209 (class 2606 OID 16441)
-- Name: users users_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_pkey PRIMARY KEY (user_id);


--
-- TOC entry 3216 (class 2606 OID 16491)
-- Name: users User role; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT "User role" FOREIGN KEY (role_id) REFERENCES public.user_roles(role_id) NOT VALID;


--
-- TOC entry 3218 (class 2606 OID 16456)
-- Name: orders certificate_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.orders
    ADD CONSTRAINT certificate_id FOREIGN KEY (certificate_id) REFERENCES public.certificates(certificate_id);


--
-- TOC entry 3214 (class 2606 OID 16423)
-- Name: certificates_tags certificates_tags_certificate_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.certificates_tags
    ADD CONSTRAINT certificates_tags_certificate_id_fkey FOREIGN KEY (certificate_id) REFERENCES public.certificates(certificate_id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- TOC entry 3215 (class 2606 OID 16428)
-- Name: certificates_tags certificates_tags_tag_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.certificates_tags
    ADD CONSTRAINT certificates_tags_tag_id_fkey FOREIGN KEY (tag_id) REFERENCES public.tags(tag_id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- TOC entry 3217 (class 2606 OID 16451)
-- Name: orders user_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.orders
    ADD CONSTRAINT user_id FOREIGN KEY (user_id) REFERENCES public.users(user_id) ON UPDATE CASCADE;


-- Completed on 2021-11-29 07:43:08

--
-- PostgreSQL database dump complete
--

