--
-- TOC entry 1575 (class 1259 OID 21946)
-- Dependencies: 5
-- Name: actividad; Type: TABLE; Schema: public; Owner: gestion; Tablespace: 
--

CREATE TABLE actividad (
    id bigint NOT NULL,
    proceso bigint,
    nro_actividad character varying(50),
    cronograma_detalle bigint,
    descripcion character varying(50),
    responsable bigint,
    fecha_creacion timestamp without time zone,
    fecha_inicio_previsto timestamp without time zone,
    fecha_inicio_reprogramado timestamp without time zone,
    motivo_reprogramacion_inicio character varying(500),
    fecha_fin_prevista timestamp without time zone,
    fecha_fin_reprogramada timestamp without time zone,
    motivo_reprogramacion character varying(500),
    fecha_devuelta timestamp without time zone,
    fecha_resuelta timestamp without time zone,
    fecha_cancelacion timestamp without time zone,
    pregunta character varying(50),
    respuesta character varying(3),
    estado character varying(3),
    checklist_completo character varying(3),
    actividad_anterior bigint,
    alerta bigint,
    alarma bigint,
    super_tarea bigint
);


ALTER TABLE public.actividad OWNER TO gestion;

--
-- TOC entry 1613 (class 1259 OID 22119)
-- Dependencies: 5
-- Name: actividad_checlikst_detalle; Type: TABLE; Schema: public; Owner: gestion; Tablespace: 
--

CREATE TABLE actividad_checlikst_detalle (
    id bigint NOT NULL,
    descripcion character varying(50),
    respuesta character varying(3),
    fecha_hora timestamp without time zone
);


ALTER TABLE public.actividad_checlikst_detalle OWNER TO gestion;

--
-- TOC entry 1612 (class 1259 OID 22117)
-- Dependencies: 1613 5
-- Name: actividad_checlikst_detalle_id_seq; Type: SEQUENCE; Schema: public; Owner: gestion
--

CREATE SEQUENCE actividad_checlikst_detalle_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.actividad_checlikst_detalle_id_seq OWNER TO gestion;

--
-- TOC entry 2022 (class 0 OID 0)
-- Dependencies: 1612
-- Name: actividad_checlikst_detalle_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: gestion
--

ALTER SEQUENCE actividad_checlikst_detalle_id_seq OWNED BY actividad_checlikst_detalle.id;


--
-- TOC entry 2023 (class 0 OID 0)
-- Dependencies: 1612
-- Name: actividad_checlikst_detalle_id_seq; Type: SEQUENCE SET; Schema: public; Owner: gestion
--

SELECT pg_catalog.setval('actividad_checlikst_detalle_id_seq', 1, false);


--
-- TOC entry 1574 (class 1259 OID 21944)
-- Dependencies: 1575 5
-- Name: actividad_id_seq; Type: SEQUENCE; Schema: public; Owner: gestion
--

CREATE SEQUENCE actividad_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.actividad_id_seq OWNER TO gestion;

--
-- TOC entry 2024 (class 0 OID 0)
-- Dependencies: 1574
-- Name: actividad_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: gestion
--

ALTER SEQUENCE actividad_id_seq OWNED BY actividad.id;


--
-- TOC entry 2025 (class 0 OID 0)
-- Dependencies: 1574
-- Name: actividad_id_seq; Type: SEQUENCE SET; Schema: public; Owner: gestion
--

SELECT pg_catalog.setval('actividad_id_seq', 1, false);


--
-- TOC entry 1609 (class 1259 OID 22100)
-- Dependencies: 5
-- Name: aud_log; Type: TABLE; Schema: public; Owner: gestion; Tablespace: 
--

CREATE TABLE aud_log (
    id bigint NOT NULL,
    aud_tabla_id bigint,
    registro_id bigint,
    valor_anterior text,
    valor_nuevo text,
    fecha timestamp without time zone,
    usuario_id bigint,
    ip character varying(50)
);


ALTER TABLE public.aud_log OWNER TO gestion;

--
-- TOC entry 1608 (class 1259 OID 22098)
-- Dependencies: 1609 5
-- Name: aud_log_id_seq; Type: SEQUENCE; Schema: public; Owner: gestion
--

CREATE SEQUENCE aud_log_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.aud_log_id_seq OWNER TO gestion;

--
-- TOC entry 2026 (class 0 OID 0)
-- Dependencies: 1608
-- Name: aud_log_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: gestion
--

ALTER SEQUENCE aud_log_id_seq OWNED BY aud_log.id;


--
-- TOC entry 2027 (class 0 OID 0)
-- Dependencies: 1608
-- Name: aud_log_id_seq; Type: SEQUENCE SET; Schema: public; Owner: gestion
--

SELECT pg_catalog.setval('aud_log_id_seq', 22, true);


--
-- TOC entry 1607 (class 1259 OID 22092)
-- Dependencies: 5
-- Name: aud_tabla; Type: TABLE; Schema: public; Owner: gestion; Tablespace: 
--

CREATE TABLE aud_tabla (
    id bigint NOT NULL,
    nombre character varying(50)
);


ALTER TABLE public.aud_tabla OWNER TO gestion;

--
-- TOC entry 1606 (class 1259 OID 22090)
-- Dependencies: 5 1607
-- Name: aud_tabla_id_seq; Type: SEQUENCE; Schema: public; Owner: gestion
--

CREATE SEQUENCE aud_tabla_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.aud_tabla_id_seq OWNER TO gestion;

--
-- TOC entry 2028 (class 0 OID 0)
-- Dependencies: 1606
-- Name: aud_tabla_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: gestion
--

ALTER SEQUENCE aud_tabla_id_seq OWNED BY aud_tabla.id;


--
-- TOC entry 2029 (class 0 OID 0)
-- Dependencies: 1606
-- Name: aud_tabla_id_seq; Type: SEQUENCE SET; Schema: public; Owner: gestion
--

SELECT pg_catalog.setval('aud_tabla_id_seq', 1, false);


--
-- TOC entry 1585 (class 1259 OID 21992)
-- Dependencies: 5
-- Name: checklist; Type: TABLE; Schema: public; Owner: gestion; Tablespace: 
--

CREATE TABLE checklist (
    id bigint NOT NULL,
    descripcion character varying(50)
);


ALTER TABLE public.checklist OWNER TO gestion;

--
-- TOC entry 1587 (class 1259 OID 22000)
-- Dependencies: 5
-- Name: checklist_detalle; Type: TABLE; Schema: public; Owner: gestion; Tablespace: 
--

CREATE TABLE checklist_detalle (
    id bigint NOT NULL,
    descripcion character varying(50),
    checklist bigint,
    nro_orden bigint
);


ALTER TABLE public.checklist_detalle OWNER TO gestion;

--
-- TOC entry 1586 (class 1259 OID 21998)
-- Dependencies: 1587 5
-- Name: checklist_detalle_id_seq; Type: SEQUENCE; Schema: public; Owner: gestion
--

CREATE SEQUENCE checklist_detalle_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.checklist_detalle_id_seq OWNER TO gestion;

--
-- TOC entry 2030 (class 0 OID 0)
-- Dependencies: 1586
-- Name: checklist_detalle_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: gestion
--

ALTER SEQUENCE checklist_detalle_id_seq OWNED BY checklist_detalle.id;


--
-- TOC entry 2031 (class 0 OID 0)
-- Dependencies: 1586
-- Name: checklist_detalle_id_seq; Type: SEQUENCE SET; Schema: public; Owner: gestion
--

SELECT pg_catalog.setval('checklist_detalle_id_seq', 1, false);


--
-- TOC entry 1584 (class 1259 OID 21990)
-- Dependencies: 5 1585
-- Name: checklist_id_seq; Type: SEQUENCE; Schema: public; Owner: gestion
--

CREATE SEQUENCE checklist_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.checklist_id_seq OWNER TO gestion;

--
-- TOC entry 2032 (class 0 OID 0)
-- Dependencies: 1584
-- Name: checklist_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: gestion
--

ALTER SEQUENCE checklist_id_seq OWNED BY checklist.id;


--
-- TOC entry 2033 (class 0 OID 0)
-- Dependencies: 1584
-- Name: checklist_id_seq; Type: SEQUENCE SET; Schema: public; Owner: gestion
--

SELECT pg_catalog.setval('checklist_id_seq', 1, false);


--
-- TOC entry 1583 (class 1259 OID 21981)
-- Dependencies: 5
-- Name: cliente; Type: TABLE; Schema: public; Owner: gestion; Tablespace: 
--

CREATE TABLE cliente (
    id bigint NOT NULL,
    nombre character varying(50),
    direccion character varying(500),
    telefono character varying(50),
    persona_contacto character varying(500)
);


ALTER TABLE public.cliente OWNER TO gestion;

--
-- TOC entry 1582 (class 1259 OID 21979)
-- Dependencies: 5 1583
-- Name: cliente_id_seq; Type: SEQUENCE; Schema: public; Owner: gestion
--

CREATE SEQUENCE cliente_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.cliente_id_seq OWNER TO gestion;

--
-- TOC entry 2034 (class 0 OID 0)
-- Dependencies: 1582
-- Name: cliente_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: gestion
--

ALTER SEQUENCE cliente_id_seq OWNED BY cliente.id;


--
-- TOC entry 2035 (class 0 OID 0)
-- Dependencies: 1582
-- Name: cliente_id_seq; Type: SEQUENCE SET; Schema: public; Owner: gestion
--

SELECT pg_catalog.setval('cliente_id_seq', 1, true);


--
-- TOC entry 1579 (class 1259 OID 21965)
-- Dependencies: 5
-- Name: cronograma; Type: TABLE; Schema: public; Owner: gestion; Tablespace: 
--

CREATE TABLE cronograma (
    id bigint NOT NULL,
    nombre character varying(50)
);


ALTER TABLE public.cronograma OWNER TO gestion;

--
-- TOC entry 1581 (class 1259 OID 21973)
-- Dependencies: 5
-- Name: cronograma_detalle; Type: TABLE; Schema: public; Owner: gestion; Tablespace: 
--

CREATE TABLE cronograma_detalle (
    id bigint NOT NULL,
    cronograma bigint,
    descripcion character varying(50),
    rol bigint,
    alerta_inicio character varying(3),
    duracion bigint,
    alerta bigint,
    alarma bigint,
    pregunta bigint,
    tarea_si bigint,
    tarea_no bigint,
    tarea_siguiente bigint,
    checklist bigint,
    nro_orden bigint
);


ALTER TABLE public.cronograma_detalle OWNER TO gestion;

--
-- TOC entry 1580 (class 1259 OID 21971)
-- Dependencies: 5 1581
-- Name: cronograma_detalle_id_seq; Type: SEQUENCE; Schema: public; Owner: gestion
--

CREATE SEQUENCE cronograma_detalle_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.cronograma_detalle_id_seq OWNER TO gestion;

--
-- TOC entry 2036 (class 0 OID 0)
-- Dependencies: 1580
-- Name: cronograma_detalle_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: gestion
--

ALTER SEQUENCE cronograma_detalle_id_seq OWNED BY cronograma_detalle.id;


--
-- TOC entry 2037 (class 0 OID 0)
-- Dependencies: 1580
-- Name: cronograma_detalle_id_seq; Type: SEQUENCE SET; Schema: public; Owner: gestion
--

SELECT pg_catalog.setval('cronograma_detalle_id_seq', 1, false);


--
-- TOC entry 1578 (class 1259 OID 21963)
-- Dependencies: 1579 5
-- Name: cronograma_id_seq; Type: SEQUENCE; Schema: public; Owner: gestion
--

CREATE SEQUENCE cronograma_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.cronograma_id_seq OWNER TO gestion;

--
-- TOC entry 2038 (class 0 OID 0)
-- Dependencies: 1578
-- Name: cronograma_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: gestion
--

ALTER SEQUENCE cronograma_id_seq OWNED BY cronograma.id;


--
-- TOC entry 2039 (class 0 OID 0)
-- Dependencies: 1578
-- Name: cronograma_id_seq; Type: SEQUENCE SET; Schema: public; Owner: gestion
--

SELECT pg_catalog.setval('cronograma_id_seq', 1, true);


--
-- TOC entry 1589 (class 1259 OID 22008)
-- Dependencies: 5
-- Name: documento; Type: TABLE; Schema: public; Owner: gestion; Tablespace: 
--

CREATE TABLE documento (
    id bigint NOT NULL,
    filename character varying(50),
    filepath character varying(500),
    filetype character varying(50),
    file_extension character varying(50),
    bloqueado character varying(3),
    usuario_bloqueo bigint,
    fecha_bloqueo timestamp without time zone,
    usuario_desbloqueo bigint,
    fecha_desbloqueo timestamp without time zone,
    entidad character varying(50),
    id_entidad bigint
);


ALTER TABLE public.documento OWNER TO gestion;

--
-- TOC entry 1588 (class 1259 OID 22006)
-- Dependencies: 5 1589
-- Name: documento_id_seq; Type: SEQUENCE; Schema: public; Owner: gestion
--

CREATE SEQUENCE documento_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.documento_id_seq OWNER TO gestion;

--
-- TOC entry 2040 (class 0 OID 0)
-- Dependencies: 1588
-- Name: documento_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: gestion
--

ALTER SEQUENCE documento_id_seq OWNED BY documento.id;


--
-- TOC entry 2041 (class 0 OID 0)
-- Dependencies: 1588
-- Name: documento_id_seq; Type: SEQUENCE SET; Schema: public; Owner: gestion
--

SELECT pg_catalog.setval('documento_id_seq', 1, false);


--
-- TOC entry 1605 (class 1259 OID 22081)
-- Dependencies: 5
-- Name: log_sesion; Type: TABLE; Schema: public; Owner: gestion; Tablespace: 
--

CREATE TABLE log_sesion (
    id bigint NOT NULL,
    usuario_id bigint,
    fecha_intento timestamp without time zone,
    ip character varying(50),
    exito character varying(3),
    nro_sesion bigint,
    observacion character varying(500),
    usuario character varying(50)
);


ALTER TABLE public.log_sesion OWNER TO gestion;

--
-- TOC entry 1604 (class 1259 OID 22079)
-- Dependencies: 5 1605
-- Name: log_sesion_id_seq; Type: SEQUENCE; Schema: public; Owner: gestion
--

CREATE SEQUENCE log_sesion_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.log_sesion_id_seq OWNER TO gestion;

--
-- TOC entry 2042 (class 0 OID 0)
-- Dependencies: 1604
-- Name: log_sesion_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: gestion
--

ALTER SEQUENCE log_sesion_id_seq OWNED BY log_sesion.id;


--
-- TOC entry 2043 (class 0 OID 0)
-- Dependencies: 1604
-- Name: log_sesion_id_seq; Type: SEQUENCE SET; Schema: public; Owner: gestion
--

SELECT pg_catalog.setval('log_sesion_id_seq', 4, true);


--
-- TOC entry 1615 (class 1259 OID 22127)
-- Dependencies: 5
-- Name: notificaciones; Type: TABLE; Schema: public; Owner: gestion; Tablespace: 
--

CREATE TABLE notificaciones (
    id bigint NOT NULL,
    entidad character varying(50),
    id_entidad bigint,
    mostrado character varying(3),
    usuario bigint,
    responsable bigint,
    fecha_mostrado timestamp without time zone,
    fecha_creacion timestamp without time zone,
    estado character varying(3) NOT NULL,
    titulo character varying(50),
    descripcion character varying(500)
);


ALTER TABLE public.notificaciones OWNER TO gestion;

--
-- TOC entry 1614 (class 1259 OID 22125)
-- Dependencies: 1615 5
-- Name: notificaciones_id_seq; Type: SEQUENCE; Schema: public; Owner: gestion
--

CREATE SEQUENCE notificaciones_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.notificaciones_id_seq OWNER TO gestion;

--
-- TOC entry 2044 (class 0 OID 0)
-- Dependencies: 1614
-- Name: notificaciones_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: gestion
--

ALTER SEQUENCE notificaciones_id_seq OWNED BY notificaciones.id;


--
-- TOC entry 2045 (class 0 OID 0)
-- Dependencies: 1614
-- Name: notificaciones_id_seq; Type: SEQUENCE SET; Schema: public; Owner: gestion
--

SELECT pg_catalog.setval('notificaciones_id_seq', 1, false);


--
-- TOC entry 1611 (class 1259 OID 22111)
-- Dependencies: 5
-- Name: observaciones; Type: TABLE; Schema: public; Owner: gestion; Tablespace: 
--

CREATE TABLE observaciones (
    id bigint NOT NULL,
    descripcion character varying(50),
    usuario bigint,
    fecha_hora timestamp without time zone,
    entidad character varying(50),
    id_entidad bigint
);


ALTER TABLE public.observaciones OWNER TO gestion;

--
-- TOC entry 1610 (class 1259 OID 22109)
-- Dependencies: 1611 5
-- Name: observaciones_id_seq; Type: SEQUENCE; Schema: public; Owner: gestion
--

CREATE SEQUENCE observaciones_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.observaciones_id_seq OWNER TO gestion;

--
-- TOC entry 2046 (class 0 OID 0)
-- Dependencies: 1610
-- Name: observaciones_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: gestion
--

ALTER SEQUENCE observaciones_id_seq OWNED BY observaciones.id;


--
-- TOC entry 2047 (class 0 OID 0)
-- Dependencies: 1610
-- Name: observaciones_id_seq; Type: SEQUENCE SET; Schema: public; Owner: gestion
--

SELECT pg_catalog.setval('observaciones_id_seq', 1, false);


--
-- TOC entry 1599 (class 1259 OID 22057)
-- Dependencies: 5
-- Name: permiso; Type: TABLE; Schema: public; Owner: gestion; Tablespace: 
--

CREATE TABLE permiso (
    id bigint NOT NULL,
    descripcion character varying(50)
);


ALTER TABLE public.permiso OWNER TO gestion;

--
-- TOC entry 1598 (class 1259 OID 22055)
-- Dependencies: 1599 5
-- Name: permiso_id_seq; Type: SEQUENCE; Schema: public; Owner: gestion
--

CREATE SEQUENCE permiso_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.permiso_id_seq OWNER TO gestion;

--
-- TOC entry 2048 (class 0 OID 0)
-- Dependencies: 1598
-- Name: permiso_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: gestion
--

ALTER SEQUENCE permiso_id_seq OWNED BY permiso.id;


--
-- TOC entry 2049 (class 0 OID 0)
-- Dependencies: 1598
-- Name: permiso_id_seq; Type: SEQUENCE SET; Schema: public; Owner: gestion
--

SELECT pg_catalog.setval('permiso_id_seq', 1, false);


--
-- TOC entry 1591 (class 1259 OID 22019)
-- Dependencies: 5
-- Name: pregunta; Type: TABLE; Schema: public; Owner: gestion; Tablespace: 
--

CREATE TABLE pregunta (
    id bigint NOT NULL,
    descripcion character varying(50)
);


ALTER TABLE public.pregunta OWNER TO gestion;

--
-- TOC entry 1590 (class 1259 OID 22017)
-- Dependencies: 5 1591
-- Name: pregunta_id_seq; Type: SEQUENCE; Schema: public; Owner: gestion
--

CREATE SEQUENCE pregunta_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.pregunta_id_seq OWNER TO gestion;

--
-- TOC entry 2050 (class 0 OID 0)
-- Dependencies: 1590
-- Name: pregunta_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: gestion
--

ALTER SEQUENCE pregunta_id_seq OWNED BY pregunta.id;


--
-- TOC entry 2051 (class 0 OID 0)
-- Dependencies: 1590
-- Name: pregunta_id_seq; Type: SEQUENCE SET; Schema: public; Owner: gestion
--

SELECT pg_catalog.setval('pregunta_id_seq', 2, true);


--
-- TOC entry 1573 (class 1259 OID 21935)
-- Dependencies: 5
-- Name: proceso; Type: TABLE; Schema: public; Owner: gestion; Tablespace: 
--

CREATE TABLE proceso (
    id bigint NOT NULL,
    nro_proceso character varying(50),
    responsable bigint,
    cliente bigint,
    cronograma bigint,
    estado character varying(3),
    descripcion character varying(500),
    fecha_inicio_contratual timestamp without time zone,
    fecha_inicio_real timestamp without time zone,
    fecha_fin_prevista timestamp without time zone,
    fecha_fin_reprogramada timestamp without time zone,
    motivo_reprogramacion character varying(500),
    cliente_notificado character varying(3)
);


ALTER TABLE public.proceso OWNER TO gestion;

--
-- TOC entry 1572 (class 1259 OID 21933)
-- Dependencies: 5 1573
-- Name: proceso_id_seq; Type: SEQUENCE; Schema: public; Owner: gestion
--

CREATE SEQUENCE proceso_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.proceso_id_seq OWNER TO gestion;

--
-- TOC entry 2052 (class 0 OID 0)
-- Dependencies: 1572
-- Name: proceso_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: gestion
--

ALTER SEQUENCE proceso_id_seq OWNED BY proceso.id;


--
-- TOC entry 2053 (class 0 OID 0)
-- Dependencies: 1572
-- Name: proceso_id_seq; Type: SEQUENCE SET; Schema: public; Owner: gestion
--

SELECT pg_catalog.setval('proceso_id_seq', 1, false);


--
-- TOC entry 1601 (class 1259 OID 22065)
-- Dependencies: 5
-- Name: rol; Type: TABLE; Schema: public; Owner: gestion; Tablespace: 
--

CREATE TABLE rol (
    id bigint NOT NULL,
    descripcion character varying(50)
);


ALTER TABLE public.rol OWNER TO gestion;

--
-- TOC entry 1600 (class 1259 OID 22063)
-- Dependencies: 1601 5
-- Name: rol_id_seq; Type: SEQUENCE; Schema: public; Owner: gestion
--

CREATE SEQUENCE rol_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.rol_id_seq OWNER TO gestion;

--
-- TOC entry 2054 (class 0 OID 0)
-- Dependencies: 1600
-- Name: rol_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: gestion
--

ALTER SEQUENCE rol_id_seq OWNED BY rol.id;


--
-- TOC entry 2055 (class 0 OID 0)
-- Dependencies: 1600
-- Name: rol_id_seq; Type: SEQUENCE SET; Schema: public; Owner: gestion
--

SELECT pg_catalog.setval('rol_id_seq', 6, true);


--
-- TOC entry 1603 (class 1259 OID 22073)
-- Dependencies: 5
-- Name: rol_permiso; Type: TABLE; Schema: public; Owner: gestion; Tablespace: 
--

CREATE TABLE rol_permiso (
    id bigint NOT NULL,
    rol_id bigint,
    permiso_id bigint
);


ALTER TABLE public.rol_permiso OWNER TO gestion;

--
-- TOC entry 1602 (class 1259 OID 22071)
-- Dependencies: 1603 5
-- Name: rol_permiso_id_seq; Type: SEQUENCE; Schema: public; Owner: gestion
--

CREATE SEQUENCE rol_permiso_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.rol_permiso_id_seq OWNER TO gestion;

--
-- TOC entry 2056 (class 0 OID 0)
-- Dependencies: 1602
-- Name: rol_permiso_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: gestion
--

ALTER SEQUENCE rol_permiso_id_seq OWNED BY rol_permiso.id;


--
-- TOC entry 2057 (class 0 OID 0)
-- Dependencies: 1602
-- Name: rol_permiso_id_seq; Type: SEQUENCE SET; Schema: public; Owner: gestion
--

SELECT pg_catalog.setval('rol_permiso_id_seq', 1, false);


--
-- TOC entry 1577 (class 1259 OID 21957)
-- Dependencies: 5
-- Name: tipo_alarma; Type: TABLE; Schema: public; Owner: gestion; Tablespace: 
--

CREATE TABLE tipo_alarma (
    id bigint NOT NULL,
    descripcion character varying(50),
    dias bigint,
    horas bigint,
    responsable1 bigint,
    responsable2 bigint,
    tipo character varying(50)
);


ALTER TABLE public.tipo_alarma OWNER TO gestion;

--
-- TOC entry 1576 (class 1259 OID 21955)
-- Dependencies: 1577 5
-- Name: tipo_alarma_id_seq; Type: SEQUENCE; Schema: public; Owner: gestion
--

CREATE SEQUENCE tipo_alarma_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.tipo_alarma_id_seq OWNER TO gestion;

--
-- TOC entry 2058 (class 0 OID 0)
-- Dependencies: 1576
-- Name: tipo_alarma_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: gestion
--

ALTER SEQUENCE tipo_alarma_id_seq OWNED BY tipo_alarma.id;


--
-- TOC entry 2059 (class 0 OID 0)
-- Dependencies: 1576
-- Name: tipo_alarma_id_seq; Type: SEQUENCE SET; Schema: public; Owner: gestion
--

SELECT pg_catalog.setval('tipo_alarma_id_seq', 9, true);


--
-- TOC entry 1593 (class 1259 OID 22027)
-- Dependencies: 5
-- Name: usuario; Type: TABLE; Schema: public; Owner: gestion; Tablespace: 
--

CREATE TABLE usuario (
    id bigint NOT NULL,
    usuario character varying(50),
    nombre character varying(50),
    apellido character varying(50),
    ultimo_login timestamp without time zone,
    estado character varying(3),
    email character varying(500),
    nro_sesion bigint
);


ALTER TABLE public.usuario OWNER TO gestion;

--
-- TOC entry 1592 (class 1259 OID 22025)
-- Dependencies: 1593 5
-- Name: usuario_id_seq; Type: SEQUENCE; Schema: public; Owner: gestion
--

CREATE SEQUENCE usuario_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.usuario_id_seq OWNER TO gestion;

--
-- TOC entry 2060 (class 0 OID 0)
-- Dependencies: 1592
-- Name: usuario_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: gestion
--

ALTER SEQUENCE usuario_id_seq OWNED BY usuario.id;


--
-- TOC entry 2061 (class 0 OID 0)
-- Dependencies: 1592
-- Name: usuario_id_seq; Type: SEQUENCE SET; Schema: public; Owner: gestion
--

SELECT pg_catalog.setval('usuario_id_seq', 3, true);


--
-- TOC entry 1595 (class 1259 OID 22038)
-- Dependencies: 5
-- Name: usuario_password; Type: TABLE; Schema: public; Owner: gestion; Tablespace: 
--

CREATE TABLE usuario_password (
    id bigint NOT NULL,
    usuario_id bigint,
    password character varying(500)
);


ALTER TABLE public.usuario_password OWNER TO gestion;

--
-- TOC entry 1594 (class 1259 OID 22036)
-- Dependencies: 1595 5
-- Name: usuario_password_id_seq; Type: SEQUENCE; Schema: public; Owner: gestion
--

CREATE SEQUENCE usuario_password_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.usuario_password_id_seq OWNER TO gestion;

--
-- TOC entry 2062 (class 0 OID 0)
-- Dependencies: 1594
-- Name: usuario_password_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: gestion
--

ALTER SEQUENCE usuario_password_id_seq OWNED BY usuario_password.id;


--
-- TOC entry 2063 (class 0 OID 0)
-- Dependencies: 1594
-- Name: usuario_password_id_seq; Type: SEQUENCE SET; Schema: public; Owner: gestion
--

SELECT pg_catalog.setval('usuario_password_id_seq', 2, true);


--
-- TOC entry 1597 (class 1259 OID 22049)
-- Dependencies: 5
-- Name: usuario_rol_permiso; Type: TABLE; Schema: public; Owner: gestion; Tablespace: 
--

CREATE TABLE usuario_rol_permiso (
    id bigint NOT NULL,
    usuario_id bigint,
    permiso_id bigint,
    rol_id bigint
);


ALTER TABLE public.usuario_rol_permiso OWNER TO gestion;

--
-- TOC entry 1596 (class 1259 OID 22047)
-- Dependencies: 5 1597
-- Name: usuario_rol_permiso_id_seq; Type: SEQUENCE; Schema: public; Owner: gestion
--

CREATE SEQUENCE usuario_rol_permiso_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.usuario_rol_permiso_id_seq OWNER TO gestion;

--
-- TOC entry 2064 (class 0 OID 0)
-- Dependencies: 1596
-- Name: usuario_rol_permiso_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: gestion
--

ALTER SEQUENCE usuario_rol_permiso_id_seq OWNED BY usuario_rol_permiso.id;


--
-- TOC entry 2065 (class 0 OID 0)
-- Dependencies: 1596
-- Name: usuario_rol_permiso_id_seq; Type: SEQUENCE SET; Schema: public; Owner: gestion
--

SELECT pg_catalog.setval('usuario_rol_permiso_id_seq', 2, true);


--
-- TOC entry 1894 (class 2604 OID 21949)
-- Dependencies: 1575 1574 1575
-- Name: id; Type: DEFAULT; Schema: public; Owner: gestion
--

ALTER TABLE actividad ALTER COLUMN id SET DEFAULT nextval('actividad_id_seq'::regclass);


--
-- TOC entry 1913 (class 2604 OID 22122)
-- Dependencies: 1612 1613 1613
-- Name: id; Type: DEFAULT; Schema: public; Owner: gestion
--

ALTER TABLE actividad_checlikst_detalle ALTER COLUMN id SET DEFAULT nextval('actividad_checlikst_detalle_id_seq'::regclass);


--
-- TOC entry 1911 (class 2604 OID 22103)
-- Dependencies: 1609 1608 1609
-- Name: id; Type: DEFAULT; Schema: public; Owner: gestion
--

ALTER TABLE aud_log ALTER COLUMN id SET DEFAULT nextval('aud_log_id_seq'::regclass);


--
-- TOC entry 1910 (class 2604 OID 22095)
-- Dependencies: 1607 1606 1607
-- Name: id; Type: DEFAULT; Schema: public; Owner: gestion
--

ALTER TABLE aud_tabla ALTER COLUMN id SET DEFAULT nextval('aud_tabla_id_seq'::regclass);


--
-- TOC entry 1899 (class 2604 OID 21995)
-- Dependencies: 1585 1584 1585
-- Name: id; Type: DEFAULT; Schema: public; Owner: gestion
--

ALTER TABLE checklist ALTER COLUMN id SET DEFAULT nextval('checklist_id_seq'::regclass);


--
-- TOC entry 1900 (class 2604 OID 22003)
-- Dependencies: 1586 1587 1587
-- Name: id; Type: DEFAULT; Schema: public; Owner: gestion
--

ALTER TABLE checklist_detalle ALTER COLUMN id SET DEFAULT nextval('checklist_detalle_id_seq'::regclass);


--
-- TOC entry 1898 (class 2604 OID 21984)
-- Dependencies: 1582 1583 1583
-- Name: id; Type: DEFAULT; Schema: public; Owner: gestion
--

ALTER TABLE cliente ALTER COLUMN id SET DEFAULT nextval('cliente_id_seq'::regclass);


--
-- TOC entry 1896 (class 2604 OID 21968)
-- Dependencies: 1579 1578 1579
-- Name: id; Type: DEFAULT; Schema: public; Owner: gestion
--

ALTER TABLE cronograma ALTER COLUMN id SET DEFAULT nextval('cronograma_id_seq'::regclass);


--
-- TOC entry 1897 (class 2604 OID 21976)
-- Dependencies: 1581 1580 1581
-- Name: id; Type: DEFAULT; Schema: public; Owner: gestion
--

ALTER TABLE cronograma_detalle ALTER COLUMN id SET DEFAULT nextval('cronograma_detalle_id_seq'::regclass);


--
-- TOC entry 1901 (class 2604 OID 22011)
-- Dependencies: 1589 1588 1589
-- Name: id; Type: DEFAULT; Schema: public; Owner: gestion
--

ALTER TABLE documento ALTER COLUMN id SET DEFAULT nextval('documento_id_seq'::regclass);


--
-- TOC entry 1909 (class 2604 OID 22084)
-- Dependencies: 1604 1605 1605
-- Name: id; Type: DEFAULT; Schema: public; Owner: gestion
--

ALTER TABLE log_sesion ALTER COLUMN id SET DEFAULT nextval('log_sesion_id_seq'::regclass);


--
-- TOC entry 1914 (class 2604 OID 22130)
-- Dependencies: 1615 1614 1615
-- Name: id; Type: DEFAULT; Schema: public; Owner: gestion
--

ALTER TABLE notificaciones ALTER COLUMN id SET DEFAULT nextval('notificaciones_id_seq'::regclass);


--
-- TOC entry 1912 (class 2604 OID 22114)
-- Dependencies: 1611 1610 1611
-- Name: id; Type: DEFAULT; Schema: public; Owner: gestion
--

ALTER TABLE observaciones ALTER COLUMN id SET DEFAULT nextval('observaciones_id_seq'::regclass);


--
-- TOC entry 1906 (class 2604 OID 22060)
-- Dependencies: 1598 1599 1599
-- Name: id; Type: DEFAULT; Schema: public; Owner: gestion
--

ALTER TABLE permiso ALTER COLUMN id SET DEFAULT nextval('permiso_id_seq'::regclass);


--
-- TOC entry 1902 (class 2604 OID 22022)
-- Dependencies: 1590 1591 1591
-- Name: id; Type: DEFAULT; Schema: public; Owner: gestion
--

ALTER TABLE pregunta ALTER COLUMN id SET DEFAULT nextval('pregunta_id_seq'::regclass);


--
-- TOC entry 1893 (class 2604 OID 21938)
-- Dependencies: 1573 1572 1573
-- Name: id; Type: DEFAULT; Schema: public; Owner: gestion
--

ALTER TABLE proceso ALTER COLUMN id SET DEFAULT nextval('proceso_id_seq'::regclass);


--
-- TOC entry 1907 (class 2604 OID 22068)
-- Dependencies: 1601 1600 1601
-- Name: id; Type: DEFAULT; Schema: public; Owner: gestion
--

ALTER TABLE rol ALTER COLUMN id SET DEFAULT nextval('rol_id_seq'::regclass);


--
-- TOC entry 1908 (class 2604 OID 22076)
-- Dependencies: 1602 1603 1603
-- Name: id; Type: DEFAULT; Schema: public; Owner: gestion
--

ALTER TABLE rol_permiso ALTER COLUMN id SET DEFAULT nextval('rol_permiso_id_seq'::regclass);


--
-- TOC entry 1895 (class 2604 OID 21960)
-- Dependencies: 1576 1577 1577
-- Name: id; Type: DEFAULT; Schema: public; Owner: gestion
--

ALTER TABLE tipo_alarma ALTER COLUMN id SET DEFAULT nextval('tipo_alarma_id_seq'::regclass);


--
-- TOC entry 1903 (class 2604 OID 22030)
-- Dependencies: 1593 1592 1593
-- Name: id; Type: DEFAULT; Schema: public; Owner: gestion
--

ALTER TABLE usuario ALTER COLUMN id SET DEFAULT nextval('usuario_id_seq'::regclass);


--
-- TOC entry 1904 (class 2604 OID 22041)
-- Dependencies: 1594 1595 1595
-- Name: id; Type: DEFAULT; Schema: public; Owner: gestion
--

ALTER TABLE usuario_password ALTER COLUMN id SET DEFAULT nextval('usuario_password_id_seq'::regclass);


--
-- TOC entry 1905 (class 2604 OID 22052)
-- Dependencies: 1597 1596 1597
-- Name: id; Type: DEFAULT; Schema: public; Owner: gestion
--

ALTER TABLE usuario_rol_permiso ALTER COLUMN id SET DEFAULT nextval('usuario_rol_permiso_id_seq'::regclass);


--
-- TOC entry 1996 (class 0 OID 21946)
-- Dependencies: 1575
-- Data for Name: actividad; Type: TABLE DATA; Schema: public; Owner: gestion
--



--
-- TOC entry 2015 (class 0 OID 22119)
-- Dependencies: 1613
-- Data for Name: actividad_checlikst_detalle; Type: TABLE DATA; Schema: public; Owner: gestion
--



--
-- TOC entry 2013 (class 0 OID 22100)
-- Dependencies: 1609
-- Data for Name: aud_log; Type: TABLE DATA; Schema: public; Owner: gestion
--

INSERT INTO aud_log VALUES (2, NULL, 3, 'Registro Nuevo', 'usuarioId=3#apellido=usuario#email=usuario@email.com#estado=A#nombre=usuario#nroSesion=null#ultimoLogin=null#usuario=usuario', '2013-08-23 19:04:26.844', 1, '127.0.0.1');
INSERT INTO aud_log VALUES (4, NULL, 3, 'usuarioId=3#apellido=usuario#email=usuario@email.com#estado=A#nombre=usuario#nroSesion=null#ultimoLogin=null#usuario=usuario', 'usuarioId=3#apellido=usuario#email=usuario@email.com#estado=A#nombre=usuario#nroSesion=null#ultimoLogin=null#usuario=usuario', '2013-08-23 19:04:45.057', 1, '127.0.0.1');
INSERT INTO aud_log VALUES (5, NULL, 2, 'Registro Nuevo', 'usuarioRolPermisoId=2#rol.rolId=2#usuario.usuarioId=3', '2013-08-23 19:04:45.084', 1, '127.0.0.1');
INSERT INTO aud_log VALUES (6, NULL, 3, 'Registro Nuevo', 'rolId=3#descripcion=Ingeniero', '2013-08-23 19:05:09.587', 1, '127.0.0.1');
INSERT INTO aud_log VALUES (7, NULL, 4, 'Registro Nuevo', 'rolId=4#descripcion=Autocadista', '2013-08-23 19:05:21.63', 1, '127.0.0.1');
INSERT INTO aud_log VALUES (8, NULL, 5, 'Registro Nuevo', 'rolId=5#descripcion=Ayudante de campo', '2013-08-23 19:05:28.26', 1, '127.0.0.1');
INSERT INTO aud_log VALUES (9, NULL, 6, 'Registro Nuevo', 'rolId=6#descripcion=Administrativo', '2013-08-23 19:05:36.698', 1, '127.0.0.1');
INSERT INTO aud_log VALUES (17, NULL, 8, 'Registro Nuevo', 'tipoAlarmaId=8#descripcion=alerta 1#tipo=Alerta#dias=1#horas=0#responsable1.usuarioId=3#responsable2.usuarioId=1', '2013-08-23 19:07:48.268', 1, '127.0.0.1');
INSERT INTO aud_log VALUES (18, NULL, 9, 'Registro Nuevo', 'tipoAlarmaId=9#descripcion=alarma 1#tipo=Alarma#dias=1#horas=0#responsable1.usuarioId=3#responsable2.usuarioId=1', '2013-08-23 19:08:10.437', 1, '127.0.0.1');
INSERT INTO aud_log VALUES (19, NULL, 1, 'Registro Nuevo', 'clienteId=1#nombre=AIT S.A.#direccion=Gral. Santos 843#telefono=201014#personaContacto=Margarita Rojas', '2013-08-23 19:08:48.633', 1, '127.0.0.1');
INSERT INTO aud_log VALUES (20, NULL, 1, 'Registro Nuevo', 'preguntaId=1#descripcion=Tiene anticipo?', '2013-08-23 19:09:00.938', 1, '127.0.0.1');
INSERT INTO aud_log VALUES (21, NULL, 2, 'Registro Nuevo', 'preguntaId=2#descripcion=Requiere evaluación in situ?', '2013-08-23 19:09:12.715', 1, '127.0.0.1');
INSERT INTO aud_log VALUES (22, NULL, 1, 'Registro Nuevo', 'cronogramaId=1#nombre=Cronograma 1', '2013-08-23 19:09:43.419', 1, '127.0.0.1');


--
-- TOC entry 2012 (class 0 OID 22092)
-- Dependencies: 1607
-- Data for Name: aud_tabla; Type: TABLE DATA; Schema: public; Owner: gestion
--



--
-- TOC entry 2001 (class 0 OID 21992)
-- Dependencies: 1585
-- Data for Name: checklist; Type: TABLE DATA; Schema: public; Owner: gestion
--



--
-- TOC entry 2002 (class 0 OID 22000)
-- Dependencies: 1587
-- Data for Name: checklist_detalle; Type: TABLE DATA; Schema: public; Owner: gestion
--



--
-- TOC entry 2000 (class 0 OID 21981)
-- Dependencies: 1583
-- Data for Name: cliente; Type: TABLE DATA; Schema: public; Owner: gestion
--

INSERT INTO cliente VALUES (1, 'AIT S.A.', 'Gral. Santos 843', '201014', 'Margarita Rojas');


--
-- TOC entry 1998 (class 0 OID 21965)
-- Dependencies: 1579
-- Data for Name: cronograma; Type: TABLE DATA; Schema: public; Owner: gestion
--

INSERT INTO cronograma VALUES (1, 'Cronograma 1');


--
-- TOC entry 1999 (class 0 OID 21973)
-- Dependencies: 1581
-- Data for Name: cronograma_detalle; Type: TABLE DATA; Schema: public; Owner: gestion
--



--
-- TOC entry 2003 (class 0 OID 22008)
-- Dependencies: 1589
-- Data for Name: documento; Type: TABLE DATA; Schema: public; Owner: gestion
--



--
-- TOC entry 2011 (class 0 OID 22081)
-- Dependencies: 1605
-- Data for Name: log_sesion; Type: TABLE DATA; Schema: public; Owner: gestion
--

INSERT INTO log_sesion VALUES (1, 1, '2013-08-23 19:03:06.616', '127.0.0.1', 'N', NULL, 'Password incorrecto', 'admin');
INSERT INTO log_sesion VALUES (2, 1, '2013-08-23 19:03:14.31', '127.0.0.1', 'Y', 2, NULL, 'admin');
INSERT INTO log_sesion VALUES (3, 1, '2013-08-23 19:29:44.3', '127.0.0.1', 'Y', 3, NULL, 'admin');
INSERT INTO log_sesion VALUES (4, 1, '2013-08-23 19:29:49.481', '127.0.0.1', 'Y', 4, NULL, 'admin');


--
-- TOC entry 2016 (class 0 OID 22127)
-- Dependencies: 1615
-- Data for Name: notificaciones; Type: TABLE DATA; Schema: public; Owner: gestion
--



--
-- TOC entry 2014 (class 0 OID 22111)
-- Dependencies: 1611
-- Data for Name: observaciones; Type: TABLE DATA; Schema: public; Owner: gestion
--



--
-- TOC entry 2008 (class 0 OID 22057)
-- Dependencies: 1599
-- Data for Name: permiso; Type: TABLE DATA; Schema: public; Owner: gestion
--

INSERT INTO permiso VALUES (1, 'administracion');
INSERT INTO permiso VALUES (2, 'usuarios ver listado');
INSERT INTO permiso VALUES (3, 'usuarios crear');
INSERT INTO permiso VALUES (4, 'usuarios modificar');
INSERT INTO permiso VALUES (5, 'usuarios modificar roles');
INSERT INTO permiso VALUES (6, 'roles modificar permisos');
INSERT INTO permiso VALUES (7, 'roles ver listado');
INSERT INTO permiso VALUES (8, 'roles crear');
INSERT INTO permiso VALUES (9, 'roles modificar');
INSERT INTO permiso VALUES (10, 'usuarios modificar permisos');


--
-- TOC entry 2004 (class 0 OID 22019)
-- Dependencies: 1591
-- Data for Name: pregunta; Type: TABLE DATA; Schema: public; Owner: gestion
--

INSERT INTO pregunta VALUES (1, 'Tiene anticipo?');
INSERT INTO pregunta VALUES (2, 'Requiere evaluación in situ?');


--
-- TOC entry 1995 (class 0 OID 21935)
-- Dependencies: 1573
-- Data for Name: proceso; Type: TABLE DATA; Schema: public; Owner: gestion
--



--
-- TOC entry 2009 (class 0 OID 22065)
-- Dependencies: 1601
-- Data for Name: rol; Type: TABLE DATA; Schema: public; Owner: gestion
--

INSERT INTO rol VALUES (1, 'admin');
INSERT INTO rol VALUES (2, 'user');
INSERT INTO rol VALUES (3, 'Ingeniero');
INSERT INTO rol VALUES (4, 'Autocadista');
INSERT INTO rol VALUES (5, 'Ayudante de campo');
INSERT INTO rol VALUES (6, 'Administrativo');


--
-- TOC entry 2010 (class 0 OID 22073)
-- Dependencies: 1603
-- Data for Name: rol_permiso; Type: TABLE DATA; Schema: public; Owner: gestion
--

INSERT INTO rol_permiso VALUES (1, 1, 1);
INSERT INTO rol_permiso VALUES (2, 1, 2);
INSERT INTO rol_permiso VALUES (3, 1, 3);
INSERT INTO rol_permiso VALUES (4, 1, 4);
INSERT INTO rol_permiso VALUES (5, 1, 5);
INSERT INTO rol_permiso VALUES (6, 1, 6);
INSERT INTO rol_permiso VALUES (7, 1, 7);
INSERT INTO rol_permiso VALUES (8, 1, 8);
INSERT INTO rol_permiso VALUES (9, 1, 9);
INSERT INTO rol_permiso VALUES (10, 1, 10);


--
-- TOC entry 1997 (class 0 OID 21957)
-- Dependencies: 1577
-- Data for Name: tipo_alarma; Type: TABLE DATA; Schema: public; Owner: gestion
--

INSERT INTO tipo_alarma VALUES (8, 'alerta 1', 1, 0, 3, 1, 'Alerta');
INSERT INTO tipo_alarma VALUES (9, 'alarma 1', 1, 0, 3, 1, 'Alarma');


--
-- TOC entry 2005 (class 0 OID 22027)
-- Dependencies: 1593
-- Data for Name: usuario; Type: TABLE DATA; Schema: public; Owner: gestion
--

INSERT INTO usuario VALUES (3, 'usuario', 'usuario', 'usuario', NULL, 'A', 'usuario@email.com', NULL);
INSERT INTO usuario VALUES (1, 'admin', 'Admin', 'Admin', '2013-08-23 19:29:49.481', 'A', 'admin@ait.com.py', 4);


--
-- TOC entry 2006 (class 0 OID 22038)
-- Dependencies: 1595
-- Data for Name: usuario_password; Type: TABLE DATA; Schema: public; Owner: gestion
--

INSERT INTO usuario_password VALUES (1, 1, '21232f297a57a5a743894a0e4a801fc3');
INSERT INTO usuario_password VALUES (2, 3, 'f8032d5cae3de20fcec887f395ec9a6a');


--
-- TOC entry 2007 (class 0 OID 22049)
-- Dependencies: 1597
-- Data for Name: usuario_rol_permiso; Type: TABLE DATA; Schema: public; Owner: gestion
--

INSERT INTO usuario_rol_permiso VALUES (1, 1, NULL, 1);
INSERT INTO usuario_rol_permiso VALUES (2, 3, NULL, 2);


--
-- TOC entry 1956 (class 2606 OID 22124)
-- Dependencies: 1613 1613
-- Name: actividad_checlikst_detalle_pkey; Type: CONSTRAINT; Schema: public; Owner: gestion; Tablespace: 
--

ALTER TABLE ONLY actividad_checlikst_detalle
    ADD CONSTRAINT actividad_checlikst_detalle_pkey PRIMARY KEY (id);


--
-- TOC entry 1918 (class 2606 OID 21954)
-- Dependencies: 1575 1575
-- Name: actividad_pkey; Type: CONSTRAINT; Schema: public; Owner: gestion; Tablespace: 
--

ALTER TABLE ONLY actividad
    ADD CONSTRAINT actividad_pkey PRIMARY KEY (id);


--
-- TOC entry 1952 (class 2606 OID 22108)
-- Dependencies: 1609 1609
-- Name: aud_log_pkey; Type: CONSTRAINT; Schema: public; Owner: gestion; Tablespace: 
--

ALTER TABLE ONLY aud_log
    ADD CONSTRAINT aud_log_pkey PRIMARY KEY (id);


--
-- TOC entry 1950 (class 2606 OID 22097)
-- Dependencies: 1607 1607
-- Name: aud_tabla_pkey; Type: CONSTRAINT; Schema: public; Owner: gestion; Tablespace: 
--

ALTER TABLE ONLY aud_tabla
    ADD CONSTRAINT aud_tabla_pkey PRIMARY KEY (id);


--
-- TOC entry 1930 (class 2606 OID 22005)
-- Dependencies: 1587 1587
-- Name: checklist_detalle_pkey; Type: CONSTRAINT; Schema: public; Owner: gestion; Tablespace: 
--

ALTER TABLE ONLY checklist_detalle
    ADD CONSTRAINT checklist_detalle_pkey PRIMARY KEY (id);


--
-- TOC entry 1928 (class 2606 OID 21997)
-- Dependencies: 1585 1585
-- Name: checklist_pkey; Type: CONSTRAINT; Schema: public; Owner: gestion; Tablespace: 
--

ALTER TABLE ONLY checklist
    ADD CONSTRAINT checklist_pkey PRIMARY KEY (id);


--
-- TOC entry 1926 (class 2606 OID 21989)
-- Dependencies: 1583 1583
-- Name: cliente_pkey; Type: CONSTRAINT; Schema: public; Owner: gestion; Tablespace: 
--

ALTER TABLE ONLY cliente
    ADD CONSTRAINT cliente_pkey PRIMARY KEY (id);


--
-- TOC entry 1924 (class 2606 OID 21978)
-- Dependencies: 1581 1581
-- Name: cronograma_detalle_pkey; Type: CONSTRAINT; Schema: public; Owner: gestion; Tablespace: 
--

ALTER TABLE ONLY cronograma_detalle
    ADD CONSTRAINT cronograma_detalle_pkey PRIMARY KEY (id);


--
-- TOC entry 1922 (class 2606 OID 21970)
-- Dependencies: 1579 1579
-- Name: cronograma_pkey; Type: CONSTRAINT; Schema: public; Owner: gestion; Tablespace: 
--

ALTER TABLE ONLY cronograma
    ADD CONSTRAINT cronograma_pkey PRIMARY KEY (id);


--
-- TOC entry 1932 (class 2606 OID 22016)
-- Dependencies: 1589 1589
-- Name: documento_pkey; Type: CONSTRAINT; Schema: public; Owner: gestion; Tablespace: 
--

ALTER TABLE ONLY documento
    ADD CONSTRAINT documento_pkey PRIMARY KEY (id);


--
-- TOC entry 1948 (class 2606 OID 22089)
-- Dependencies: 1605 1605
-- Name: log_sesion_pkey; Type: CONSTRAINT; Schema: public; Owner: gestion; Tablespace: 
--

ALTER TABLE ONLY log_sesion
    ADD CONSTRAINT log_sesion_pkey PRIMARY KEY (id);


--
-- TOC entry 1958 (class 2606 OID 22135)
-- Dependencies: 1615 1615
-- Name: notificaciones_pkey; Type: CONSTRAINT; Schema: public; Owner: gestion; Tablespace: 
--

ALTER TABLE ONLY notificaciones
    ADD CONSTRAINT notificaciones_pkey PRIMARY KEY (id);


--
-- TOC entry 1954 (class 2606 OID 22116)
-- Dependencies: 1611 1611
-- Name: observaciones_pkey; Type: CONSTRAINT; Schema: public; Owner: gestion; Tablespace: 
--

ALTER TABLE ONLY observaciones
    ADD CONSTRAINT observaciones_pkey PRIMARY KEY (id);


--
-- TOC entry 1942 (class 2606 OID 22062)
-- Dependencies: 1599 1599
-- Name: permiso_pkey; Type: CONSTRAINT; Schema: public; Owner: gestion; Tablespace: 
--

ALTER TABLE ONLY permiso
    ADD CONSTRAINT permiso_pkey PRIMARY KEY (id);


--
-- TOC entry 1934 (class 2606 OID 22024)
-- Dependencies: 1591 1591
-- Name: pregunta_pkey; Type: CONSTRAINT; Schema: public; Owner: gestion; Tablespace: 
--

ALTER TABLE ONLY pregunta
    ADD CONSTRAINT pregunta_pkey PRIMARY KEY (id);


--
-- TOC entry 1916 (class 2606 OID 21943)
-- Dependencies: 1573 1573
-- Name: proceso_pkey; Type: CONSTRAINT; Schema: public; Owner: gestion; Tablespace: 
--

ALTER TABLE ONLY proceso
    ADD CONSTRAINT proceso_pkey PRIMARY KEY (id);


--
-- TOC entry 1946 (class 2606 OID 22078)
-- Dependencies: 1603 1603
-- Name: rol_permiso_pkey; Type: CONSTRAINT; Schema: public; Owner: gestion; Tablespace: 
--

ALTER TABLE ONLY rol_permiso
    ADD CONSTRAINT rol_permiso_pkey PRIMARY KEY (id);


--
-- TOC entry 1944 (class 2606 OID 22070)
-- Dependencies: 1601 1601
-- Name: rol_pkey; Type: CONSTRAINT; Schema: public; Owner: gestion; Tablespace: 
--

ALTER TABLE ONLY rol
    ADD CONSTRAINT rol_pkey PRIMARY KEY (id);


--
-- TOC entry 1920 (class 2606 OID 21962)
-- Dependencies: 1577 1577
-- Name: tipo_alarma_pkey; Type: CONSTRAINT; Schema: public; Owner: gestion; Tablespace: 
--

ALTER TABLE ONLY tipo_alarma
    ADD CONSTRAINT tipo_alarma_pkey PRIMARY KEY (id);


--
-- TOC entry 1938 (class 2606 OID 22046)
-- Dependencies: 1595 1595
-- Name: usuario_password_pkey; Type: CONSTRAINT; Schema: public; Owner: gestion; Tablespace: 
--

ALTER TABLE ONLY usuario_password
    ADD CONSTRAINT usuario_password_pkey PRIMARY KEY (id);


--
-- TOC entry 1936 (class 2606 OID 22035)
-- Dependencies: 1593 1593
-- Name: usuario_pkey; Type: CONSTRAINT; Schema: public; Owner: gestion; Tablespace: 
--

ALTER TABLE ONLY usuario
    ADD CONSTRAINT usuario_pkey PRIMARY KEY (id);


--
-- TOC entry 1940 (class 2606 OID 22054)
-- Dependencies: 1597 1597
-- Name: usuario_rol_permiso_pkey; Type: CONSTRAINT; Schema: public; Owner: gestion; Tablespace: 
--

ALTER TABLE ONLY usuario_rol_permiso
    ADD CONSTRAINT usuario_rol_permiso_pkey PRIMARY KEY (id);


--
-- TOC entry 1965 (class 2606 OID 22166)
-- Dependencies: 1575 1917 1575
-- Name: actividad_actividad_anterior_fkey; Type: FK CONSTRAINT; Schema: public; Owner: gestion
--

ALTER TABLE ONLY actividad
    ADD CONSTRAINT actividad_actividad_anterior_fkey FOREIGN KEY (actividad_anterior) REFERENCES actividad(id) ON UPDATE RESTRICT ON DELETE RESTRICT;


--
-- TOC entry 1967 (class 2606 OID 22176)
-- Dependencies: 1577 1919 1575
-- Name: actividad_alarma_fkey; Type: FK CONSTRAINT; Schema: public; Owner: gestion
--

ALTER TABLE ONLY actividad
    ADD CONSTRAINT actividad_alarma_fkey FOREIGN KEY (alarma) REFERENCES tipo_alarma(id) ON UPDATE RESTRICT ON DELETE RESTRICT;


--
-- TOC entry 1966 (class 2606 OID 22171)
-- Dependencies: 1575 1919 1577
-- Name: actividad_alerta_fkey; Type: FK CONSTRAINT; Schema: public; Owner: gestion
--

ALTER TABLE ONLY actividad
    ADD CONSTRAINT actividad_alerta_fkey FOREIGN KEY (alerta) REFERENCES tipo_alarma(id) ON UPDATE RESTRICT ON DELETE RESTRICT;


--
-- TOC entry 1963 (class 2606 OID 22156)
-- Dependencies: 1581 1575 1923
-- Name: actividad_cronograma_detalle_fkey; Type: FK CONSTRAINT; Schema: public; Owner: gestion
--

ALTER TABLE ONLY actividad
    ADD CONSTRAINT actividad_cronograma_detalle_fkey FOREIGN KEY (cronograma_detalle) REFERENCES cronograma_detalle(id) ON UPDATE RESTRICT ON DELETE RESTRICT;


--
-- TOC entry 1962 (class 2606 OID 22151)
-- Dependencies: 1575 1915 1573
-- Name: actividad_proceso_fkey; Type: FK CONSTRAINT; Schema: public; Owner: gestion
--

ALTER TABLE ONLY actividad
    ADD CONSTRAINT actividad_proceso_fkey FOREIGN KEY (proceso) REFERENCES proceso(id) ON UPDATE RESTRICT ON DELETE RESTRICT;


--
-- TOC entry 1964 (class 2606 OID 22161)
-- Dependencies: 1593 1935 1575
-- Name: actividad_responsable_fkey; Type: FK CONSTRAINT; Schema: public; Owner: gestion
--

ALTER TABLE ONLY actividad
    ADD CONSTRAINT actividad_responsable_fkey FOREIGN KEY (responsable) REFERENCES usuario(id) ON UPDATE RESTRICT ON DELETE RESTRICT;


--
-- TOC entry 1968 (class 2606 OID 22181)
-- Dependencies: 1917 1575 1575
-- Name: actividad_super_tarea_fkey; Type: FK CONSTRAINT; Schema: public; Owner: gestion
--

ALTER TABLE ONLY actividad
    ADD CONSTRAINT actividad_super_tarea_fkey FOREIGN KEY (super_tarea) REFERENCES actividad(id) ON UPDATE RESTRICT ON DELETE RESTRICT;


--
-- TOC entry 1990 (class 2606 OID 22291)
-- Dependencies: 1609 1949 1607
-- Name: aud_log_aud_tabla_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: gestion
--

ALTER TABLE ONLY aud_log
    ADD CONSTRAINT aud_log_aud_tabla_id_fkey FOREIGN KEY (aud_tabla_id) REFERENCES aud_tabla(id) ON UPDATE RESTRICT ON DELETE RESTRICT;


--
-- TOC entry 1991 (class 2606 OID 22296)
-- Dependencies: 1935 1609 1593
-- Name: aud_log_usuario_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: gestion
--

ALTER TABLE ONLY aud_log
    ADD CONSTRAINT aud_log_usuario_id_fkey FOREIGN KEY (usuario_id) REFERENCES usuario(id) ON UPDATE RESTRICT ON DELETE RESTRICT;


--
-- TOC entry 1980 (class 2606 OID 22241)
-- Dependencies: 1585 1927 1587
-- Name: checklist_detalle_checklist_fkey; Type: FK CONSTRAINT; Schema: public; Owner: gestion
--

ALTER TABLE ONLY checklist_detalle
    ADD CONSTRAINT checklist_detalle_checklist_fkey FOREIGN KEY (checklist) REFERENCES checklist(id) ON UPDATE RESTRICT ON DELETE RESTRICT;


--
-- TOC entry 1974 (class 2606 OID 22211)
-- Dependencies: 1919 1581 1577
-- Name: cronograma_detalle_alarma_fkey; Type: FK CONSTRAINT; Schema: public; Owner: gestion
--

ALTER TABLE ONLY cronograma_detalle
    ADD CONSTRAINT cronograma_detalle_alarma_fkey FOREIGN KEY (alarma) REFERENCES tipo_alarma(id) ON UPDATE RESTRICT ON DELETE RESTRICT;


--
-- TOC entry 1973 (class 2606 OID 22206)
-- Dependencies: 1577 1919 1581
-- Name: cronograma_detalle_alerta_fkey; Type: FK CONSTRAINT; Schema: public; Owner: gestion
--

ALTER TABLE ONLY cronograma_detalle
    ADD CONSTRAINT cronograma_detalle_alerta_fkey FOREIGN KEY (alerta) REFERENCES tipo_alarma(id) ON UPDATE RESTRICT ON DELETE RESTRICT;


--
-- TOC entry 1979 (class 2606 OID 22236)
-- Dependencies: 1585 1581 1927
-- Name: cronograma_detalle_checklist_fkey; Type: FK CONSTRAINT; Schema: public; Owner: gestion
--

ALTER TABLE ONLY cronograma_detalle
    ADD CONSTRAINT cronograma_detalle_checklist_fkey FOREIGN KEY (checklist) REFERENCES checklist(id) ON UPDATE RESTRICT ON DELETE RESTRICT;


--
-- TOC entry 1971 (class 2606 OID 22196)
-- Dependencies: 1581 1921 1579
-- Name: cronograma_detalle_cronograma_fkey; Type: FK CONSTRAINT; Schema: public; Owner: gestion
--

ALTER TABLE ONLY cronograma_detalle
    ADD CONSTRAINT cronograma_detalle_cronograma_fkey FOREIGN KEY (cronograma) REFERENCES cronograma(id) ON UPDATE RESTRICT ON DELETE RESTRICT;


--
-- TOC entry 1975 (class 2606 OID 22216)
-- Dependencies: 1591 1933 1581
-- Name: cronograma_detalle_pregunta_fkey; Type: FK CONSTRAINT; Schema: public; Owner: gestion
--

ALTER TABLE ONLY cronograma_detalle
    ADD CONSTRAINT cronograma_detalle_pregunta_fkey FOREIGN KEY (pregunta) REFERENCES pregunta(id) ON UPDATE RESTRICT ON DELETE RESTRICT;


--
-- TOC entry 1972 (class 2606 OID 22201)
-- Dependencies: 1943 1601 1581
-- Name: cronograma_detalle_rol_fkey; Type: FK CONSTRAINT; Schema: public; Owner: gestion
--

ALTER TABLE ONLY cronograma_detalle
    ADD CONSTRAINT cronograma_detalle_rol_fkey FOREIGN KEY (rol) REFERENCES rol(id) ON UPDATE RESTRICT ON DELETE RESTRICT;


--
-- TOC entry 1977 (class 2606 OID 22226)
-- Dependencies: 1923 1581 1581
-- Name: cronograma_detalle_tarea_no_fkey; Type: FK CONSTRAINT; Schema: public; Owner: gestion
--

ALTER TABLE ONLY cronograma_detalle
    ADD CONSTRAINT cronograma_detalle_tarea_no_fkey FOREIGN KEY (tarea_no) REFERENCES cronograma_detalle(id) ON UPDATE RESTRICT ON DELETE RESTRICT;


--
-- TOC entry 1976 (class 2606 OID 22221)
-- Dependencies: 1923 1581 1581
-- Name: cronograma_detalle_tarea_si_fkey; Type: FK CONSTRAINT; Schema: public; Owner: gestion
--

ALTER TABLE ONLY cronograma_detalle
    ADD CONSTRAINT cronograma_detalle_tarea_si_fkey FOREIGN KEY (tarea_si) REFERENCES cronograma_detalle(id) ON UPDATE RESTRICT ON DELETE RESTRICT;


--
-- TOC entry 1978 (class 2606 OID 22231)
-- Dependencies: 1923 1581 1581
-- Name: cronograma_detalle_tarea_siguiente_fkey; Type: FK CONSTRAINT; Schema: public; Owner: gestion
--

ALTER TABLE ONLY cronograma_detalle
    ADD CONSTRAINT cronograma_detalle_tarea_siguiente_fkey FOREIGN KEY (tarea_siguiente) REFERENCES cronograma_detalle(id) ON UPDATE RESTRICT ON DELETE RESTRICT;


--
-- TOC entry 1981 (class 2606 OID 22246)
-- Dependencies: 1593 1935 1589
-- Name: documento_usuario_bloqueo_fkey; Type: FK CONSTRAINT; Schema: public; Owner: gestion
--

ALTER TABLE ONLY documento
    ADD CONSTRAINT documento_usuario_bloqueo_fkey FOREIGN KEY (usuario_bloqueo) REFERENCES usuario(id) ON UPDATE RESTRICT ON DELETE RESTRICT;


--
-- TOC entry 1982 (class 2606 OID 22251)
-- Dependencies: 1935 1589 1593
-- Name: documento_usuario_desbloqueo_fkey; Type: FK CONSTRAINT; Schema: public; Owner: gestion
--

ALTER TABLE ONLY documento
    ADD CONSTRAINT documento_usuario_desbloqueo_fkey FOREIGN KEY (usuario_desbloqueo) REFERENCES usuario(id) ON UPDATE RESTRICT ON DELETE RESTRICT;


--
-- TOC entry 1989 (class 2606 OID 22286)
-- Dependencies: 1605 1593 1935
-- Name: log_sesion_usuario_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: gestion
--

ALTER TABLE ONLY log_sesion
    ADD CONSTRAINT log_sesion_usuario_id_fkey FOREIGN KEY (usuario_id) REFERENCES usuario(id) ON UPDATE RESTRICT ON DELETE RESTRICT;


--
-- TOC entry 1994 (class 2606 OID 22311)
-- Dependencies: 1593 1615 1935
-- Name: notificaciones_responsable_fkey; Type: FK CONSTRAINT; Schema: public; Owner: gestion
--

ALTER TABLE ONLY notificaciones
    ADD CONSTRAINT notificaciones_responsable_fkey FOREIGN KEY (responsable) REFERENCES usuario(id) ON UPDATE RESTRICT ON DELETE RESTRICT;


--
-- TOC entry 1993 (class 2606 OID 22306)
-- Dependencies: 1935 1615 1593
-- Name: notificaciones_usuario_fkey; Type: FK CONSTRAINT; Schema: public; Owner: gestion
--

ALTER TABLE ONLY notificaciones
    ADD CONSTRAINT notificaciones_usuario_fkey FOREIGN KEY (usuario) REFERENCES usuario(id) ON UPDATE RESTRICT ON DELETE RESTRICT;


--
-- TOC entry 1992 (class 2606 OID 22301)
-- Dependencies: 1593 1611 1935
-- Name: observaciones_usuario_fkey; Type: FK CONSTRAINT; Schema: public; Owner: gestion
--

ALTER TABLE ONLY observaciones
    ADD CONSTRAINT observaciones_usuario_fkey FOREIGN KEY (usuario) REFERENCES usuario(id) ON UPDATE RESTRICT ON DELETE RESTRICT;


--
-- TOC entry 1960 (class 2606 OID 22141)
-- Dependencies: 1573 1583 1925
-- Name: proceso_cliente_fkey; Type: FK CONSTRAINT; Schema: public; Owner: gestion
--

ALTER TABLE ONLY proceso
    ADD CONSTRAINT proceso_cliente_fkey FOREIGN KEY (cliente) REFERENCES cliente(id) ON UPDATE RESTRICT ON DELETE RESTRICT;


--
-- TOC entry 1961 (class 2606 OID 22146)
-- Dependencies: 1921 1573 1579
-- Name: proceso_cronograma_fkey; Type: FK CONSTRAINT; Schema: public; Owner: gestion
--

ALTER TABLE ONLY proceso
    ADD CONSTRAINT proceso_cronograma_fkey FOREIGN KEY (cronograma) REFERENCES cronograma(id) ON UPDATE RESTRICT ON DELETE RESTRICT;


--
-- TOC entry 1959 (class 2606 OID 22136)
-- Dependencies: 1935 1573 1593
-- Name: proceso_responsable_fkey; Type: FK CONSTRAINT; Schema: public; Owner: gestion
--

ALTER TABLE ONLY proceso
    ADD CONSTRAINT proceso_responsable_fkey FOREIGN KEY (responsable) REFERENCES usuario(id) ON UPDATE RESTRICT ON DELETE RESTRICT;


--
-- TOC entry 1988 (class 2606 OID 22281)
-- Dependencies: 1941 1599 1603
-- Name: rol_permiso_permiso_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: gestion
--

ALTER TABLE ONLY rol_permiso
    ADD CONSTRAINT rol_permiso_permiso_id_fkey FOREIGN KEY (permiso_id) REFERENCES permiso(id) ON UPDATE RESTRICT ON DELETE RESTRICT;


--
-- TOC entry 1987 (class 2606 OID 22276)
-- Dependencies: 1601 1603 1943
-- Name: rol_permiso_rol_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: gestion
--

ALTER TABLE ONLY rol_permiso
    ADD CONSTRAINT rol_permiso_rol_id_fkey FOREIGN KEY (rol_id) REFERENCES rol(id) ON UPDATE RESTRICT ON DELETE RESTRICT;


--
-- TOC entry 1969 (class 2606 OID 22186)
-- Dependencies: 1593 1577 1935
-- Name: tipo_alarma_responsable1_fkey; Type: FK CONSTRAINT; Schema: public; Owner: gestion
--

ALTER TABLE ONLY tipo_alarma
    ADD CONSTRAINT tipo_alarma_responsable1_fkey FOREIGN KEY (responsable1) REFERENCES usuario(id) ON UPDATE RESTRICT ON DELETE RESTRICT;


--
-- TOC entry 1970 (class 2606 OID 22191)
-- Dependencies: 1593 1577 1935
-- Name: tipo_alarma_responsable2_fkey; Type: FK CONSTRAINT; Schema: public; Owner: gestion
--

ALTER TABLE ONLY tipo_alarma
    ADD CONSTRAINT tipo_alarma_responsable2_fkey FOREIGN KEY (responsable2) REFERENCES usuario(id) ON UPDATE RESTRICT ON DELETE RESTRICT;


--
-- TOC entry 1983 (class 2606 OID 22256)
-- Dependencies: 1935 1593 1595
-- Name: usuario_password_usuario_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: gestion
--

ALTER TABLE ONLY usuario_password
    ADD CONSTRAINT usuario_password_usuario_id_fkey FOREIGN KEY (usuario_id) REFERENCES usuario(id) ON UPDATE RESTRICT ON DELETE RESTRICT;


--
-- TOC entry 1985 (class 2606 OID 22266)
-- Dependencies: 1599 1597 1941
-- Name: usuario_rol_permiso_permiso_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: gestion
--

ALTER TABLE ONLY usuario_rol_permiso
    ADD CONSTRAINT usuario_rol_permiso_permiso_id_fkey FOREIGN KEY (permiso_id) REFERENCES permiso(id) ON UPDATE RESTRICT ON DELETE RESTRICT;


--
-- TOC entry 1986 (class 2606 OID 22271)
-- Dependencies: 1601 1597 1943
-- Name: usuario_rol_permiso_rol_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: gestion
--

ALTER TABLE ONLY usuario_rol_permiso
    ADD CONSTRAINT usuario_rol_permiso_rol_id_fkey FOREIGN KEY (rol_id) REFERENCES rol(id) ON UPDATE RESTRICT ON DELETE RESTRICT;


--
-- TOC entry 1984 (class 2606 OID 22261)
-- Dependencies: 1935 1597 1593
-- Name: usuario_rol_permiso_usuario_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: gestion
--

ALTER TABLE ONLY usuario_rol_permiso
    ADD CONSTRAINT usuario_rol_permiso_usuario_id_fkey FOREIGN KEY (usuario_id) REFERENCES usuario(id) ON UPDATE RESTRICT ON DELETE RESTRICT;


--
-- TOC entry 2021 (class 0 OID 0)
-- Dependencies: 5
-- Name: public; Type: ACL; Schema: -; Owner: postgres
--

REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM postgres;
GRANT ALL ON SCHEMA public TO postgres;
GRANT ALL ON SCHEMA public TO PUBLIC;


-- Completed on 2013-08-23 19:31:35

--
-- PostgreSQL database dump complete
--

