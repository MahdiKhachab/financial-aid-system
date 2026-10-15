

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET client_min_messages = warning;
SET row_security = off;


CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;



COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


SET default_tablespace = '';

SET default_with_oids = false;


CREATE TABLE public.attachment (
    id bigint NOT NULL,
    lastupdate timestamp without time zone,
    contenttype character varying(255),
    creationdate timestamp without time zone,
    data bytea,
    filename character varying(255),
    notes character varying(255),
    size bigint,
    version integer,
    mediator_id bigint,
    needdetails_id bigint,
    purchasevoucher_id bigint
);



CREATE TABLE public.cfg_company (
    id bigint NOT NULL,
    lastupdate timestamp without time zone,
    creationdate timestamp without time zone,
    name character varying(255),
    version integer
);



CREATE TABLE public.cfg_healthystatus (
    id bigint NOT NULL,
    lastupdate timestamp without time zone,
    creationdate timestamp without time zone,
    status character varying(255),
    version integer
);



CREATE TABLE public.cfg_inssurance (
    id bigint NOT NULL,
    lastupdate timestamp without time zone,
    creationdate timestamp without time zone,
    name character varying(255),
    version integer
);



CREATE TABLE public.cfg_poorpercentage (
    id bigint NOT NULL,
    lastupdate timestamp without time zone,
    creationdate timestamp without time zone,
    poordescription character varying(255),
    poorfrom integer,
    poorto integer,
    version integer
);



CREATE TABLE public.cfg_religionreference (
    id bigint NOT NULL,
    lastupdate timestamp without time zone,
    creationdate timestamp without time zone,
    name character varying(255),
    version integer
);



CREATE TABLE public.cfg_submission (
    id bigint NOT NULL,
    lastupdate timestamp without time zone,
    creationdate timestamp without time zone,
    name character varying(255),
    version integer
);



CREATE TABLE public.cfg_submissiondetails (
    id bigint NOT NULL,
    lastupdate timestamp without time zone,
    balance_dd double precision,
    balance_ll double precision,
    creationdate timestamp without time zone,
    type character varying(255),
    version integer,
    submission_id bigint
);



CREATE TABLE public.deadneed (
    id bigint NOT NULL,
    lastupdate timestamp without time zone,
    amount double precision,
    count integer,
    creationdate timestamp without time zone,
    currency integer,
    finished boolean,
    needtype integer,
    unitcount integer,
    version integer,
    deadperson_id bigint,
    service_id bigint
);



CREATE TABLE public.deadperson (
    id bigint NOT NULL,
    lastupdate timestamp without time zone,
    creationdate timestamp without time zone,
    deadmothername character varying(255),
    deadname character varying(255),
    delegator character varying(255),
    delegatorphone character varying(255),
    version integer
);



CREATE TABLE public.deserved (
    id bigint NOT NULL,
    lastupdate timestamp without time zone,
    creationdate timestamp without time zone,
    fullname character varying(255),
    hascar boolean,
    knownby character varying(255),
    livingarea character varying(255),
    nbofexpatiatechildren integer,
    nbofindividuals integer,
    notes character varying(255),
    persongenre character varying(255),
    phoneno character varying(255),
    richlogical boolean,
    salary double precision,
    version integer,
    working boolean,
    healthystatus_id bigint,
    inssurance_id bigint,
    poorpercentage_id bigint,
    religionreference_id bigint
);



CREATE TABLE public.mediator (
    id bigint NOT NULL,
    lastupdate timestamp without time zone,
    ammount double precision,
    creationdate timestamp without time zone,
    currency character varying(255),
    delegatename character varying(255),
    delivered boolean,
    moneytype character varying(255),
    phoneno character varying(255),
    receivedate date,
    receiverofcompany character varying(255),
    version integer,
    sponsor_id bigint,
    company_id bigint
);



CREATE TABLE public.needdetails (
    id bigint NOT NULL,
    lastupdate timestamp without time zone,
    cost double precision,
    count integer,
    creationdate timestamp without time zone,
    date date,
    finished boolean,
    needtype integer,
    notes character varying(255),
    receivedcost double precision,
    unitcount integer,
    version integer,
    deadneed_id bigint,
    worker_id bigint
);



CREATE TABLE public.provideaid (
    id bigint NOT NULL,
    lastupdate timestamp without time zone,
    creationdate timestamp without time zone,
    currencyenum character varying(255),
    deliverydate timestamp without time zone,
    note character varying(255),
    value double precision,
    version integer,
    submissiondetailscfg_id bigint
);



CREATE TABLE public.provideaidcompany (
    id bigint NOT NULL,
    lastupdate timestamp without time zone,
    creationdate timestamp without time zone,
    currencyenum character varying(255),
    delegate character varying(255),
    deliverydate timestamp without time zone,
    note character varying(255),
    value double precision,
    version integer,
    submissiondetailscfg_id bigint,
    company_id bigint
);



CREATE TABLE public.provideaiddesrvedperson (
    id bigint NOT NULL,
    lastupdate timestamp without time zone,
    creationdate timestamp without time zone,
    currencyenum character varying(255),
    deliverydate timestamp without time zone,
    note character varying(255),
    value double precision,
    version integer,
    submissiondetailscfg_id bigint,
    desrvedperson_id bigint
);



CREATE TABLE public.provideaidrabet (
    id bigint NOT NULL,
    lastupdate timestamp without time zone,
    creationdate timestamp without time zone,
    currencyenum character varying(255),
    delagate character varying(255),
    deliverydate timestamp without time zone,
    note character varying(255),
    rabetname character varying(255),
    value double precision,
    version integer,
    submissiondetailscfg_id bigint
);



CREATE TABLE public.purchase_voucher (
    id bigint NOT NULL,
    lastupdate timestamp without time zone,
    creationdate timestamp without time zone,
    paied boolean,
    totalcost double precision,
    version integer,
    stores_id bigint
);



CREATE TABLE public.purchase_voucher_coupon (
    id bigint NOT NULL,
    lastupdate timestamp without time zone,
    creationdate timestamp without time zone,
    nbofdesrvedperson integer,
    value double precision,
    version integer,
    submissiondetailscfg_id bigint,
    purchase_voucher_id bigint
);



CREATE TABLE public.purchase_voucher_desrved (
    purchase_voucher_id bigint NOT NULL,
    deserved_id bigint NOT NULL
);



CREATE TABLE public.receive_money (
    id bigint NOT NULL,
    lastupdate timestamp without time zone,
    creationdate timestamp without time zone,
    currencyenum character varying(255),
    date timestamp without time zone,
    note character varying(255),
    value double precision,
    version integer,
    sponser_id bigint,
    submissiondetailscfg_id bigint
);



CREATE SEQUENCE public.seq_gen_sequence
    START WITH 50
    INCREMENT BY 50
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;



CREATE TABLE public.service (
    id bigint NOT NULL,
    lastupdate timestamp without time zone,
    counted boolean,
    creationdate timestamp without time zone,
    name character varying(255),
    version integer
);



CREATE TABLE public.sponser (
    id bigint NOT NULL,
    lastupdate timestamp without time zone,
    creationdate timestamp without time zone,
    fullname character varying(255),
    livingarea character varying(255),
    phoneno character varying(255),
    version integer,
    religionreference_id bigint
);



CREATE TABLE public.stores (
    id bigint NOT NULL,
    lastupdate timestamp without time zone,
    address character varying(255),
    creationdate timestamp without time zone,
    phoneno character varying(255),
    storename character varying(255),
    version integer
);



CREATE TABLE public.subbmission_exchnage (
    id bigint NOT NULL,
    lastupdate timestamp without time zone,
    creationdate timestamp without time zone,
    date timestamp without time zone,
    liravalue double precision,
    rate double precision,
    version integer,
    submissiondetailscfg_id bigint
);



CREATE TABLE public.worker (
    id bigint NOT NULL,
    lastupdate timestamp without time zone,
    creationdate timestamp without time zone,
    livingarea character varying(255),
    name character varying(255),
    notes character varying(255),
    phoneno character varying(255),
    version integer,
    religion_id bigint
);



CREATE TABLE public.worker_service (
    worker_id bigint NOT NULL,
    service_id bigint NOT NULL
);



ALTER TABLE ONLY public.attachment
    ADD CONSTRAINT attachment_pkey PRIMARY KEY (id);



ALTER TABLE ONLY public.cfg_company
    ADD CONSTRAINT cfg_company_pkey PRIMARY KEY (id);



ALTER TABLE ONLY public.cfg_healthystatus
    ADD CONSTRAINT cfg_healthystatus_pkey PRIMARY KEY (id);



ALTER TABLE ONLY public.cfg_inssurance
    ADD CONSTRAINT cfg_inssurance_pkey PRIMARY KEY (id);



ALTER TABLE ONLY public.cfg_poorpercentage
    ADD CONSTRAINT cfg_poorpercentage_pkey PRIMARY KEY (id);



ALTER TABLE ONLY public.cfg_religionreference
    ADD CONSTRAINT cfg_religionreference_pkey PRIMARY KEY (id);



ALTER TABLE ONLY public.cfg_submission
    ADD CONSTRAINT cfg_submission_pkey PRIMARY KEY (id);



ALTER TABLE ONLY public.cfg_submissiondetails
    ADD CONSTRAINT cfg_submissiondetails_pkey PRIMARY KEY (id);



ALTER TABLE ONLY public.deadneed
    ADD CONSTRAINT deadneed_pkey PRIMARY KEY (id);



ALTER TABLE ONLY public.deadperson
    ADD CONSTRAINT deadperson_pkey PRIMARY KEY (id);



ALTER TABLE ONLY public.deserved
    ADD CONSTRAINT deserved_pkey PRIMARY KEY (id);



ALTER TABLE ONLY public.mediator
    ADD CONSTRAINT mediator_pkey PRIMARY KEY (id);



ALTER TABLE ONLY public.needdetails
    ADD CONSTRAINT needdetails_pkey PRIMARY KEY (id);



ALTER TABLE ONLY public.provideaid
    ADD CONSTRAINT provideaid_pkey PRIMARY KEY (id);



ALTER TABLE ONLY public.provideaidcompany
    ADD CONSTRAINT provideaidcompany_pkey PRIMARY KEY (id);



ALTER TABLE ONLY public.provideaiddesrvedperson
    ADD CONSTRAINT provideaiddesrvedperson_pkey PRIMARY KEY (id);



ALTER TABLE ONLY public.provideaidrabet
    ADD CONSTRAINT provideaidrabet_pkey PRIMARY KEY (id);



ALTER TABLE ONLY public.purchase_voucher_coupon
    ADD CONSTRAINT purchase_voucher_coupon_pkey PRIMARY KEY (id);



ALTER TABLE ONLY public.purchase_voucher_desrved
    ADD CONSTRAINT purchase_voucher_desrved_pkey PRIMARY KEY (purchase_voucher_id, deserved_id);



ALTER TABLE ONLY public.purchase_voucher
    ADD CONSTRAINT purchase_voucher_pkey PRIMARY KEY (id);



ALTER TABLE ONLY public.receive_money
    ADD CONSTRAINT receive_money_pkey PRIMARY KEY (id);



ALTER TABLE ONLY public.service
    ADD CONSTRAINT service_pkey PRIMARY KEY (id);



ALTER TABLE ONLY public.sponser
    ADD CONSTRAINT sponser_pkey PRIMARY KEY (id);



ALTER TABLE ONLY public.stores
    ADD CONSTRAINT stores_pkey PRIMARY KEY (id);



ALTER TABLE ONLY public.subbmission_exchnage
    ADD CONSTRAINT subbmission_exchnage_pkey PRIMARY KEY (id);



ALTER TABLE ONLY public.worker
    ADD CONSTRAINT worker_pkey PRIMARY KEY (id);



ALTER TABLE ONLY public.worker_service
    ADD CONSTRAINT worker_service_pkey PRIMARY KEY (worker_id, service_id);



ALTER TABLE ONLY public.attachment
    ADD CONSTRAINT fk_attachment_mediator_id FOREIGN KEY (mediator_id) REFERENCES public.mediator(id);



ALTER TABLE ONLY public.attachment
    ADD CONSTRAINT fk_attachment_needdetails_id FOREIGN KEY (needdetails_id) REFERENCES public.needdetails(id);



ALTER TABLE ONLY public.attachment
    ADD CONSTRAINT fk_attachment_purchasevoucher_id FOREIGN KEY (purchasevoucher_id) REFERENCES public.purchase_voucher(id);



ALTER TABLE ONLY public.cfg_submissiondetails
    ADD CONSTRAINT fk_cfg_submissiondetails_submission_id FOREIGN KEY (submission_id) REFERENCES public.cfg_submission(id);



ALTER TABLE ONLY public.deadneed
    ADD CONSTRAINT fk_deadneed_deadperson_id FOREIGN KEY (deadperson_id) REFERENCES public.deadperson(id);



ALTER TABLE ONLY public.deadneed
    ADD CONSTRAINT fk_deadneed_service_id FOREIGN KEY (service_id) REFERENCES public.service(id);



ALTER TABLE ONLY public.deserved
    ADD CONSTRAINT fk_deserved_healthystatus_id FOREIGN KEY (healthystatus_id) REFERENCES public.cfg_healthystatus(id);



ALTER TABLE ONLY public.deserved
    ADD CONSTRAINT fk_deserved_inssurance_id FOREIGN KEY (inssurance_id) REFERENCES public.cfg_inssurance(id);



ALTER TABLE ONLY public.deserved
    ADD CONSTRAINT fk_deserved_poorpercentage_id FOREIGN KEY (poorpercentage_id) REFERENCES public.cfg_poorpercentage(id);



ALTER TABLE ONLY public.deserved
    ADD CONSTRAINT fk_deserved_religionreference_id FOREIGN KEY (religionreference_id) REFERENCES public.cfg_religionreference(id);



ALTER TABLE ONLY public.mediator
    ADD CONSTRAINT fk_mediator_company_id FOREIGN KEY (company_id) REFERENCES public.cfg_company(id);



ALTER TABLE ONLY public.mediator
    ADD CONSTRAINT fk_mediator_sponsor_id FOREIGN KEY (sponsor_id) REFERENCES public.sponser(id);



ALTER TABLE ONLY public.needdetails
    ADD CONSTRAINT fk_needdetails_deadneed_id FOREIGN KEY (deadneed_id) REFERENCES public.deadneed(id);



ALTER TABLE ONLY public.needdetails
    ADD CONSTRAINT fk_needdetails_worker_id FOREIGN KEY (worker_id) REFERENCES public.worker(id);



ALTER TABLE ONLY public.provideaid
    ADD CONSTRAINT fk_provideaid_submissiondetailscfg_id FOREIGN KEY (submissiondetailscfg_id) REFERENCES public.cfg_submissiondetails(id);



ALTER TABLE ONLY public.provideaidcompany
    ADD CONSTRAINT fk_provideaidcompany_company_id FOREIGN KEY (company_id) REFERENCES public.cfg_company(id);



ALTER TABLE ONLY public.provideaidcompany
    ADD CONSTRAINT fk_provideaidcompany_submissiondetailscfg_id FOREIGN KEY (submissiondetailscfg_id) REFERENCES public.cfg_submissiondetails(id);



ALTER TABLE ONLY public.provideaiddesrvedperson
    ADD CONSTRAINT fk_provideaiddesrvedperson_desrvedperson_id FOREIGN KEY (desrvedperson_id) REFERENCES public.deserved(id);



ALTER TABLE ONLY public.provideaiddesrvedperson
    ADD CONSTRAINT fk_provideaiddesrvedperson_submissiondetailscfg_id FOREIGN KEY (submissiondetailscfg_id) REFERENCES public.cfg_submissiondetails(id);



ALTER TABLE ONLY public.provideaidrabet
    ADD CONSTRAINT fk_provideaidrabet_submissiondetailscfg_id FOREIGN KEY (submissiondetailscfg_id) REFERENCES public.cfg_submissiondetails(id);



ALTER TABLE ONLY public.purchase_voucher_coupon
    ADD CONSTRAINT fk_purchase_voucher_coupon_purchase_voucher_id FOREIGN KEY (purchase_voucher_id) REFERENCES public.purchase_voucher(id);



ALTER TABLE ONLY public.purchase_voucher_coupon
    ADD CONSTRAINT fk_purchase_voucher_coupon_submissiondetailscfg_id FOREIGN KEY (submissiondetailscfg_id) REFERENCES public.cfg_submissiondetails(id);



ALTER TABLE ONLY public.purchase_voucher_desrved
    ADD CONSTRAINT fk_purchase_voucher_desrved_deserved_id FOREIGN KEY (deserved_id) REFERENCES public.deserved(id);



ALTER TABLE ONLY public.purchase_voucher_desrved
    ADD CONSTRAINT fk_purchase_voucher_desrved_purchase_voucher_id FOREIGN KEY (purchase_voucher_id) REFERENCES public.purchase_voucher(id);



ALTER TABLE ONLY public.purchase_voucher
    ADD CONSTRAINT fk_purchase_voucher_stores_id FOREIGN KEY (stores_id) REFERENCES public.stores(id);



ALTER TABLE ONLY public.receive_money
    ADD CONSTRAINT fk_receive_money_sponser_id FOREIGN KEY (sponser_id) REFERENCES public.sponser(id);



ALTER TABLE ONLY public.receive_money
    ADD CONSTRAINT fk_receive_money_submissiondetailscfg_id FOREIGN KEY (submissiondetailscfg_id) REFERENCES public.cfg_submissiondetails(id);



ALTER TABLE ONLY public.sponser
    ADD CONSTRAINT fk_sponser_religionreference_id FOREIGN KEY (religionreference_id) REFERENCES public.cfg_religionreference(id);



ALTER TABLE ONLY public.subbmission_exchnage
    ADD CONSTRAINT fk_subbmission_exchnage_submissiondetailscfg_id FOREIGN KEY (submissiondetailscfg_id) REFERENCES public.cfg_submissiondetails(id);



ALTER TABLE ONLY public.worker
    ADD CONSTRAINT fk_worker_religion_id FOREIGN KEY (religion_id) REFERENCES public.cfg_religionreference(id);



ALTER TABLE ONLY public.worker_service
    ADD CONSTRAINT fk_worker_service_service_id FOREIGN KEY (service_id) REFERENCES public.service(id);



ALTER TABLE ONLY public.worker_service
    ADD CONSTRAINT fk_worker_service_worker_id FOREIGN KEY (worker_id) REFERENCES public.worker(id);



