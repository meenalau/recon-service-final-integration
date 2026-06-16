--
-- PostgreSQL database dump
--

\restrict xdGXoW0JUn9RgrrOsFmeKydvaX5doTZ91oFrSebw0KRjyd50b0ALHv1PEUXwUzN

-- Dumped from database version 18.3
-- Dumped by pg_dump version 18.3

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET transaction_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- Name: capital_markets; Type: SCHEMA; Schema: -; Owner: postgres
--

CREATE SCHEMA capital_markets;


ALTER SCHEMA capital_markets OWNER TO postgres;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: audit_log; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.audit_log (
    audit_id bigint NOT NULL,
    entity_id bigint NOT NULL,
    entity_name character varying(50),
    action_type character varying(20),
    created_at timestamp without time zone
);


ALTER TABLE public.audit_log OWNER TO postgres;

--
-- Name: counterparties; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.counterparties (
    counterparty_id bigint NOT NULL,
    counterparty_name character varying(30) NOT NULL,
    country character varying(15) NOT NULL,
    counterparty_type character varying(10) NOT NULL,
    lei character varying(10) NOT NULL,
    bic character varying(11) NOT NULL,
    CONSTRAINT chk_counterparty_type CHECK (((counterparty_type)::text = ANY ((ARRAY['BANK'::character varying, 'BROKER'::character varying, 'FUND'::character varying])::text[])))
);


ALTER TABLE public.counterparties OWNER TO postgres;

--
-- Name: databasechangelog; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.databasechangelog (
    id character varying(255) NOT NULL,
    author character varying(255) NOT NULL,
    filename character varying(255) NOT NULL,
    dateexecuted timestamp without time zone NOT NULL,
    orderexecuted integer NOT NULL,
    exectype character varying(10) NOT NULL,
    md5sum character varying(35),
    description character varying(255),
    comments character varying(255),
    tag character varying(255),
    liquibase character varying(20),
    contexts character varying(255),
    labels character varying(255),
    deployment_id character varying(10)
);


ALTER TABLE public.databasechangelog OWNER TO postgres;

--
-- Name: databasechangeloglock; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.databasechangeloglock (
    id integer NOT NULL,
    locked boolean NOT NULL,
    lockgranted timestamp without time zone,
    lockedby character varying(255)
);


ALTER TABLE public.databasechangeloglock OWNER TO postgres;

--
-- Name: instruments; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.instruments (
    instrument_id bigint NOT NULL,
    isin character varying(15) NOT NULL,
    instrument_name character varying(20) NOT NULL,
    asset_class character varying(10) NOT NULL,
    currency character(3) NOT NULL,
    issuer character varying(20) NOT NULL,
    coupon_rate numeric(5,2),
    maturity_date date,
    CONSTRAINT chk_asset_class CHECK (((asset_class)::text = ANY ((ARRAY['BOND'::character varying, 'FX'::character varying, 'EQUITY'::character varying])::text[])))
);


ALTER TABLE public.instruments OWNER TO postgres;

--
-- Name: instruments_instrument_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

ALTER TABLE public.instruments ALTER COLUMN instrument_id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.instruments_instrument_id_seq
    START WITH 101
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- Name: recon_breaks; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.recon_breaks (
    break_id character varying(15) NOT NULL,
    trade_id bigint NOT NULL,
    break_date date NOT NULL,
    break_type character varying(20) NOT NULL,
    our_qty numeric(18,2) NOT NULL,
    their_qty numeric(18,2) NOT NULL,
    our_amount numeric(20,2) NOT NULL,
    their_amount numeric(20,2) NOT NULL,
    currency character varying(5) NOT NULL,
    status character varying(15) NOT NULL,
    resolved_by character varying(100),
    CONSTRAINT chk_break_type CHECK (((break_type)::text = ANY ((ARRAY['QTY_MISMATCH'::character varying, 'AMOUNT_MISMATCH'::character varying, 'BOTH_MISMATCH'::character varying, 'MISSING_TRADE'::character varying])::text[]))),
    CONSTRAINT chk_recon_status CHECK (((status)::text = ANY ((ARRAY['OPEN'::character varying, 'UNDER_REVIEW'::character varying, 'ESCALATED'::character varying, 'RESOLVED'::character varying])::text[])))
);


ALTER TABLE public.recon_breaks OWNER TO postgres;

--
-- Name: settlements; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.settlements (
    settlement_id character varying(15) NOT NULL,
    trade_id bigint NOT NULL,
    expected_date date NOT NULL,
    actual_date date,
    custodian_ref character varying(20) NOT NULL,
    status character varying(15) NOT NULL,
    settled_amount numeric(20,2),
    settlement_currency character varying(5) NOT NULL,
    failure_reason character varying(100),
    CONSTRAINT chk_actual_date CHECK (((actual_date IS NULL) OR (actual_date >= expected_date))),
    CONSTRAINT chk_settlement_status CHECK (((status)::text = ANY ((ARRAY['PENDING'::character varying, 'IN_PROGRESS'::character varying, 'SETTLED'::character varying, 'FAILED'::character varying, 'CANCELLED'::character varying])::text[])))
);


ALTER TABLE public.settlements OWNER TO postgres;

--
-- Name: trades; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.trades (
    trade_id bigint NOT NULL,
    trader_name character varying(15) NOT NULL,
    trading_desk character varying(15) NOT NULL,
    instrument_id bigint NOT NULL,
    counterparty_id bigint NOT NULL,
    trade_type character varying(10) NOT NULL,
    quantity numeric(18,2) NOT NULL,
    price numeric(18,4) NOT NULL,
    trade_reference character varying(15) NOT NULL,
    status character varying(10) NOT NULL,
    trade_timestamp timestamp without time zone NOT NULL,
    CONSTRAINT chk_trade_status CHECK (((status)::text = ANY ((ARRAY['EXECUTED'::character varying, 'CONFIRMED'::character varying, 'MATCHED'::character varying, 'SETTLED'::character varying, 'CUSTODY'::character varying])::text[]))),
    CONSTRAINT chk_trade_type CHECK (((trade_type)::text = ANY ((ARRAY['BUY'::character varying, 'SELL'::character varying])::text[])))
);


ALTER TABLE public.trades OWNER TO postgres;

--
-- Name: trades_trade_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.trades_trade_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.trades_trade_id_seq OWNER TO postgres;

--
-- Name: trades_trade_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.trades_trade_id_seq OWNED BY public.trades.trade_id;


--
-- Name: trades trade_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.trades ALTER COLUMN trade_id SET DEFAULT nextval('public.trades_trade_id_seq'::regclass);


--
-- Data for Name: audit_log; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.audit_log (audit_id, entity_id, entity_name, action_type, created_at) FROM stdin;
\.


--
-- Data for Name: counterparties; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.counterparties (counterparty_id, counterparty_name, country, counterparty_type, lei, bic) FROM stdin;
10011	Deutsche Bank India	INDIA	BANK	Y0LT52	DEUTINBB
10012	ICICI Securities	INDIA	BROKER	3Z9A42	ICBRINB1
10013	National Australia Bank	AUSTRALIA	BANK	EH3Z21	NATAAU33
10014	SBI Mutual Fund	INDIA	FUND	2QSS41	SBININBB
\.


--
-- Data for Name: databasechangelog; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.databasechangelog (id, author, filename, dateexecuted, orderexecuted, exectype, md5sum, description, comments, tag, liquibase, contexts, labels, deployment_id) FROM stdin;
2	meenal	db/changelog/db.changelog-master.yaml	2026-05-30 20:12:19.868183	1	EXECUTED	9:e2ddec2efd22ddde7429e0635269dcda	addColumn tableName=recon_breaks		\N	5.0.2	\N	\N	0152137264
3	meenal	db/changelog/db.changelog-master.yaml	2026-05-31 12:09:50.060761	2	EXECUTED	9:d800752288cc132ca84c43c9d159de56	createTable tableName=audit_log		\N	5.0.2	\N	\N	0209586791
\.


--
-- Data for Name: databasechangeloglock; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.databasechangeloglock (id, locked, lockgranted, lockedby) FROM stdin;
1	f	\N	\N
\.


--
-- Data for Name: instruments; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.instruments (instrument_id, isin, instrument_name, asset_class, currency, issuer, coupon_rate, maturity_date) FROM stdin;
101	INDGOV2035	Govt Bond 2035	BOND	INR	Govt of India	7.10	2035-08-15
102	HDFCBOND29	HDFC Bond 2029	BOND	INR	HDFC Bank	6.85	2029-03-10
103	USDINRSPOT01	USDINR Spot	FX	USD	FX Market	\N	\N
104	RELIANCEEQ01	Reliance Equity	EQUITY	INR	Reliance	\N	\N
105	AUSGOV2032	AUS Bond 2032	BOND	AUD	Aus Govt	4.25	2032-11-21
\.


--
-- Data for Name: recon_breaks; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.recon_breaks (break_id, trade_id, break_date, break_type, our_qty, their_qty, our_amount, their_amount, currency, status, resolved_by) FROM stdin;
BRK2026001	1	2026-01-11	QTY_MISMATCH	1000.00	900.00	98500.00	98500.00	USD	OPEN	\N
BRK2026002	2	2026-01-12	BOTH_MISMATCH	500.00	480.00	51125.00	48960.00	USD	RESOLVED	ops_team
BRK2026003	3	2026-01-13	MISSING_TRADE	750.00	0.00	74812.50	0.00	USD	RESOLVED	ops_team
BRK2026004	4	2026-01-14	MISSING_TRADE	200.00	200.00	30000.00	0.00	USD	ESCALATED	senior_ops
BRK2026005	5	2026-01-15	AMOUNT_MISMATCH	300.00	300.00	325.50	318.00	USD	ESCALATED	senior_ops
BRK2026006	6	2026-01-15	AMOUNT_MISMATCH	400.00	400.00	432.80	132.80	USD	RESOLVED	ops_team
\.


--
-- Data for Name: settlements; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.settlements (settlement_id, trade_id, expected_date, actual_date, custodian_ref, status, settled_amount, settlement_currency, failure_reason) FROM stdin;
SET2026001	1	2026-01-13	\N	CUST-REF-0001	PENDING	\N	USD	\N
SET2026002	2	2026-01-13	\N	CUST-REF-0002	FAILED	\N	USD	\N
SET2026003	3	2026-01-14	2026-01-14	CUST-REF-0003	SETTLED	74812.50	USD	\N
SET2026004	4	2026-01-15	\N	CUST-REF-0004	CANCELLED	\N	USD	cancelled by counterparty
SET2026005	5	2026-01-15	\N	CUST-REF-0005	IN_PROGRESS	\N	USD	\N
SET2026006	6	2026-01-15	\N	CUST-REF-0006	SETTLED	\N	USD	\N
\.


--
-- Data for Name: trades; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.trades (trade_id, trader_name, trading_desk, instrument_id, counterparty_id, trade_type, quantity, price, trade_reference, status, trade_timestamp) FROM stdin;
1	Farhan	Fixed Income	101	10011	BUY	1000.00	98.5000	TRD2026001	EXECUTED	2026-01-10 09:00:00
2	Raju	Fixed Income	102	10012	SELL	500.00	102.2500	TRD2026002	MATCHED	2026-01-11 10:30:00
3	Rancho	Fixed Income	105	10013	BUY	750.00	99.7500	TRD2026003	SETTLED	2026-01-12 11:00:00
4	Virus	Equities	104	10014	BUY	200.00	150.0000	TRD2026004	CONFIRMED	2026-01-13 09:45:00
5	Virat	FX Desk	103	10013	BUY	300.00	1.0850	TRD2026005	CONFIRMED	2026-01-14 08:15:00
6	Anushka	FX Desk	103	10012	SELL	400.00	1.0820	TRD2026008	CONFIRMED	2026-01-14 08:30:00
\.


--
-- Name: instruments_instrument_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.instruments_instrument_id_seq', 105, true);


--
-- Name: trades_trade_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.trades_trade_id_seq', 27, true);


--
-- Name: audit_log audit_log_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.audit_log
    ADD CONSTRAINT audit_log_pkey PRIMARY KEY (audit_id);


--
-- Name: counterparties counterparties_bic_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.counterparties
    ADD CONSTRAINT counterparties_bic_key UNIQUE (bic);


--
-- Name: counterparties counterparties_lei_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.counterparties
    ADD CONSTRAINT counterparties_lei_key UNIQUE (lei);


--
-- Name: counterparties counterparties_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.counterparties
    ADD CONSTRAINT counterparties_pkey PRIMARY KEY (counterparty_id);


--
-- Name: databasechangeloglock databasechangeloglock_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.databasechangeloglock
    ADD CONSTRAINT databasechangeloglock_pkey PRIMARY KEY (id);


--
-- Name: instruments instruments_isin_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.instruments
    ADD CONSTRAINT instruments_isin_key UNIQUE (isin);


--
-- Name: instruments instruments_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.instruments
    ADD CONSTRAINT instruments_pkey PRIMARY KEY (instrument_id);


--
-- Name: recon_breaks recon_breaks_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.recon_breaks
    ADD CONSTRAINT recon_breaks_pkey PRIMARY KEY (break_id);


--
-- Name: settlements settlements_custodian_ref_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.settlements
    ADD CONSTRAINT settlements_custodian_ref_key UNIQUE (custodian_ref);


--
-- Name: settlements settlements_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.settlements
    ADD CONSTRAINT settlements_pkey PRIMARY KEY (settlement_id);


--
-- Name: trades trades_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.trades
    ADD CONSTRAINT trades_pkey PRIMARY KEY (trade_id);


--
-- Name: trades trades_trade_reference_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.trades
    ADD CONSTRAINT trades_trade_reference_key UNIQUE (trade_reference);


--
-- Name: trades fk_trade_counterparty; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.trades
    ADD CONSTRAINT fk_trade_counterparty FOREIGN KEY (counterparty_id) REFERENCES public.counterparties(counterparty_id);


--
-- Name: trades fk_trade_instrument; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.trades
    ADD CONSTRAINT fk_trade_instrument FOREIGN KEY (instrument_id) REFERENCES public.instruments(instrument_id);


--
-- PostgreSQL database dump complete
--

\unrestrict xdGXoW0JUn9RgrrOsFmeKydvaX5doTZ91oFrSebw0KRjyd50b0ALHv1PEUXwUzN

