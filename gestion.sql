--
-- TOC entry 161 (class 1259 OID 206815)
-- Dependencies: 6
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
-- TOC entry 162 (class 1259 OID 206821)
-- Dependencies: 6
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
-- TOC entry 163 (class 1259 OID 206824)
-- Dependencies: 162 6
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
-- TOC entry 2177 (class 0 OID 0)
-- Dependencies: 163
-- Name: actividad_checlikst_detalle_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: gestion
--

ALTER SEQUENCE actividad_checlikst_detalle_id_seq OWNED BY actividad_checlikst_detalle.id;


--
-- TOC entry 164 (class 1259 OID 206826)
-- Dependencies: 6 161
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
-- TOC entry 2178 (class 0 OID 0)
-- Dependencies: 164
-- Name: actividad_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: gestion
--

ALTER SEQUENCE actividad_id_seq OWNED BY actividad.id;


--
-- TOC entry 165 (class 1259 OID 206828)
-- Dependencies: 6
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
-- TOC entry 166 (class 1259 OID 206834)
-- Dependencies: 6 165
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
-- TOC entry 2179 (class 0 OID 0)
-- Dependencies: 166
-- Name: aud_log_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: gestion
--

ALTER SEQUENCE aud_log_id_seq OWNED BY aud_log.id;


--
-- TOC entry 167 (class 1259 OID 206836)
-- Dependencies: 6
-- Name: aud_tabla; Type: TABLE; Schema: public; Owner: gestion; Tablespace: 
--

CREATE TABLE aud_tabla (
    id bigint NOT NULL,
    nombre character varying(50)
);


ALTER TABLE public.aud_tabla OWNER TO gestion;

--
-- TOC entry 168 (class 1259 OID 206839)
-- Dependencies: 6 167
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
-- TOC entry 2180 (class 0 OID 0)
-- Dependencies: 168
-- Name: aud_tabla_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: gestion
--

ALTER SEQUENCE aud_tabla_id_seq OWNED BY aud_tabla.id;


--
-- TOC entry 169 (class 1259 OID 206841)
-- Dependencies: 6
-- Name: checklist; Type: TABLE; Schema: public; Owner: gestion; Tablespace: 
--

CREATE TABLE checklist (
    id bigint NOT NULL,
    descripcion character varying(50)
);


ALTER TABLE public.checklist OWNER TO gestion;

--
-- TOC entry 170 (class 1259 OID 206844)
-- Dependencies: 6
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
-- TOC entry 171 (class 1259 OID 206847)
-- Dependencies: 6 170
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
-- TOC entry 2181 (class 0 OID 0)
-- Dependencies: 171
-- Name: checklist_detalle_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: gestion
--

ALTER SEQUENCE checklist_detalle_id_seq OWNED BY checklist_detalle.id;


--
-- TOC entry 172 (class 1259 OID 206849)
-- Dependencies: 6 169
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
-- TOC entry 2182 (class 0 OID 0)
-- Dependencies: 172
-- Name: checklist_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: gestion
--

ALTER SEQUENCE checklist_id_seq OWNED BY checklist.id;


--
-- TOC entry 173 (class 1259 OID 206851)
-- Dependencies: 6
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
-- TOC entry 174 (class 1259 OID 206857)
-- Dependencies: 173 6
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
-- TOC entry 2183 (class 0 OID 0)
-- Dependencies: 174
-- Name: cliente_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: gestion
--

ALTER SEQUENCE cliente_id_seq OWNED BY cliente.id;


--
-- TOC entry 175 (class 1259 OID 206859)
-- Dependencies: 6
-- Name: cronograma; Type: TABLE; Schema: public; Owner: gestion; Tablespace: 
--

CREATE TABLE cronograma (
    id bigint NOT NULL,
    nombre character varying(50)
);


ALTER TABLE public.cronograma OWNER TO gestion;

--
-- TOC entry 176 (class 1259 OID 206862)
-- Dependencies: 6
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
-- TOC entry 177 (class 1259 OID 206865)
-- Dependencies: 176 6
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
-- TOC entry 2184 (class 0 OID 0)
-- Dependencies: 177
-- Name: cronograma_detalle_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: gestion
--

ALTER SEQUENCE cronograma_detalle_id_seq OWNED BY cronograma_detalle.id;


--
-- TOC entry 178 (class 1259 OID 206867)
-- Dependencies: 175 6
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
-- TOC entry 2185 (class 0 OID 0)
-- Dependencies: 178
-- Name: cronograma_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: gestion
--

ALTER SEQUENCE cronograma_id_seq OWNED BY cronograma.id;


--
-- TOC entry 179 (class 1259 OID 206869)
-- Dependencies: 6
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
-- TOC entry 180 (class 1259 OID 206875)
-- Dependencies: 6 179
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
-- TOC entry 2186 (class 0 OID 0)
-- Dependencies: 180
-- Name: documento_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: gestion
--

ALTER SEQUENCE documento_id_seq OWNED BY documento.id;


--
-- TOC entry 181 (class 1259 OID 206877)
-- Dependencies: 6
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
-- TOC entry 182 (class 1259 OID 206883)
-- Dependencies: 6 181
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
-- TOC entry 2187 (class 0 OID 0)
-- Dependencies: 182
-- Name: log_sesion_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: gestion
--

ALTER SEQUENCE log_sesion_id_seq OWNED BY log_sesion.id;


--
-- TOC entry 183 (class 1259 OID 206885)
-- Dependencies: 6
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
-- TOC entry 184 (class 1259 OID 206891)
-- Dependencies: 183 6
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
-- TOC entry 2188 (class 0 OID 0)
-- Dependencies: 184
-- Name: notificaciones_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: gestion
--

ALTER SEQUENCE notificaciones_id_seq OWNED BY notificaciones.id;


--
-- TOC entry 185 (class 1259 OID 206893)
-- Dependencies: 6
-- Name: observacion; Type: TABLE; Schema: public; Owner: gestion; Tablespace: 
--

CREATE TABLE observacion (
    id bigint NOT NULL,
    descripcion character varying(50),
    usuario bigint,
    fecha_hora timestamp without time zone,
    entidad character varying(50),
    id_entidad bigint
);


ALTER TABLE public.observacion OWNER TO gestion;

--
-- TOC entry 186 (class 1259 OID 206896)
-- Dependencies: 6 185
-- Name: observacion_id_seq; Type: SEQUENCE; Schema: public; Owner: gestion
--

CREATE SEQUENCE observacion_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.observacion_id_seq OWNER TO gestion;

--
-- TOC entry 2189 (class 0 OID 0)
-- Dependencies: 186
-- Name: observacion_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: gestion
--

ALTER SEQUENCE observacion_id_seq OWNED BY observacion.id;


--
-- TOC entry 187 (class 1259 OID 206898)
-- Dependencies: 6
-- Name: permiso; Type: TABLE; Schema: public; Owner: gestion; Tablespace: 
--

CREATE TABLE permiso (
    id bigint NOT NULL,
    descripcion character varying(50)
);


ALTER TABLE public.permiso OWNER TO gestion;

--
-- TOC entry 188 (class 1259 OID 206901)
-- Dependencies: 187 6
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
-- TOC entry 2190 (class 0 OID 0)
-- Dependencies: 188
-- Name: permiso_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: gestion
--

ALTER SEQUENCE permiso_id_seq OWNED BY permiso.id;


--
-- TOC entry 189 (class 1259 OID 206903)
-- Dependencies: 6
-- Name: pregunta; Type: TABLE; Schema: public; Owner: gestion; Tablespace: 
--

CREATE TABLE pregunta (
    id bigint NOT NULL,
    descripcion character varying(50)
);


ALTER TABLE public.pregunta OWNER TO gestion;

--
-- TOC entry 190 (class 1259 OID 206906)
-- Dependencies: 189 6
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
-- TOC entry 2191 (class 0 OID 0)
-- Dependencies: 190
-- Name: pregunta_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: gestion
--

ALTER SEQUENCE pregunta_id_seq OWNED BY pregunta.id;


--
-- TOC entry 191 (class 1259 OID 206908)
-- Dependencies: 6
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
-- TOC entry 192 (class 1259 OID 206914)
-- Dependencies: 6 191
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
-- TOC entry 2192 (class 0 OID 0)
-- Dependencies: 192
-- Name: proceso_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: gestion
--

ALTER SEQUENCE proceso_id_seq OWNED BY proceso.id;


--
-- TOC entry 193 (class 1259 OID 206916)
-- Dependencies: 6
-- Name: rol; Type: TABLE; Schema: public; Owner: gestion; Tablespace: 
--

CREATE TABLE rol (
    id bigint NOT NULL,
    descripcion character varying(50)
);


ALTER TABLE public.rol OWNER TO gestion;

--
-- TOC entry 194 (class 1259 OID 206919)
-- Dependencies: 193 6
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
-- TOC entry 2193 (class 0 OID 0)
-- Dependencies: 194
-- Name: rol_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: gestion
--

ALTER SEQUENCE rol_id_seq OWNED BY rol.id;


--
-- TOC entry 195 (class 1259 OID 206921)
-- Dependencies: 6
-- Name: rol_permiso; Type: TABLE; Schema: public; Owner: gestion; Tablespace: 
--

CREATE TABLE rol_permiso (
    id bigint NOT NULL,
    rol_id bigint,
    permiso_id bigint
);


ALTER TABLE public.rol_permiso OWNER TO gestion;

--
-- TOC entry 196 (class 1259 OID 206924)
-- Dependencies: 6 195
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
-- TOC entry 2194 (class 0 OID 0)
-- Dependencies: 196
-- Name: rol_permiso_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: gestion
--

ALTER SEQUENCE rol_permiso_id_seq OWNED BY rol_permiso.id;


--
-- TOC entry 197 (class 1259 OID 206926)
-- Dependencies: 6
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
-- TOC entry 198 (class 1259 OID 206929)
-- Dependencies: 197 6
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
-- TOC entry 2195 (class 0 OID 0)
-- Dependencies: 198
-- Name: tipo_alarma_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: gestion
--

ALTER SEQUENCE tipo_alarma_id_seq OWNED BY tipo_alarma.id;


--
-- TOC entry 199 (class 1259 OID 206931)
-- Dependencies: 6
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
-- TOC entry 200 (class 1259 OID 206937)
-- Dependencies: 6 199
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
-- TOC entry 2196 (class 0 OID 0)
-- Dependencies: 200
-- Name: usuario_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: gestion
--

ALTER SEQUENCE usuario_id_seq OWNED BY usuario.id;


--
-- TOC entry 201 (class 1259 OID 206939)
-- Dependencies: 6
-- Name: usuario_password; Type: TABLE; Schema: public; Owner: gestion; Tablespace: 
--

CREATE TABLE usuario_password (
    id bigint NOT NULL,
    usuario_id bigint,
    password character varying(500)
);


ALTER TABLE public.usuario_password OWNER TO gestion;

--
-- TOC entry 202 (class 1259 OID 206945)
-- Dependencies: 6 201
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
-- TOC entry 2197 (class 0 OID 0)
-- Dependencies: 202
-- Name: usuario_password_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: gestion
--

ALTER SEQUENCE usuario_password_id_seq OWNED BY usuario_password.id;


--
-- TOC entry 203 (class 1259 OID 206947)
-- Dependencies: 6
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
-- TOC entry 204 (class 1259 OID 206950)
-- Dependencies: 203 6
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
-- TOC entry 2198 (class 0 OID 0)
-- Dependencies: 204
-- Name: usuario_rol_permiso_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: gestion
--

ALTER SEQUENCE usuario_rol_permiso_id_seq OWNED BY usuario_rol_permiso.id;


--
-- TOC entry 2023 (class 2604 OID 206952)
-- Dependencies: 164 161
-- Name: id; Type: DEFAULT; Schema: public; Owner: gestion
--

ALTER TABLE ONLY actividad ALTER COLUMN id SET DEFAULT nextval('actividad_id_seq'::regclass);


--
-- TOC entry 2024 (class 2604 OID 206953)
-- Dependencies: 163 162
-- Name: id; Type: DEFAULT; Schema: public; Owner: gestion
--

ALTER TABLE ONLY actividad_checlikst_detalle ALTER COLUMN id SET DEFAULT nextval('actividad_checlikst_detalle_id_seq'::regclass);


--
-- TOC entry 2025 (class 2604 OID 206954)
-- Dependencies: 166 165
-- Name: id; Type: DEFAULT; Schema: public; Owner: gestion
--

ALTER TABLE ONLY aud_log ALTER COLUMN id SET DEFAULT nextval('aud_log_id_seq'::regclass);


--
-- TOC entry 2026 (class 2604 OID 206955)
-- Dependencies: 168 167
-- Name: id; Type: DEFAULT; Schema: public; Owner: gestion
--

ALTER TABLE ONLY aud_tabla ALTER COLUMN id SET DEFAULT nextval('aud_tabla_id_seq'::regclass);


--
-- TOC entry 2027 (class 2604 OID 206956)
-- Dependencies: 172 169
-- Name: id; Type: DEFAULT; Schema: public; Owner: gestion
--

ALTER TABLE ONLY checklist ALTER COLUMN id SET DEFAULT nextval('checklist_id_seq'::regclass);


--
-- TOC entry 2028 (class 2604 OID 206957)
-- Dependencies: 171 170
-- Name: id; Type: DEFAULT; Schema: public; Owner: gestion
--

ALTER TABLE ONLY checklist_detalle ALTER COLUMN id SET DEFAULT nextval('checklist_detalle_id_seq'::regclass);


--
-- TOC entry 2029 (class 2604 OID 206958)
-- Dependencies: 174 173
-- Name: id; Type: DEFAULT; Schema: public; Owner: gestion
--

ALTER TABLE ONLY cliente ALTER COLUMN id SET DEFAULT nextval('cliente_id_seq'::regclass);


--
-- TOC entry 2030 (class 2604 OID 206959)
-- Dependencies: 178 175
-- Name: id; Type: DEFAULT; Schema: public; Owner: gestion
--

ALTER TABLE ONLY cronograma ALTER COLUMN id SET DEFAULT nextval('cronograma_id_seq'::regclass);


--
-- TOC entry 2031 (class 2604 OID 206960)
-- Dependencies: 177 176
-- Name: id; Type: DEFAULT; Schema: public; Owner: gestion
--

ALTER TABLE ONLY cronograma_detalle ALTER COLUMN id SET DEFAULT nextval('cronograma_detalle_id_seq'::regclass);


--
-- TOC entry 2032 (class 2604 OID 206961)
-- Dependencies: 180 179
-- Name: id; Type: DEFAULT; Schema: public; Owner: gestion
--

ALTER TABLE ONLY documento ALTER COLUMN id SET DEFAULT nextval('documento_id_seq'::regclass);


--
-- TOC entry 2033 (class 2604 OID 206962)
-- Dependencies: 182 181
-- Name: id; Type: DEFAULT; Schema: public; Owner: gestion
--

ALTER TABLE ONLY log_sesion ALTER COLUMN id SET DEFAULT nextval('log_sesion_id_seq'::regclass);


--
-- TOC entry 2034 (class 2604 OID 206963)
-- Dependencies: 184 183
-- Name: id; Type: DEFAULT; Schema: public; Owner: gestion
--

ALTER TABLE ONLY notificaciones ALTER COLUMN id SET DEFAULT nextval('notificaciones_id_seq'::regclass);


--
-- TOC entry 2035 (class 2604 OID 206964)
-- Dependencies: 186 185
-- Name: id; Type: DEFAULT; Schema: public; Owner: gestion
--

ALTER TABLE ONLY observacion ALTER COLUMN id SET DEFAULT nextval('observacion_id_seq'::regclass);


--
-- TOC entry 2036 (class 2604 OID 206965)
-- Dependencies: 188 187
-- Name: id; Type: DEFAULT; Schema: public; Owner: gestion
--

ALTER TABLE ONLY permiso ALTER COLUMN id SET DEFAULT nextval('permiso_id_seq'::regclass);


--
-- TOC entry 2037 (class 2604 OID 206966)
-- Dependencies: 190 189
-- Name: id; Type: DEFAULT; Schema: public; Owner: gestion
--

ALTER TABLE ONLY pregunta ALTER COLUMN id SET DEFAULT nextval('pregunta_id_seq'::regclass);


--
-- TOC entry 2038 (class 2604 OID 206967)
-- Dependencies: 192 191
-- Name: id; Type: DEFAULT; Schema: public; Owner: gestion
--

ALTER TABLE ONLY proceso ALTER COLUMN id SET DEFAULT nextval('proceso_id_seq'::regclass);


--
-- TOC entry 2039 (class 2604 OID 206968)
-- Dependencies: 194 193
-- Name: id; Type: DEFAULT; Schema: public; Owner: gestion
--

ALTER TABLE ONLY rol ALTER COLUMN id SET DEFAULT nextval('rol_id_seq'::regclass);


--
-- TOC entry 2040 (class 2604 OID 206969)
-- Dependencies: 196 195
-- Name: id; Type: DEFAULT; Schema: public; Owner: gestion
--

ALTER TABLE ONLY rol_permiso ALTER COLUMN id SET DEFAULT nextval('rol_permiso_id_seq'::regclass);


--
-- TOC entry 2041 (class 2604 OID 206970)
-- Dependencies: 198 197
-- Name: id; Type: DEFAULT; Schema: public; Owner: gestion
--

ALTER TABLE ONLY tipo_alarma ALTER COLUMN id SET DEFAULT nextval('tipo_alarma_id_seq'::regclass);


--
-- TOC entry 2042 (class 2604 OID 206971)
-- Dependencies: 200 199
-- Name: id; Type: DEFAULT; Schema: public; Owner: gestion
--

ALTER TABLE ONLY usuario ALTER COLUMN id SET DEFAULT nextval('usuario_id_seq'::regclass);


--
-- TOC entry 2043 (class 2604 OID 206972)
-- Dependencies: 202 201
-- Name: id; Type: DEFAULT; Schema: public; Owner: gestion
--

ALTER TABLE ONLY usuario_password ALTER COLUMN id SET DEFAULT nextval('usuario_password_id_seq'::regclass);


--
-- TOC entry 2044 (class 2604 OID 206973)
-- Dependencies: 204 203
-- Name: id; Type: DEFAULT; Schema: public; Owner: gestion
--

ALTER TABLE ONLY usuario_rol_permiso ALTER COLUMN id SET DEFAULT nextval('usuario_rol_permiso_id_seq'::regclass);


--
-- TOC entry 2125 (class 0 OID 206815)
-- Dependencies: 161 2169
-- Data for Name: actividad; Type: TABLE DATA; Schema: public; Owner: gestion
--

INSERT INTO actividad (id, proceso, nro_actividad, cronograma_detalle, descripcion, responsable, fecha_creacion, fecha_inicio_previsto, fecha_inicio_reprogramado, motivo_reprogramacion_inicio, fecha_fin_prevista, fecha_fin_reprogramada, motivo_reprogramacion, fecha_devuelta, fecha_resuelta, fecha_cancelacion, pregunta, respuesta, estado, checklist_completo, actividad_anterior, alerta, alarma, super_tarea) VALUES (27, 11, '1/2013', 1, 'Asignación de responsable', NULL, '2013-09-08 21:48:33.788', '2013-09-08 21:48:33.788', NULL, NULL, '2013-09-10 21:48:33.788', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'NUE', NULL, NULL, NULL, NULL, NULL);


--
-- TOC entry 2126 (class 0 OID 206821)
-- Dependencies: 162 2169
-- Data for Name: actividad_checlikst_detalle; Type: TABLE DATA; Schema: public; Owner: gestion
--



--
-- TOC entry 2199 (class 0 OID 0)
-- Dependencies: 163
-- Name: actividad_checlikst_detalle_id_seq; Type: SEQUENCE SET; Schema: public; Owner: gestion
--

SELECT pg_catalog.setval('actividad_checlikst_detalle_id_seq', 1, false);


--
-- TOC entry 2200 (class 0 OID 0)
-- Dependencies: 164
-- Name: actividad_id_seq; Type: SEQUENCE SET; Schema: public; Owner: gestion
--

SELECT pg_catalog.setval('actividad_id_seq', 27, true);


--
-- TOC entry 2129 (class 0 OID 206828)
-- Dependencies: 165 2169
-- Data for Name: aud_log; Type: TABLE DATA; Schema: public; Owner: gestion
--

INSERT INTO aud_log (id, aud_tabla_id, registro_id, valor_anterior, valor_nuevo, fecha, usuario_id, ip) VALUES (2, NULL, 3, 'Registro Nuevo', 'usuarioId=3#apellido=usuario#email=usuario@email.com#estado=A#nombre=usuario#nroSesion=null#ultimoLogin=null#usuario=usuario', '2013-08-23 19:04:26.844', 1, '127.0.0.1');
INSERT INTO aud_log (id, aud_tabla_id, registro_id, valor_anterior, valor_nuevo, fecha, usuario_id, ip) VALUES (4, NULL, 3, 'usuarioId=3#apellido=usuario#email=usuario@email.com#estado=A#nombre=usuario#nroSesion=null#ultimoLogin=null#usuario=usuario', 'usuarioId=3#apellido=usuario#email=usuario@email.com#estado=A#nombre=usuario#nroSesion=null#ultimoLogin=null#usuario=usuario', '2013-08-23 19:04:45.057', 1, '127.0.0.1');
INSERT INTO aud_log (id, aud_tabla_id, registro_id, valor_anterior, valor_nuevo, fecha, usuario_id, ip) VALUES (5, NULL, 2, 'Registro Nuevo', 'usuarioRolPermisoId=2#rol.rolId=2#usuario.usuarioId=3', '2013-08-23 19:04:45.084', 1, '127.0.0.1');
INSERT INTO aud_log (id, aud_tabla_id, registro_id, valor_anterior, valor_nuevo, fecha, usuario_id, ip) VALUES (6, NULL, 3, 'Registro Nuevo', 'rolId=3#descripcion=Ingeniero', '2013-08-23 19:05:09.587', 1, '127.0.0.1');
INSERT INTO aud_log (id, aud_tabla_id, registro_id, valor_anterior, valor_nuevo, fecha, usuario_id, ip) VALUES (7, NULL, 4, 'Registro Nuevo', 'rolId=4#descripcion=Autocadista', '2013-08-23 19:05:21.63', 1, '127.0.0.1');
INSERT INTO aud_log (id, aud_tabla_id, registro_id, valor_anterior, valor_nuevo, fecha, usuario_id, ip) VALUES (8, NULL, 5, 'Registro Nuevo', 'rolId=5#descripcion=Ayudante de campo', '2013-08-23 19:05:28.26', 1, '127.0.0.1');
INSERT INTO aud_log (id, aud_tabla_id, registro_id, valor_anterior, valor_nuevo, fecha, usuario_id, ip) VALUES (9, NULL, 6, 'Registro Nuevo', 'rolId=6#descripcion=Administrativo', '2013-08-23 19:05:36.698', 1, '127.0.0.1');
INSERT INTO aud_log (id, aud_tabla_id, registro_id, valor_anterior, valor_nuevo, fecha, usuario_id, ip) VALUES (17, NULL, 8, 'Registro Nuevo', 'tipoAlarmaId=8#descripcion=alerta 1#tipo=Alerta#dias=1#horas=0#responsable1.usuarioId=3#responsable2.usuarioId=1', '2013-08-23 19:07:48.268', 1, '127.0.0.1');
INSERT INTO aud_log (id, aud_tabla_id, registro_id, valor_anterior, valor_nuevo, fecha, usuario_id, ip) VALUES (18, NULL, 9, 'Registro Nuevo', 'tipoAlarmaId=9#descripcion=alarma 1#tipo=Alarma#dias=1#horas=0#responsable1.usuarioId=3#responsable2.usuarioId=1', '2013-08-23 19:08:10.437', 1, '127.0.0.1');
INSERT INTO aud_log (id, aud_tabla_id, registro_id, valor_anterior, valor_nuevo, fecha, usuario_id, ip) VALUES (19, NULL, 1, 'Registro Nuevo', 'clienteId=1#nombre=AIT S.A.#direccion=Gral. Santos 843#telefono=201014#personaContacto=Margarita Rojas', '2013-08-23 19:08:48.633', 1, '127.0.0.1');
INSERT INTO aud_log (id, aud_tabla_id, registro_id, valor_anterior, valor_nuevo, fecha, usuario_id, ip) VALUES (20, NULL, 1, 'Registro Nuevo', 'preguntaId=1#descripcion=Tiene anticipo?', '2013-08-23 19:09:00.938', 1, '127.0.0.1');
INSERT INTO aud_log (id, aud_tabla_id, registro_id, valor_anterior, valor_nuevo, fecha, usuario_id, ip) VALUES (21, NULL, 2, 'Registro Nuevo', 'preguntaId=2#descripcion=Requiere evaluación in situ?', '2013-08-23 19:09:12.715', 1, '127.0.0.1');
INSERT INTO aud_log (id, aud_tabla_id, registro_id, valor_anterior, valor_nuevo, fecha, usuario_id, ip) VALUES (22, NULL, 1, 'Registro Nuevo', 'cronogramaId=1#nombre=Cronograma 1', '2013-08-23 19:09:43.419', 1, '127.0.0.1');
INSERT INTO aud_log (id, aud_tabla_id, registro_id, valor_anterior, valor_nuevo, fecha, usuario_id, ip) VALUES (23, NULL, 1, 'Registro Nuevo', 'procesoId=1#nroProceso=1/2013#responsable.usuarioId=3#cliente.clienteId=1#cronograma.cronogramaId=1#estado=P#descripcion=test#fechaInicioContratual=03/09/2013 00:00:00#fechaInicioReal=03/09/2013 00:00:00#fechaFinPrevista=null#fechaFinReprogramada=null#motivoReprogramacion=#clienteNotificado=Si', '2013-09-03 19:16:30.471', 1, '127.0.0.1');
INSERT INTO aud_log (id, aud_tabla_id, registro_id, valor_anterior, valor_nuevo, fecha, usuario_id, ip) VALUES (24, NULL, 11, 'actividadId=11#nroActividad=1/2013#cronogramaDetalle.cronogramaDetalleId=1#descripcion=Asignacion de responsable#fechaCreacion=04/09/2013 21:52:13#fechaInicioPrevisto=04/09/2013 21:52:00#fechaInicioReprogramado=null#motivoReprogramacionInicio=null#fechaFinPrevista=09/09/2013 21:52:00#fechaFinReprogramada=null#motivoReprogramacion=null#fechaDevuelta=null#fechaResuelta=null#fechaCancelacion=null#pregunta=null#respuesta=null#estado=PRO#checklistCompleto=null#alerta.tipoAlarmaId=8#alarma.tipoAlarmaId=9#master.procesoId=1', 'actividadId=11#nroActividad=1/2013#cronogramaDetalle.cronogramaDetalleId=1#descripcion=Asignacion de responsable#fechaCreacion=04/09/2013 21:52:13#fechaInicioPrevisto=04/09/2013 21:52:00#fechaInicioReprogramado=null#motivoReprogramacionInicio=null#fechaFinPrevista=09/09/2013 21:52:00#fechaFinReprogramada=null#motivoReprogramacion=null#fechaDevuelta=null#fechaResuelta=null#fechaCancelacion=null#pregunta=null#respuesta=null#estado=PRO#checklistCompleto=null#alerta.tipoAlarmaId=8#alarma.tipoAlarmaId=9#master.procesoId=1', '2013-09-05 00:34:19.883', 1, '127.0.0.1');
INSERT INTO aud_log (id, aud_tabla_id, registro_id, valor_anterior, valor_nuevo, fecha, usuario_id, ip) VALUES (25, NULL, 11, 'actividadId=11#nroActividad=1/2013#cronogramaDetalle.cronogramaDetalleId=1#descripcion=Asignacion de responsable#fechaCreacion=04/09/2013 21:52:13#fechaInicioPrevisto=04/09/2013 21:52:00#fechaInicioReprogramado=null#motivoReprogramacionInicio=null#fechaFinPrevista=09/09/2013 21:52:00#fechaFinReprogramada=null#motivoReprogramacion=null#fechaDevuelta=null#fechaResuelta=null#fechaCancelacion=null#pregunta=null#respuesta=null#estado=NUE#checklistCompleto=null#alerta.tipoAlarmaId=8#alarma.tipoAlarmaId=9#master.procesoId=1', 'actividadId=11#nroActividad=1/2013#cronogramaDetalle.cronogramaDetalleId=1#descripcion=Asignacion de responsable#fechaCreacion=04/09/2013 21:52:13#fechaInicioPrevisto=04/09/2013 21:52:00#fechaInicioReprogramado=null#motivoReprogramacionInicio=null#fechaFinPrevista=09/09/2013 21:52:00#fechaFinReprogramada=null#motivoReprogramacion=null#fechaDevuelta=null#fechaResuelta=null#fechaCancelacion=null#pregunta=null#respuesta=null#estado=NUE#checklistCompleto=null#alerta.tipoAlarmaId=8#alarma.tipoAlarmaId=9#master.procesoId=1', '2013-09-05 00:36:52.141', 1, '127.0.0.1');
INSERT INTO aud_log (id, aud_tabla_id, registro_id, valor_anterior, valor_nuevo, fecha, usuario_id, ip) VALUES (26, NULL, 15, 'actividadId=15#nroActividad=2/2013#cronogramaDetalle.cronogramaDetalleId=2#descripcion=Evaluacion de terreno#fechaCreacion=05/09/2013 00:37:08#fechaInicioPrevisto=05/09/2013 00:37:00#fechaInicioReprogramado=null#motivoReprogramacionInicio=null#fechaFinPrevista=15/09/2013 00:37:00#fechaFinReprogramada=null#motivoReprogramacion=null#fechaDevuelta=null#fechaResuelta=null#fechaCancelacion=null#pregunta=Tiene anticipo?#respuesta=SI#estado=PRO#checklistCompleto=null#actividadAnterior.actividadId=11#alerta.tipoAlarmaId=8#alarma.tipoAlarmaId=9#master.procesoId=1', 'actividadId=15#nroActividad=2/2013#cronogramaDetalle.cronogramaDetalleId=2#descripcion=Evaluacion de terreno#fechaCreacion=05/09/2013 00:37:08#fechaInicioPrevisto=05/09/2013 00:37:00#fechaInicioReprogramado=null#motivoReprogramacionInicio=null#fechaFinPrevista=15/09/2013 00:37:00#fechaFinReprogramada=null#motivoReprogramacion=null#fechaDevuelta=null#fechaResuelta=null#fechaCancelacion=null#pregunta=Tiene anticipo?#respuesta=SI#estado=PRO#checklistCompleto=null#actividadAnterior.actividadId=11#alerta.tipoAlarmaId=8#alarma.tipoAlarmaId=9#master.procesoId=1', '2013-09-05 00:42:51.056', 1, '127.0.0.1');
INSERT INTO aud_log (id, aud_tabla_id, registro_id, valor_anterior, valor_nuevo, fecha, usuario_id, ip) VALUES (27, NULL, 11, 'actividadId=11#nroActividad=1/2013#cronogramaDetalle.cronogramaDetalleId=1#descripcion=Asignacion de responsable#fechaCreacion=04/09/2013 21:52:13#fechaInicioPrevisto=04/09/2013 21:52:00#fechaInicioReprogramado=null#motivoReprogramacionInicio=null#fechaFinPrevista=09/09/2013 21:52:00#fechaFinReprogramada=null#motivoReprogramacion=null#fechaDevuelta=null#fechaResuelta=null#fechaCancelacion=null#pregunta=null#respuesta=null#estado=PRO#checklistCompleto=null#alerta.tipoAlarmaId=8#alarma.tipoAlarmaId=9#master.procesoId=1', 'actividadId=11#nroActividad=1/2013#cronogramaDetalle.cronogramaDetalleId=1#descripcion=Asignacion de responsable#fechaCreacion=04/09/2013 21:52:13#fechaInicioPrevisto=04/09/2013 21:52:00#fechaInicioReprogramado=null#motivoReprogramacionInicio=null#fechaFinPrevista=09/09/2013 21:52:00#fechaFinReprogramada=null#motivoReprogramacion=null#fechaDevuelta=null#fechaResuelta=null#fechaCancelacion=null#pregunta=null#respuesta=null#estado=PRO#checklistCompleto=null#alerta.tipoAlarmaId=8#alarma.tipoAlarmaId=9#master.procesoId=1', '2013-09-05 00:51:06.646', 1, '127.0.0.1');
INSERT INTO aud_log (id, aud_tabla_id, registro_id, valor_anterior, valor_nuevo, fecha, usuario_id, ip) VALUES (65, NULL, 2, 'Registro Nuevo', 'documentoId=2#filename=ggil#filepath=D:\tmp\#filetype=null#fileExtension=txt#bloqueado=Si#usuarioBloqueo.usuarioId=1#fechaBloqueo=08/09/2013 22:03:21#fechaDesbloqueo=null#entidad=Actividad#idEntidad=27', '2013-09-08 22:03:21.521', 1, '127.0.0.1');
INSERT INTO aud_log (id, aud_tabla_id, registro_id, valor_anterior, valor_nuevo, fecha, usuario_id, ip) VALUES (66, NULL, 3, 'Registro Nuevo', 'observacionId=3#descripcion=observacion 1 actividad 1#usuario.usuarioId=1#fechaHora=08/09/2013 22:03:41#entidad=Actividad#idEntidad=27', '2013-09-08 22:03:41.812', 1, '127.0.0.1');
INSERT INTO aud_log (id, aud_tabla_id, registro_id, valor_anterior, valor_nuevo, fecha, usuario_id, ip) VALUES (67, NULL, 4, 'Registro Nuevo', 'observacionId=4#descripcion=observación 2 actividad 1#usuario.usuarioId=1#fechaHora=08/09/2013 22:04:00#entidad=Actividad#idEntidad=27', '2013-09-08 22:04:00.947', 1, '127.0.0.1');
INSERT INTO aud_log (id, aud_tabla_id, registro_id, valor_anterior, valor_nuevo, fecha, usuario_id, ip) VALUES (28, NULL, 23, 'actividadId=23#nroActividad=4/2013#cronogramaDetalle.cronogramaDetalleId=4#descripcion=Elaboracion de informe#fechaCreacion=05/09/2013 01:11:33#fechaInicioPrevisto=05/09/2013 01:11:00#fechaInicioReprogramado=null#motivoReprogramacionInicio=null#fechaFinPrevista=12/09/2013 01:11:00#fechaFinReprogramada=null#motivoReprogramacion=null#fechaDevuelta=null#fechaResuelta=null#fechaCancelacion=null#pregunta=null#respuesta=null#estado=null#checklistCompleto=null#actividadAnterior.actividadId=22#alerta.tipoAlarmaId=8#alarma.tipoAlarmaId=9#master.procesoId=1', 'actividadId=23#nroActividad=4/2013#cronogramaDetalle.cronogramaDetalleId=4#descripcion=Elaboracion de informe#fechaCreacion=05/09/2013 01:11:33#fechaInicioPrevisto=05/09/2013 01:11:00#fechaInicioReprogramado=null#motivoReprogramacionInicio=null#fechaFinPrevista=12/09/2013 01:11:00#fechaFinReprogramada=null#motivoReprogramacion=null#fechaDevuelta=null#fechaResuelta=null#fechaCancelacion=null#pregunta=null#respuesta=null#estado=null#checklistCompleto=null#actividadAnterior.actividadId=22#alerta.tipoAlarmaId=8#alarma.tipoAlarmaId=9#master.procesoId=1', '2013-09-05 01:11:53.088', 1, '127.0.0.1');
INSERT INTO aud_log (id, aud_tabla_id, registro_id, valor_anterior, valor_nuevo, fecha, usuario_id, ip) VALUES (29, NULL, 23, 'actividadId=23#nroActividad=4/2013#cronogramaDetalle.cronogramaDetalleId=4#descripcion=Elaboracion de informe#fechaCreacion=05/09/2013 01:11:33#fechaInicioPrevisto=05/09/2013 01:11:00#fechaInicioReprogramado=null#motivoReprogramacionInicio=null#fechaFinPrevista=12/09/2013 01:11:00#fechaFinReprogramada=null#motivoReprogramacion=null#fechaDevuelta=null#fechaResuelta=null#fechaCancelacion=null#pregunta=null#respuesta=null#estado=null#checklistCompleto=null#actividadAnterior.actividadId=22#alerta.tipoAlarmaId=8#alarma.tipoAlarmaId=9#master.procesoId=1', 'actividadId=23#nroActividad=4/2013#cronogramaDetalle.cronogramaDetalleId=4#descripcion=Elaboracion de informe#fechaCreacion=05/09/2013 01:11:33#fechaInicioPrevisto=05/09/2013 01:11:00#fechaInicioReprogramado=null#motivoReprogramacionInicio=null#fechaFinPrevista=12/09/2013 01:11:00#fechaFinReprogramada=null#motivoReprogramacion=null#fechaDevuelta=null#fechaResuelta=null#fechaCancelacion=null#pregunta=null#respuesta=null#estado=null#checklistCompleto=null#actividadAnterior.actividadId=22#alerta.tipoAlarmaId=8#alarma.tipoAlarmaId=9#master.procesoId=1', '2013-09-05 01:11:56.576', 1, '127.0.0.1');
INSERT INTO aud_log (id, aud_tabla_id, registro_id, valor_anterior, valor_nuevo, fecha, usuario_id, ip) VALUES (30, NULL, 23, 'actividadId=23#nroActividad=4/2013#cronogramaDetalle.cronogramaDetalleId=4#descripcion=Elaboracion de informe#fechaCreacion=05/09/2013 01:11:33#fechaInicioPrevisto=05/09/2013 01:11:00#fechaInicioReprogramado=null#motivoReprogramacionInicio=null#fechaFinPrevista=12/09/2013 01:11:00#fechaFinReprogramada=null#motivoReprogramacion=null#fechaDevuelta=null#fechaResuelta=null#fechaCancelacion=null#pregunta=null#respuesta=null#estado=PRO#checklistCompleto=null#actividadAnterior.actividadId=22#alerta.tipoAlarmaId=8#alarma.tipoAlarmaId=9#master.procesoId=1', 'actividadId=23#nroActividad=4/2013#cronogramaDetalle.cronogramaDetalleId=4#descripcion=Elaboracion de informe#fechaCreacion=05/09/2013 01:11:33#fechaInicioPrevisto=05/09/2013 01:11:00#fechaInicioReprogramado=null#motivoReprogramacionInicio=null#fechaFinPrevista=12/09/2013 01:11:00#fechaFinReprogramada=null#motivoReprogramacion=null#fechaDevuelta=null#fechaResuelta=null#fechaCancelacion=null#pregunta=null#respuesta=null#estado=PRO#checklistCompleto=null#actividadAnterior.actividadId=22#alerta.tipoAlarmaId=8#alarma.tipoAlarmaId=9#master.procesoId=1', '2013-09-05 01:12:40.796', 1, '127.0.0.1');
INSERT INTO aud_log (id, aud_tabla_id, registro_id, valor_anterior, valor_nuevo, fecha, usuario_id, ip) VALUES (31, NULL, 23, 'actividadId=23#nroActividad=4/2013#cronogramaDetalle.cronogramaDetalleId=4#descripcion=Elaboracion de informe#fechaCreacion=05/09/2013 01:11:33#fechaInicioPrevisto=05/09/2013 01:11:00#fechaInicioReprogramado=null#motivoReprogramacionInicio=null#fechaFinPrevista=12/09/2013 01:11:00#fechaFinReprogramada=null#motivoReprogramacion=null#fechaDevuelta=null#fechaResuelta=null#fechaCancelacion=null#pregunta=null#respuesta=null#estado=null#checklistCompleto=null#actividadAnterior.actividadId=22#alerta.tipoAlarmaId=8#alarma.tipoAlarmaId=9#master.procesoId=1', 'actividadId=23#nroActividad=4/2013#cronogramaDetalle.cronogramaDetalleId=4#descripcion=Elaboracion de informe#fechaCreacion=05/09/2013 01:11:33#fechaInicioPrevisto=05/09/2013 01:11:00#fechaInicioReprogramado=null#motivoReprogramacionInicio=null#fechaFinPrevista=12/09/2013 01:11:00#fechaFinReprogramada=null#motivoReprogramacion=null#fechaDevuelta=null#fechaResuelta=null#fechaCancelacion=null#pregunta=null#respuesta=null#estado=null#checklistCompleto=null#actividadAnterior.actividadId=22#alerta.tipoAlarmaId=8#alarma.tipoAlarmaId=9#master.procesoId=1', '2013-09-05 01:12:51.87', 1, '127.0.0.1');
INSERT INTO aud_log (id, aud_tabla_id, registro_id, valor_anterior, valor_nuevo, fecha, usuario_id, ip) VALUES (32, NULL, 11, 'actividadId=11#nroActividad=1/2013#cronogramaDetalle.cronogramaDetalleId=1#descripcion=Asignacion de responsable#fechaCreacion=04/09/2013 21:52:13#fechaInicioPrevisto=04/09/2013 21:52:00#fechaInicioReprogramado=null#motivoReprogramacionInicio=null#fechaFinPrevista=09/09/2013 21:52:00#fechaFinReprogramada=null#motivoReprogramacion=null#fechaDevuelta=null#fechaResuelta=null#fechaCancelacion=null#pregunta=null#respuesta=null#estado=NUE#checklistCompleto=null#alerta.tipoAlarmaId=8#alarma.tipoAlarmaId=9#master.procesoId=1', 'actividadId=11#nroActividad=1/2013#cronogramaDetalle.cronogramaDetalleId=1#descripcion=Asignacion de responsable#fechaCreacion=04/09/2013 21:52:13#fechaInicioPrevisto=04/09/2013 21:52:00#fechaInicioReprogramado=null#motivoReprogramacionInicio=null#fechaFinPrevista=09/09/2013 21:52:00#fechaFinReprogramada=null#motivoReprogramacion=null#fechaDevuelta=null#fechaResuelta=null#fechaCancelacion=null#pregunta=null#respuesta=null#estado=NUE#checklistCompleto=null#alerta.tipoAlarmaId=8#alarma.tipoAlarmaId=9#master.procesoId=1', '2013-09-05 01:14:24.137', 1, '127.0.0.1');
INSERT INTO aud_log (id, aud_tabla_id, registro_id, valor_anterior, valor_nuevo, fecha, usuario_id, ip) VALUES (33, NULL, 24, 'actividadId=24#nroActividad=5/2013#cronogramaDetalle.cronogramaDetalleId=2#descripcion=Evaluacion de terreno#fechaCreacion=05/09/2013 01:14:26#fechaInicioPrevisto=05/09/2013 01:14:00#fechaInicioReprogramado=null#motivoReprogramacionInicio=null#fechaFinPrevista=15/09/2013 01:14:00#fechaFinReprogramada=null#motivoReprogramacion=null#fechaDevuelta=null#fechaResuelta=null#fechaCancelacion=null#pregunta=Tiene anticipo?#respuesta=null#estado=PRO#checklistCompleto=null#actividadAnterior.actividadId=11#alerta.tipoAlarmaId=8#alarma.tipoAlarmaId=9#master.procesoId=1', 'actividadId=24#nroActividad=5/2013#cronogramaDetalle.cronogramaDetalleId=2#descripcion=Evaluacion de terreno#fechaCreacion=05/09/2013 01:14:26#fechaInicioPrevisto=05/09/2013 01:14:00#fechaInicioReprogramado=null#motivoReprogramacionInicio=null#fechaFinPrevista=15/09/2013 01:14:00#fechaFinReprogramada=null#motivoReprogramacion=null#fechaDevuelta=null#fechaResuelta=null#fechaCancelacion=null#pregunta=Tiene anticipo?#respuesta=null#estado=PRO#checklistCompleto=null#actividadAnterior.actividadId=11#alerta.tipoAlarmaId=8#alarma.tipoAlarmaId=9#master.procesoId=1', '2013-09-05 01:19:10.839', 1, '127.0.0.1');
INSERT INTO aud_log (id, aud_tabla_id, registro_id, valor_anterior, valor_nuevo, fecha, usuario_id, ip) VALUES (68, NULL, 3, 'Registro Nuevo', 'documentoId=3#filename=ggil#filepath=/tmp/jboss#filetype=null#fileExtension=txt#bloqueado=Si#usuarioBloqueo.usuarioId=1#fechaBloqueo=08/09/2013 22:07:40#fechaDesbloqueo=null#entidad=Proceso#idEntidad=11', '2013-09-08 22:07:40.304', 1, '127.0.0.1');
INSERT INTO aud_log (id, aud_tabla_id, registro_id, valor_anterior, valor_nuevo, fecha, usuario_id, ip) VALUES (34, NULL, 24, 'actividadId=24#nroActividad=5/2013#cronogramaDetalle.cronogramaDetalleId=2#descripcion=Evaluacion de terreno#fechaCreacion=05/09/2013 01:14:26#fechaInicioPrevisto=05/09/2013 01:14:00#fechaInicioReprogramado=null#motivoReprogramacionInicio=null#fechaFinPrevista=15/09/2013 01:14:00#fechaFinReprogramada=null#motivoReprogramacion=null#fechaDevuelta=null#fechaResuelta=null#fechaCancelacion=null#pregunta=Tiene anticipo?#respuesta=null#estado=CAN#checklistCompleto=null#actividadAnterior.actividadId=11#alerta.tipoAlarmaId=8#alarma.tipoAlarmaId=9#master.procesoId=1', 'actividadId=24#nroActividad=5/2013#cronogramaDetalle.cronogramaDetalleId=2#descripcion=Evaluacion de terreno#fechaCreacion=05/09/2013 01:14:26#fechaInicioPrevisto=05/09/2013 01:14:00#fechaInicioReprogramado=null#motivoReprogramacionInicio=null#fechaFinPrevista=15/09/2013 01:14:00#fechaFinReprogramada=null#motivoReprogramacion=null#fechaDevuelta=null#fechaResuelta=null#fechaCancelacion=null#pregunta=Tiene anticipo?#respuesta=null#estado=CAN#checklistCompleto=null#actividadAnterior.actividadId=11#alerta.tipoAlarmaId=8#alarma.tipoAlarmaId=9#master.procesoId=1', '2013-09-05 01:19:15.712', 1, '127.0.0.1');
INSERT INTO aud_log (id, aud_tabla_id, registro_id, valor_anterior, valor_nuevo, fecha, usuario_id, ip) VALUES (35, NULL, 4, 'Registro Nuevo', 'usuarioId=4#apellido=gavilan#email=gavilan@email.com#estado=A#nombre=gavilan#nroSesion=null#ultimoLogin=null#usuario=gavilan', '2013-09-07 10:50:18.089', 1, '127.0.0.1');
INSERT INTO aud_log (id, aud_tabla_id, registro_id, valor_anterior, valor_nuevo, fecha, usuario_id, ip) VALUES (36, NULL, 5, 'Registro Nuevo', 'usuarioId=5#apellido=perez#email=jperez@email.com#estado=A#nombre=juan#nroSesion=null#ultimoLogin=null#usuario=jperez', '2013-09-07 10:50:39.343', 1, '127.0.0.1');
INSERT INTO aud_log (id, aud_tabla_id, registro_id, valor_anterior, valor_nuevo, fecha, usuario_id, ip) VALUES (37, NULL, 6, 'Registro Nuevo', 'usuarioId=6#apellido=gonzalez#email=agonzalez@email.com#estado=A#nombre=alberto#nroSesion=null#ultimoLogin=null#usuario=agonzalez', '2013-09-07 10:51:08.957', 1, '127.0.0.1');
INSERT INTO aud_log (id, aud_tabla_id, registro_id, valor_anterior, valor_nuevo, fecha, usuario_id, ip) VALUES (38, NULL, 7, 'Registro Nuevo', 'usuarioId=7#apellido=baez#email=cbaez@email.com#estado=A#nombre=carlos#nroSesion=null#ultimoLogin=null#usuario=cbaez', '2013-09-07 10:51:26.622', 1, '127.0.0.1');
INSERT INTO aud_log (id, aud_tabla_id, registro_id, valor_anterior, valor_nuevo, fecha, usuario_id, ip) VALUES (39, NULL, 8, 'Registro Nuevo', 'usuarioId=8#apellido=rojas#email=mrojas@email.com#estado=A#nombre=mauricio#nroSesion=null#ultimoLogin=null#usuario=mrojas', '2013-09-07 10:51:45.215', 1, '127.0.0.1');
INSERT INTO aud_log (id, aud_tabla_id, registro_id, valor_anterior, valor_nuevo, fecha, usuario_id, ip) VALUES (40, NULL, 4, 'usuarioId=4#apellido=gavilan#email=gavilan@email.com#estado=A#nombre=gavilan#nroSesion=null#ultimoLogin=null#usuario=gavilan', 'usuarioId=4#apellido=gavilan#email=gavilan@email.com#estado=A#nombre=gavilan#nroSesion=null#ultimoLogin=null#usuario=gavilan', '2013-09-07 10:52:14.363', 1, '127.0.0.1');
INSERT INTO aud_log (id, aud_tabla_id, registro_id, valor_anterior, valor_nuevo, fecha, usuario_id, ip) VALUES (41, NULL, 3, 'Registro Nuevo', 'usuarioRolPermisoId=3#rol.rolId=1#usuario.usuarioId=4', '2013-09-07 10:52:14.413', 1, '127.0.0.1');
INSERT INTO aud_log (id, aud_tabla_id, registro_id, valor_anterior, valor_nuevo, fecha, usuario_id, ip) VALUES (42, NULL, 4, 'Registro Nuevo', 'usuarioRolPermisoId=4#rol.rolId=3#usuario.usuarioId=4', '2013-09-07 10:52:14.467', 1, '127.0.0.1');
INSERT INTO aud_log (id, aud_tabla_id, registro_id, valor_anterior, valor_nuevo, fecha, usuario_id, ip) VALUES (43, NULL, 5, 'usuarioId=5#apellido=perez#email=jperez@email.com#estado=A#nombre=juan#nroSesion=null#ultimoLogin=null#usuario=jperez', 'usuarioId=5#apellido=perez#email=jperez@email.com#estado=A#nombre=juan#nroSesion=null#ultimoLogin=null#usuario=jperez', '2013-09-07 10:52:38.301', 1, '127.0.0.1');
INSERT INTO aud_log (id, aud_tabla_id, registro_id, valor_anterior, valor_nuevo, fecha, usuario_id, ip) VALUES (44, NULL, 5, 'Registro Nuevo', 'usuarioRolPermisoId=5#rol.rolId=2#usuario.usuarioId=5', '2013-09-07 10:52:38.332', 1, '127.0.0.1');
INSERT INTO aud_log (id, aud_tabla_id, registro_id, valor_anterior, valor_nuevo, fecha, usuario_id, ip) VALUES (45, NULL, 6, 'Registro Nuevo', 'usuarioRolPermisoId=6#rol.rolId=4#usuario.usuarioId=5', '2013-09-07 10:52:38.355', 1, '127.0.0.1');
INSERT INTO aud_log (id, aud_tabla_id, registro_id, valor_anterior, valor_nuevo, fecha, usuario_id, ip) VALUES (46, NULL, 6, 'usuarioId=6#apellido=gonzalez#email=agonzalez@email.com#estado=A#nombre=alberto#nroSesion=null#ultimoLogin=null#usuario=agonzalez', 'usuarioId=6#apellido=gonzalez#email=agonzalez@email.com#estado=A#nombre=alberto#nroSesion=null#ultimoLogin=null#usuario=agonzalez', '2013-09-07 10:53:42.344', 1, '127.0.0.1');
INSERT INTO aud_log (id, aud_tabla_id, registro_id, valor_anterior, valor_nuevo, fecha, usuario_id, ip) VALUES (47, NULL, 7, 'Registro Nuevo', 'usuarioRolPermisoId=7#rol.rolId=2#usuario.usuarioId=6', '2013-09-07 10:53:42.365', 1, '127.0.0.1');
INSERT INTO aud_log (id, aud_tabla_id, registro_id, valor_anterior, valor_nuevo, fecha, usuario_id, ip) VALUES (48, NULL, 8, 'Registro Nuevo', 'usuarioRolPermisoId=8#rol.rolId=5#usuario.usuarioId=6', '2013-09-07 10:53:42.382', 1, '127.0.0.1');
INSERT INTO aud_log (id, aud_tabla_id, registro_id, valor_anterior, valor_nuevo, fecha, usuario_id, ip) VALUES (49, NULL, 7, 'usuarioId=7#apellido=baez#email=cbaez@email.com#estado=A#nombre=carlos#nroSesion=null#ultimoLogin=null#usuario=cbaez', 'usuarioId=7#apellido=baez#email=cbaez@email.com#estado=A#nombre=carlos#nroSesion=null#ultimoLogin=null#usuario=cbaez', '2013-09-07 10:54:56.957', 1, '127.0.0.1');
INSERT INTO aud_log (id, aud_tabla_id, registro_id, valor_anterior, valor_nuevo, fecha, usuario_id, ip) VALUES (50, NULL, 9, 'Registro Nuevo', 'usuarioRolPermisoId=9#rol.rolId=2#usuario.usuarioId=7', '2013-09-07 10:54:56.982', 1, '127.0.0.1');
INSERT INTO aud_log (id, aud_tabla_id, registro_id, valor_anterior, valor_nuevo, fecha, usuario_id, ip) VALUES (51, NULL, 10, 'Registro Nuevo', 'usuarioRolPermisoId=10#rol.rolId=5#usuario.usuarioId=7', '2013-09-07 10:54:57.003', 1, '127.0.0.1');
INSERT INTO aud_log (id, aud_tabla_id, registro_id, valor_anterior, valor_nuevo, fecha, usuario_id, ip) VALUES (52, NULL, 8, 'usuarioId=8#apellido=rojas#email=mrojas@email.com#estado=A#nombre=mauricio#nroSesion=null#ultimoLogin=null#usuario=mrojas', 'usuarioId=8#apellido=rojas#email=mrojas@email.com#estado=A#nombre=mauricio#nroSesion=null#ultimoLogin=null#usuario=mrojas', '2013-09-07 10:55:09.852', 1, '127.0.0.1');
INSERT INTO aud_log (id, aud_tabla_id, registro_id, valor_anterior, valor_nuevo, fecha, usuario_id, ip) VALUES (53, NULL, 11, 'Registro Nuevo', 'usuarioRolPermisoId=11#rol.rolId=2#usuario.usuarioId=8', '2013-09-07 10:55:09.888', 1, '127.0.0.1');
INSERT INTO aud_log (id, aud_tabla_id, registro_id, valor_anterior, valor_nuevo, fecha, usuario_id, ip) VALUES (54, NULL, 12, 'Registro Nuevo', 'usuarioRolPermisoId=12#rol.rolId=6#usuario.usuarioId=8', '2013-09-07 10:55:09.908', 1, '127.0.0.1');
INSERT INTO aud_log (id, aud_tabla_id, registro_id, valor_anterior, valor_nuevo, fecha, usuario_id, ip) VALUES (55, NULL, 1, 'Registro Nuevo', 'cronogramaDetalleId=1#tarea=Asignación de responsable#nroOrden=1#rolResponsable.rolId=3#alertaInicio=Si#duracionTarea=2#master.cronogramaId=1', '2013-09-07 11:03:30.044', 1, '127.0.0.1');
INSERT INTO aud_log (id, aud_tabla_id, registro_id, valor_anterior, valor_nuevo, fecha, usuario_id, ip) VALUES (56, NULL, 2, 'Registro Nuevo', 'cronogramaDetalleId=2#tarea=Evaluación de obra#nroOrden=2#rolResponsable.rolId=5#alertaInicio=Si#duracionTarea=0#pregunta.preguntaId=2#master.cronogramaId=1', '2013-09-07 11:04:24.673', 1, '127.0.0.1');
INSERT INTO aud_log (id, aud_tabla_id, registro_id, valor_anterior, valor_nuevo, fecha, usuario_id, ip) VALUES (57, NULL, 3, 'Registro Nuevo', 'cronogramaDetalleId=3#tarea=Evaluación in situ de la obra#nroOrden=3#rolResponsable.rolId=5#alertaInicio=Si#duracionTarea=10#master.cronogramaId=1', '2013-09-07 11:05:33.37', 1, '127.0.0.1');
INSERT INTO aud_log (id, aud_tabla_id, registro_id, valor_anterior, valor_nuevo, fecha, usuario_id, ip) VALUES (58, NULL, 4, 'Registro Nuevo', 'cronogramaDetalleId=4#tarea=Elaboración de plano#nroOrden=4#rolResponsable.rolId=4#alertaInicio=Si#duracionTarea=10#master.cronogramaId=1', '2013-09-07 11:05:57.28', 1, '127.0.0.1');
INSERT INTO aud_log (id, aud_tabla_id, registro_id, valor_anterior, valor_nuevo, fecha, usuario_id, ip) VALUES (59, NULL, 5, 'Registro Nuevo', 'cronogramaDetalleId=5#tarea=Elaboración de informe#nroOrden=5#rolResponsable.rolId=3#alertaInicio=Si#duracionTarea=15#master.cronogramaId=1', '2013-09-07 11:07:31.493', 1, '127.0.0.1');
INSERT INTO aud_log (id, aud_tabla_id, registro_id, valor_anterior, valor_nuevo, fecha, usuario_id, ip) VALUES (60, NULL, 6, 'Registro Nuevo', 'cronogramaDetalleId=6#tarea=Facturación#nroOrden=6#rolResponsable.rolId=6#alertaInicio=Si#duracionTarea=2#master.cronogramaId=1', '2013-09-07 11:07:48.15', 1, '127.0.0.1');
INSERT INTO aud_log (id, aud_tabla_id, registro_id, valor_anterior, valor_nuevo, fecha, usuario_id, ip) VALUES (61, NULL, 11, 'Registro Nuevo', 'procesoId=11#nroProceso=1/2013#responsable.usuarioId=4#cliente.clienteId=1#cronograma.cronogramaId=1#estado=PRO#descripcion=ReparaciÃ³n de oficina central#fechaInicioContratual=06/09/2013 00:00:00#fechaInicioReal=09/09/2013 00:00:00#fechaFinPrevista=30/09/2013 00:00:00#fechaFinReprogramada=null#motivoReprogramacion=#clienteNotificado=null', '2013-09-08 21:48:33.869', 1, '127.0.0.1');
INSERT INTO aud_log (id, aud_tabla_id, registro_id, valor_anterior, valor_nuevo, fecha, usuario_id, ip) VALUES (62, NULL, 1, 'Registro Nuevo', 'observacionId=1#descripcion=Primera observación#usuario.usuarioId=1#fechaHora=08/09/2013 21:51:52#entidad=Proceso#idEntidad=11', '2013-09-08 21:51:52.717', 1, '127.0.0.1');
INSERT INTO aud_log (id, aud_tabla_id, registro_id, valor_anterior, valor_nuevo, fecha, usuario_id, ip) VALUES (63, NULL, 2, 'Registro Nuevo', 'observacionId=2#descripcion=Segunda Observación#usuario.usuarioId=1#fechaHora=08/09/2013 21:52:24#entidad=Proceso#idEntidad=11', '2013-09-08 21:52:24.852', 1, '127.0.0.1');
INSERT INTO aud_log (id, aud_tabla_id, registro_id, valor_anterior, valor_nuevo, fecha, usuario_id, ip) VALUES (64, NULL, 1, 'Registro Nuevo', 'documentoId=1#filename=ggil#filepath=D:\tmp\#filetype=null#fileExtension=txt#bloqueado=Si#usuarioBloqueo.usuarioId=1#fechaBloqueo=08/09/2013 21:56:42#fechaDesbloqueo=null#entidad=Proceso#idEntidad=11', '2013-09-08 21:56:42.803', 1, '127.0.0.1');


--
-- TOC entry 2201 (class 0 OID 0)
-- Dependencies: 166
-- Name: aud_log_id_seq; Type: SEQUENCE SET; Schema: public; Owner: gestion
--

SELECT pg_catalog.setval('aud_log_id_seq', 68, true);


--
-- TOC entry 2131 (class 0 OID 206836)
-- Dependencies: 167 2169
-- Data for Name: aud_tabla; Type: TABLE DATA; Schema: public; Owner: gestion
--



--
-- TOC entry 2202 (class 0 OID 0)
-- Dependencies: 168
-- Name: aud_tabla_id_seq; Type: SEQUENCE SET; Schema: public; Owner: gestion
--

SELECT pg_catalog.setval('aud_tabla_id_seq', 1, false);


--
-- TOC entry 2133 (class 0 OID 206841)
-- Dependencies: 169 2169
-- Data for Name: checklist; Type: TABLE DATA; Schema: public; Owner: gestion
--



--
-- TOC entry 2134 (class 0 OID 206844)
-- Dependencies: 170 2169
-- Data for Name: checklist_detalle; Type: TABLE DATA; Schema: public; Owner: gestion
--



--
-- TOC entry 2203 (class 0 OID 0)
-- Dependencies: 171
-- Name: checklist_detalle_id_seq; Type: SEQUENCE SET; Schema: public; Owner: gestion
--

SELECT pg_catalog.setval('checklist_detalle_id_seq', 1, false);


--
-- TOC entry 2204 (class 0 OID 0)
-- Dependencies: 172
-- Name: checklist_id_seq; Type: SEQUENCE SET; Schema: public; Owner: gestion
--

SELECT pg_catalog.setval('checklist_id_seq', 1, false);


--
-- TOC entry 2137 (class 0 OID 206851)
-- Dependencies: 173 2169
-- Data for Name: cliente; Type: TABLE DATA; Schema: public; Owner: gestion
--

INSERT INTO cliente (id, nombre, direccion, telefono, persona_contacto) VALUES (1, 'AIT S.A.', 'Gral. Santos 843', '201014', 'Margarita Rojas');


--
-- TOC entry 2205 (class 0 OID 0)
-- Dependencies: 174
-- Name: cliente_id_seq; Type: SEQUENCE SET; Schema: public; Owner: gestion
--

SELECT pg_catalog.setval('cliente_id_seq', 1, true);


--
-- TOC entry 2139 (class 0 OID 206859)
-- Dependencies: 175 2169
-- Data for Name: cronograma; Type: TABLE DATA; Schema: public; Owner: gestion
--

INSERT INTO cronograma (id, nombre) VALUES (1, 'Reparacion de obra');


--
-- TOC entry 2140 (class 0 OID 206862)
-- Dependencies: 176 2169
-- Data for Name: cronograma_detalle; Type: TABLE DATA; Schema: public; Owner: gestion
--

INSERT INTO cronograma_detalle (id, cronograma, descripcion, rol, alerta_inicio, duracion, alerta, alarma, pregunta, tarea_si, tarea_no, tarea_siguiente, checklist, nro_orden) VALUES (6, 1, 'Facturación', 6, 'Si', 2, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 6);
INSERT INTO cronograma_detalle (id, cronograma, descripcion, rol, alerta_inicio, duracion, alerta, alarma, pregunta, tarea_si, tarea_no, tarea_siguiente, checklist, nro_orden) VALUES (1, 1, 'Asignación de responsable', 3, 'Si', 2, NULL, NULL, NULL, NULL, NULL, 2, NULL, 1);
INSERT INTO cronograma_detalle (id, cronograma, descripcion, rol, alerta_inicio, duracion, alerta, alarma, pregunta, tarea_si, tarea_no, tarea_siguiente, checklist, nro_orden) VALUES (2, 1, 'Evaluación de obra', 5, 'Si', 7, NULL, NULL, 2, 3, 4, NULL, NULL, 2);
INSERT INTO cronograma_detalle (id, cronograma, descripcion, rol, alerta_inicio, duracion, alerta, alarma, pregunta, tarea_si, tarea_no, tarea_siguiente, checklist, nro_orden) VALUES (3, 1, 'Evaluación in situ de la obra', 5, 'Si', 10, NULL, NULL, NULL, NULL, NULL, 4, NULL, 3);
INSERT INTO cronograma_detalle (id, cronograma, descripcion, rol, alerta_inicio, duracion, alerta, alarma, pregunta, tarea_si, tarea_no, tarea_siguiente, checklist, nro_orden) VALUES (4, 1, 'Elaboración de plano', 4, 'Si', 10, NULL, NULL, NULL, NULL, NULL, 5, NULL, 4);
INSERT INTO cronograma_detalle (id, cronograma, descripcion, rol, alerta_inicio, duracion, alerta, alarma, pregunta, tarea_si, tarea_no, tarea_siguiente, checklist, nro_orden) VALUES (5, 1, 'Elaboración de informe', 3, 'Si', 15, NULL, NULL, NULL, NULL, NULL, 6, NULL, 5);


--
-- TOC entry 2206 (class 0 OID 0)
-- Dependencies: 177
-- Name: cronograma_detalle_id_seq; Type: SEQUENCE SET; Schema: public; Owner: gestion
--

SELECT pg_catalog.setval('cronograma_detalle_id_seq', 6, true);


--
-- TOC entry 2207 (class 0 OID 0)
-- Dependencies: 178
-- Name: cronograma_id_seq; Type: SEQUENCE SET; Schema: public; Owner: gestion
--

SELECT pg_catalog.setval('cronograma_id_seq', 1, true);


--
-- TOC entry 2143 (class 0 OID 206869)
-- Dependencies: 179 2169
-- Data for Name: documento; Type: TABLE DATA; Schema: public; Owner: gestion
--

INSERT INTO documento (id, filename, filepath, filetype, file_extension, bloqueado, usuario_bloqueo, fecha_bloqueo, usuario_desbloqueo, fecha_desbloqueo, entidad, id_entidad) VALUES (1, 'ggil', 'D:\tmp\', NULL, 'txt', 'Si', 1, '2013-09-08 21:56:42.756', NULL, NULL, 'Proceso', 11);
INSERT INTO documento (id, filename, filepath, filetype, file_extension, bloqueado, usuario_bloqueo, fecha_bloqueo, usuario_desbloqueo, fecha_desbloqueo, entidad, id_entidad) VALUES (2, 'ggil', 'D:\tmp\', NULL, 'txt', 'Si', 1, '2013-09-08 22:03:21.502', NULL, NULL, 'Actividad', 27);
INSERT INTO documento (id, filename, filepath, filetype, file_extension, bloqueado, usuario_bloqueo, fecha_bloqueo, usuario_desbloqueo, fecha_desbloqueo, entidad, id_entidad) VALUES (3, 'ggil', '/tmp/jboss', NULL, 'txt', 'Si', 1, '2013-09-08 22:07:40.275', NULL, NULL, 'Proceso', 11);


--
-- TOC entry 2208 (class 0 OID 0)
-- Dependencies: 180
-- Name: documento_id_seq; Type: SEQUENCE SET; Schema: public; Owner: gestion
--

SELECT pg_catalog.setval('documento_id_seq', 3, true);


--
-- TOC entry 2145 (class 0 OID 206877)
-- Dependencies: 181 2169
-- Data for Name: log_sesion; Type: TABLE DATA; Schema: public; Owner: gestion
--

INSERT INTO log_sesion (id, usuario_id, fecha_intento, ip, exito, nro_sesion, observacion, usuario) VALUES (1, 1, '2013-08-23 19:03:06.616', '127.0.0.1', 'N', NULL, 'Password incorrecto', 'admin');
INSERT INTO log_sesion (id, usuario_id, fecha_intento, ip, exito, nro_sesion, observacion, usuario) VALUES (2, 1, '2013-08-23 19:03:14.31', '127.0.0.1', 'Y', 2, NULL, 'admin');
INSERT INTO log_sesion (id, usuario_id, fecha_intento, ip, exito, nro_sesion, observacion, usuario) VALUES (3, 1, '2013-08-23 19:29:44.3', '127.0.0.1', 'Y', 3, NULL, 'admin');
INSERT INTO log_sesion (id, usuario_id, fecha_intento, ip, exito, nro_sesion, observacion, usuario) VALUES (4, 1, '2013-08-23 19:29:49.481', '127.0.0.1', 'Y', 4, NULL, 'admin');
INSERT INTO log_sesion (id, usuario_id, fecha_intento, ip, exito, nro_sesion, observacion, usuario) VALUES (5, 1, '2013-09-03 19:14:41.658', '127.0.0.1', 'Y', 5, NULL, 'admin');
INSERT INTO log_sesion (id, usuario_id, fecha_intento, ip, exito, nro_sesion, observacion, usuario) VALUES (6, 1, '2013-09-05 00:27:57.992', '127.0.0.1', 'Y', 6, NULL, 'admin');
INSERT INTO log_sesion (id, usuario_id, fecha_intento, ip, exito, nro_sesion, observacion, usuario) VALUES (7, 1, '2013-09-05 00:33:58.087', '127.0.0.1', 'Y', 7, NULL, 'admin');
INSERT INTO log_sesion (id, usuario_id, fecha_intento, ip, exito, nro_sesion, observacion, usuario) VALUES (8, 1, '2013-09-05 00:36:26.039', '127.0.0.1', 'Y', 8, NULL, 'admin');
INSERT INTO log_sesion (id, usuario_id, fecha_intento, ip, exito, nro_sesion, observacion, usuario) VALUES (9, 1, '2013-09-05 00:42:30.075', '127.0.0.1', 'Y', 9, NULL, 'admin');
INSERT INTO log_sesion (id, usuario_id, fecha_intento, ip, exito, nro_sesion, observacion, usuario) VALUES (10, 1, '2013-09-05 00:53:14.817', '127.0.0.1', 'Y', 10, NULL, 'admin');
INSERT INTO log_sesion (id, usuario_id, fecha_intento, ip, exito, nro_sesion, observacion, usuario) VALUES (11, 1, '2013-09-05 00:58:27.454', '127.0.0.1', 'Y', 11, NULL, 'admin');
INSERT INTO log_sesion (id, usuario_id, fecha_intento, ip, exito, nro_sesion, observacion, usuario) VALUES (12, 1, '2013-09-05 01:00:50.159', '127.0.0.1', 'Y', 12, NULL, 'admin');
INSERT INTO log_sesion (id, usuario_id, fecha_intento, ip, exito, nro_sesion, observacion, usuario) VALUES (13, 1, '2013-09-05 01:08:57.858', '127.0.0.1', 'Y', 13, NULL, 'admin');
INSERT INTO log_sesion (id, usuario_id, fecha_intento, ip, exito, nro_sesion, observacion, usuario) VALUES (14, 1, '2013-09-05 01:18:54.089', '127.0.0.1', 'Y', 14, NULL, 'admin');
INSERT INTO log_sesion (id, usuario_id, fecha_intento, ip, exito, nro_sesion, observacion, usuario) VALUES (15, 1, '2013-09-07 10:49:32.419', '127.0.0.1', 'Y', 15, NULL, 'admin');
INSERT INTO log_sesion (id, usuario_id, fecha_intento, ip, exito, nro_sesion, observacion, usuario) VALUES (16, 4, '2013-09-07 10:55:19.6', '127.0.0.1', 'Y', 16, NULL, 'gavilan');
INSERT INTO log_sesion (id, usuario_id, fecha_intento, ip, exito, nro_sesion, observacion, usuario) VALUES (17, 5, '2013-09-07 10:55:28.022', '127.0.0.1', 'Y', 17, NULL, 'jperez');
INSERT INTO log_sesion (id, usuario_id, fecha_intento, ip, exito, nro_sesion, observacion, usuario) VALUES (18, NULL, '2013-09-07 10:55:34.893', '127.0.0.1', 'N', NULL, 'Usuario no Existe', 'amartinez');
INSERT INTO log_sesion (id, usuario_id, fecha_intento, ip, exito, nro_sesion, observacion, usuario) VALUES (19, 1, '2013-09-07 10:55:52.46', '127.0.0.1', 'Y', 19, NULL, 'admin');
INSERT INTO log_sesion (id, usuario_id, fecha_intento, ip, exito, nro_sesion, observacion, usuario) VALUES (20, 6, '2013-09-07 10:56:32.662', '127.0.0.1', 'Y', 20, NULL, 'agonzalez');
INSERT INTO log_sesion (id, usuario_id, fecha_intento, ip, exito, nro_sesion, observacion, usuario) VALUES (21, 7, '2013-09-07 10:56:40.055', '127.0.0.1', 'Y', 21, NULL, 'cbaez');
INSERT INTO log_sesion (id, usuario_id, fecha_intento, ip, exito, nro_sesion, observacion, usuario) VALUES (22, 8, '2013-09-07 10:56:46.037', '127.0.0.1', 'Y', 22, NULL, 'mrojas');
INSERT INTO log_sesion (id, usuario_id, fecha_intento, ip, exito, nro_sesion, observacion, usuario) VALUES (23, 1, '2013-09-07 10:57:31.198', '127.0.0.1', 'Y', 23, NULL, 'admin');
INSERT INTO log_sesion (id, usuario_id, fecha_intento, ip, exito, nro_sesion, observacion, usuario) VALUES (24, 1, '2013-09-08 21:47:27.405', '127.0.0.1', 'Y', 24, NULL, 'admin');
INSERT INTO log_sesion (id, usuario_id, fecha_intento, ip, exito, nro_sesion, observacion, usuario) VALUES (25, 1, '2013-09-08 21:56:29.352', '127.0.0.1', 'Y', 25, NULL, 'admin');
INSERT INTO log_sesion (id, usuario_id, fecha_intento, ip, exito, nro_sesion, observacion, usuario) VALUES (26, 1, '2013-09-08 21:58:49.383', '127.0.0.1', 'Y', 26, NULL, 'admin');
INSERT INTO log_sesion (id, usuario_id, fecha_intento, ip, exito, nro_sesion, observacion, usuario) VALUES (27, 1, '2013-09-08 22:03:10.756', '127.0.0.1', 'Y', 27, NULL, 'admin');
INSERT INTO log_sesion (id, usuario_id, fecha_intento, ip, exito, nro_sesion, observacion, usuario) VALUES (28, 1, '2013-09-08 22:06:53.027', '127.0.0.1', 'Y', 28, NULL, 'admin');


--
-- TOC entry 2209 (class 0 OID 0)
-- Dependencies: 182
-- Name: log_sesion_id_seq; Type: SEQUENCE SET; Schema: public; Owner: gestion
--

SELECT pg_catalog.setval('log_sesion_id_seq', 28, true);


--
-- TOC entry 2147 (class 0 OID 206885)
-- Dependencies: 183 2169
-- Data for Name: notificaciones; Type: TABLE DATA; Schema: public; Owner: gestion
--



--
-- TOC entry 2210 (class 0 OID 0)
-- Dependencies: 184
-- Name: notificaciones_id_seq; Type: SEQUENCE SET; Schema: public; Owner: gestion
--

SELECT pg_catalog.setval('notificaciones_id_seq', 1, false);


--
-- TOC entry 2149 (class 0 OID 206893)
-- Dependencies: 185 2169
-- Data for Name: observacion; Type: TABLE DATA; Schema: public; Owner: gestion
--

INSERT INTO observacion (id, descripcion, usuario, fecha_hora, entidad, id_entidad) VALUES (1, 'Primera observación', 1, '2013-09-08 21:51:52.71', 'Proceso', 11);
INSERT INTO observacion (id, descripcion, usuario, fecha_hora, entidad, id_entidad) VALUES (2, 'Segunda Observación', 1, '2013-09-08 21:52:24.845', 'Proceso', 11);
INSERT INTO observacion (id, descripcion, usuario, fecha_hora, entidad, id_entidad) VALUES (3, 'observacion 1 actividad 1', 1, '2013-09-08 22:03:41.803', 'Actividad', 27);
INSERT INTO observacion (id, descripcion, usuario, fecha_hora, entidad, id_entidad) VALUES (4, 'observación 2 actividad 1', 1, '2013-09-08 22:04:00.938', 'Actividad', 27);


--
-- TOC entry 2211 (class 0 OID 0)
-- Dependencies: 186
-- Name: observacion_id_seq; Type: SEQUENCE SET; Schema: public; Owner: gestion
--

SELECT pg_catalog.setval('observacion_id_seq', 4, true);


--
-- TOC entry 2151 (class 0 OID 206898)
-- Dependencies: 187 2169
-- Data for Name: permiso; Type: TABLE DATA; Schema: public; Owner: gestion
--

INSERT INTO permiso (id, descripcion) VALUES (1, 'administracion');
INSERT INTO permiso (id, descripcion) VALUES (2, 'usuarios ver listado');
INSERT INTO permiso (id, descripcion) VALUES (3, 'usuarios crear');
INSERT INTO permiso (id, descripcion) VALUES (4, 'usuarios modificar');
INSERT INTO permiso (id, descripcion) VALUES (5, 'usuarios modificar roles');
INSERT INTO permiso (id, descripcion) VALUES (6, 'roles modificar permisos');
INSERT INTO permiso (id, descripcion) VALUES (7, 'roles ver listado');
INSERT INTO permiso (id, descripcion) VALUES (8, 'roles crear');
INSERT INTO permiso (id, descripcion) VALUES (9, 'roles modificar');
INSERT INTO permiso (id, descripcion) VALUES (10, 'usuarios modificar permisos');


--
-- TOC entry 2212 (class 0 OID 0)
-- Dependencies: 188
-- Name: permiso_id_seq; Type: SEQUENCE SET; Schema: public; Owner: gestion
--

SELECT pg_catalog.setval('permiso_id_seq', 1, false);


--
-- TOC entry 2153 (class 0 OID 206903)
-- Dependencies: 189 2169
-- Data for Name: pregunta; Type: TABLE DATA; Schema: public; Owner: gestion
--

INSERT INTO pregunta (id, descripcion) VALUES (1, 'Tiene anticipo?');
INSERT INTO pregunta (id, descripcion) VALUES (2, 'Requiere evaluación in situ?');


--
-- TOC entry 2213 (class 0 OID 0)
-- Dependencies: 190
-- Name: pregunta_id_seq; Type: SEQUENCE SET; Schema: public; Owner: gestion
--

SELECT pg_catalog.setval('pregunta_id_seq', 2, true);


--
-- TOC entry 2155 (class 0 OID 206908)
-- Dependencies: 191 2169
-- Data for Name: proceso; Type: TABLE DATA; Schema: public; Owner: gestion
--

INSERT INTO proceso (id, nro_proceso, responsable, cliente, cronograma, estado, descripcion, fecha_inicio_contratual, fecha_inicio_real, fecha_fin_prevista, fecha_fin_reprogramada, motivo_reprogramacion, cliente_notificado) VALUES (11, '1/2013', 4, 1, 1, 'PRO', 'ReparaciÃ³n de oficina central', '2013-09-06 00:00:00', '2013-09-09 00:00:00', '2013-09-30 00:00:00', NULL, '', NULL);


--
-- TOC entry 2214 (class 0 OID 0)
-- Dependencies: 192
-- Name: proceso_id_seq; Type: SEQUENCE SET; Schema: public; Owner: gestion
--

SELECT pg_catalog.setval('proceso_id_seq', 11, true);


--
-- TOC entry 2157 (class 0 OID 206916)
-- Dependencies: 193 2169
-- Data for Name: rol; Type: TABLE DATA; Schema: public; Owner: gestion
--

INSERT INTO rol (id, descripcion) VALUES (1, 'admin');
INSERT INTO rol (id, descripcion) VALUES (2, 'user');
INSERT INTO rol (id, descripcion) VALUES (3, 'Ingeniero');
INSERT INTO rol (id, descripcion) VALUES (4, 'Autocadista');
INSERT INTO rol (id, descripcion) VALUES (5, 'Ayudante de campo');
INSERT INTO rol (id, descripcion) VALUES (6, 'Administrativo');


--
-- TOC entry 2215 (class 0 OID 0)
-- Dependencies: 194
-- Name: rol_id_seq; Type: SEQUENCE SET; Schema: public; Owner: gestion
--

SELECT pg_catalog.setval('rol_id_seq', 6, true);


--
-- TOC entry 2159 (class 0 OID 206921)
-- Dependencies: 195 2169
-- Data for Name: rol_permiso; Type: TABLE DATA; Schema: public; Owner: gestion
--

INSERT INTO rol_permiso (id, rol_id, permiso_id) VALUES (1, 1, 1);
INSERT INTO rol_permiso (id, rol_id, permiso_id) VALUES (2, 1, 2);
INSERT INTO rol_permiso (id, rol_id, permiso_id) VALUES (3, 1, 3);
INSERT INTO rol_permiso (id, rol_id, permiso_id) VALUES (4, 1, 4);
INSERT INTO rol_permiso (id, rol_id, permiso_id) VALUES (5, 1, 5);
INSERT INTO rol_permiso (id, rol_id, permiso_id) VALUES (6, 1, 6);
INSERT INTO rol_permiso (id, rol_id, permiso_id) VALUES (7, 1, 7);
INSERT INTO rol_permiso (id, rol_id, permiso_id) VALUES (8, 1, 8);
INSERT INTO rol_permiso (id, rol_id, permiso_id) VALUES (9, 1, 9);
INSERT INTO rol_permiso (id, rol_id, permiso_id) VALUES (10, 1, 10);


--
-- TOC entry 2216 (class 0 OID 0)
-- Dependencies: 196
-- Name: rol_permiso_id_seq; Type: SEQUENCE SET; Schema: public; Owner: gestion
--

SELECT pg_catalog.setval('rol_permiso_id_seq', 1, false);


--
-- TOC entry 2161 (class 0 OID 206926)
-- Dependencies: 197 2169
-- Data for Name: tipo_alarma; Type: TABLE DATA; Schema: public; Owner: gestion
--

INSERT INTO tipo_alarma (id, descripcion, dias, horas, responsable1, responsable2, tipo) VALUES (8, 'alerta 1', 1, 0, 3, 1, 'Alerta');
INSERT INTO tipo_alarma (id, descripcion, dias, horas, responsable1, responsable2, tipo) VALUES (9, 'alarma 1', 1, 0, 3, 1, 'Alarma');


--
-- TOC entry 2217 (class 0 OID 0)
-- Dependencies: 198
-- Name: tipo_alarma_id_seq; Type: SEQUENCE SET; Schema: public; Owner: gestion
--

SELECT pg_catalog.setval('tipo_alarma_id_seq', 9, true);


--
-- TOC entry 2163 (class 0 OID 206931)
-- Dependencies: 199 2169
-- Data for Name: usuario; Type: TABLE DATA; Schema: public; Owner: gestion
--

INSERT INTO usuario (id, usuario, nombre, apellido, ultimo_login, estado, email, nro_sesion) VALUES (3, 'usuario', 'usuario', 'usuario', NULL, 'A', 'usuario@email.com', NULL);
INSERT INTO usuario (id, usuario, nombre, apellido, ultimo_login, estado, email, nro_sesion) VALUES (4, 'gavilan', 'gavilan', 'gavilan', '2013-09-07 10:55:19.6', 'A', 'gavilan@email.com', 16);
INSERT INTO usuario (id, usuario, nombre, apellido, ultimo_login, estado, email, nro_sesion) VALUES (5, 'jperez', 'juan', 'perez', '2013-09-07 10:55:28.022', 'A', 'jperez@email.com', 17);
INSERT INTO usuario (id, usuario, nombre, apellido, ultimo_login, estado, email, nro_sesion) VALUES (6, 'agonzalez', 'alberto', 'gonzalez', '2013-09-07 10:56:32.662', 'A', 'agonzalez@email.com', 20);
INSERT INTO usuario (id, usuario, nombre, apellido, ultimo_login, estado, email, nro_sesion) VALUES (7, 'cbaez', 'carlos', 'baez', '2013-09-07 10:56:40.055', 'A', 'cbaez@email.com', 21);
INSERT INTO usuario (id, usuario, nombre, apellido, ultimo_login, estado, email, nro_sesion) VALUES (8, 'mrojas', 'mauricio', 'rojas', '2013-09-07 10:56:46.037', 'A', 'mrojas@email.com', 22);
INSERT INTO usuario (id, usuario, nombre, apellido, ultimo_login, estado, email, nro_sesion) VALUES (1, 'admin', 'Admin', 'Admin', '2013-09-08 22:06:53.027', 'A', 'admin@ait.com.py', 28);


--
-- TOC entry 2218 (class 0 OID 0)
-- Dependencies: 200
-- Name: usuario_id_seq; Type: SEQUENCE SET; Schema: public; Owner: gestion
--

SELECT pg_catalog.setval('usuario_id_seq', 8, true);


--
-- TOC entry 2165 (class 0 OID 206939)
-- Dependencies: 201 2169
-- Data for Name: usuario_password; Type: TABLE DATA; Schema: public; Owner: gestion
--

INSERT INTO usuario_password (id, usuario_id, password) VALUES (1, 1, '21232f297a57a5a743894a0e4a801fc3');
INSERT INTO usuario_password (id, usuario_id, password) VALUES (2, 3, 'f8032d5cae3de20fcec887f395ec9a6a');
INSERT INTO usuario_password (id, usuario_id, password) VALUES (3, 4, 'cce1a6c38656cd478646915741b254a8');
INSERT INTO usuario_password (id, usuario_id, password) VALUES (4, 5, 'ebde0645e6d2a650fb009be4dae6694e');
INSERT INTO usuario_password (id, usuario_id, password) VALUES (5, 6, '1bb99ff2ded3153589c8c37786716f71');
INSERT INTO usuario_password (id, usuario_id, password) VALUES (6, 7, 'c18927f80a36010812e211be81ab6efc');
INSERT INTO usuario_password (id, usuario_id, password) VALUES (7, 8, 'f81bcbb86165794ea4720d9e5400a14c');


--
-- TOC entry 2219 (class 0 OID 0)
-- Dependencies: 202
-- Name: usuario_password_id_seq; Type: SEQUENCE SET; Schema: public; Owner: gestion
--

SELECT pg_catalog.setval('usuario_password_id_seq', 7, true);


--
-- TOC entry 2167 (class 0 OID 206947)
-- Dependencies: 203 2169
-- Data for Name: usuario_rol_permiso; Type: TABLE DATA; Schema: public; Owner: gestion
--

INSERT INTO usuario_rol_permiso (id, usuario_id, permiso_id, rol_id) VALUES (1, 1, NULL, 1);
INSERT INTO usuario_rol_permiso (id, usuario_id, permiso_id, rol_id) VALUES (2, 3, NULL, 2);
INSERT INTO usuario_rol_permiso (id, usuario_id, permiso_id, rol_id) VALUES (3, 4, NULL, 1);
INSERT INTO usuario_rol_permiso (id, usuario_id, permiso_id, rol_id) VALUES (4, 4, NULL, 3);
INSERT INTO usuario_rol_permiso (id, usuario_id, permiso_id, rol_id) VALUES (5, 5, NULL, 2);
INSERT INTO usuario_rol_permiso (id, usuario_id, permiso_id, rol_id) VALUES (6, 5, NULL, 4);
INSERT INTO usuario_rol_permiso (id, usuario_id, permiso_id, rol_id) VALUES (7, 6, NULL, 2);
INSERT INTO usuario_rol_permiso (id, usuario_id, permiso_id, rol_id) VALUES (8, 6, NULL, 5);
INSERT INTO usuario_rol_permiso (id, usuario_id, permiso_id, rol_id) VALUES (9, 7, NULL, 2);
INSERT INTO usuario_rol_permiso (id, usuario_id, permiso_id, rol_id) VALUES (10, 7, NULL, 5);
INSERT INTO usuario_rol_permiso (id, usuario_id, permiso_id, rol_id) VALUES (11, 8, NULL, 2);
INSERT INTO usuario_rol_permiso (id, usuario_id, permiso_id, rol_id) VALUES (12, 8, NULL, 6);


--
-- TOC entry 2220 (class 0 OID 0)
-- Dependencies: 204
-- Name: usuario_rol_permiso_id_seq; Type: SEQUENCE SET; Schema: public; Owner: gestion
--

SELECT pg_catalog.setval('usuario_rol_permiso_id_seq', 12, true);


--
-- TOC entry 2048 (class 2606 OID 206975)
-- Dependencies: 162 162 2170
-- Name: actividad_checlikst_detalle_pkey; Type: CONSTRAINT; Schema: public; Owner: gestion; Tablespace: 
--

ALTER TABLE ONLY actividad_checlikst_detalle
    ADD CONSTRAINT actividad_checlikst_detalle_pkey PRIMARY KEY (id);


--
-- TOC entry 2046 (class 2606 OID 206977)
-- Dependencies: 161 161 2170
-- Name: actividad_pkey; Type: CONSTRAINT; Schema: public; Owner: gestion; Tablespace: 
--

ALTER TABLE ONLY actividad
    ADD CONSTRAINT actividad_pkey PRIMARY KEY (id);


--
-- TOC entry 2050 (class 2606 OID 206979)
-- Dependencies: 165 165 2170
-- Name: aud_log_pkey; Type: CONSTRAINT; Schema: public; Owner: gestion; Tablespace: 
--

ALTER TABLE ONLY aud_log
    ADD CONSTRAINT aud_log_pkey PRIMARY KEY (id);


--
-- TOC entry 2052 (class 2606 OID 206981)
-- Dependencies: 167 167 2170
-- Name: aud_tabla_pkey; Type: CONSTRAINT; Schema: public; Owner: gestion; Tablespace: 
--

ALTER TABLE ONLY aud_tabla
    ADD CONSTRAINT aud_tabla_pkey PRIMARY KEY (id);


--
-- TOC entry 2056 (class 2606 OID 206983)
-- Dependencies: 170 170 2170
-- Name: checklist_detalle_pkey; Type: CONSTRAINT; Schema: public; Owner: gestion; Tablespace: 
--

ALTER TABLE ONLY checklist_detalle
    ADD CONSTRAINT checklist_detalle_pkey PRIMARY KEY (id);


--
-- TOC entry 2054 (class 2606 OID 206985)
-- Dependencies: 169 169 2170
-- Name: checklist_pkey; Type: CONSTRAINT; Schema: public; Owner: gestion; Tablespace: 
--

ALTER TABLE ONLY checklist
    ADD CONSTRAINT checklist_pkey PRIMARY KEY (id);


--
-- TOC entry 2058 (class 2606 OID 206987)
-- Dependencies: 173 173 2170
-- Name: cliente_pkey; Type: CONSTRAINT; Schema: public; Owner: gestion; Tablespace: 
--

ALTER TABLE ONLY cliente
    ADD CONSTRAINT cliente_pkey PRIMARY KEY (id);


--
-- TOC entry 2062 (class 2606 OID 206989)
-- Dependencies: 176 176 2170
-- Name: cronograma_detalle_pkey; Type: CONSTRAINT; Schema: public; Owner: gestion; Tablespace: 
--

ALTER TABLE ONLY cronograma_detalle
    ADD CONSTRAINT cronograma_detalle_pkey PRIMARY KEY (id);


--
-- TOC entry 2060 (class 2606 OID 206991)
-- Dependencies: 175 175 2170
-- Name: cronograma_pkey; Type: CONSTRAINT; Schema: public; Owner: gestion; Tablespace: 
--

ALTER TABLE ONLY cronograma
    ADD CONSTRAINT cronograma_pkey PRIMARY KEY (id);


--
-- TOC entry 2064 (class 2606 OID 206993)
-- Dependencies: 179 179 2170
-- Name: documento_pkey; Type: CONSTRAINT; Schema: public; Owner: gestion; Tablespace: 
--

ALTER TABLE ONLY documento
    ADD CONSTRAINT documento_pkey PRIMARY KEY (id);


--
-- TOC entry 2066 (class 2606 OID 206995)
-- Dependencies: 181 181 2170
-- Name: log_sesion_pkey; Type: CONSTRAINT; Schema: public; Owner: gestion; Tablespace: 
--

ALTER TABLE ONLY log_sesion
    ADD CONSTRAINT log_sesion_pkey PRIMARY KEY (id);


--
-- TOC entry 2068 (class 2606 OID 206997)
-- Dependencies: 183 183 2170
-- Name: notificaciones_pkey; Type: CONSTRAINT; Schema: public; Owner: gestion; Tablespace: 
--

ALTER TABLE ONLY notificaciones
    ADD CONSTRAINT notificaciones_pkey PRIMARY KEY (id);


--
-- TOC entry 2070 (class 2606 OID 206999)
-- Dependencies: 185 185 2170
-- Name: observaciones_pkey; Type: CONSTRAINT; Schema: public; Owner: gestion; Tablespace: 
--

ALTER TABLE ONLY observacion
    ADD CONSTRAINT observaciones_pkey PRIMARY KEY (id);


--
-- TOC entry 2072 (class 2606 OID 207001)
-- Dependencies: 187 187 2170
-- Name: permiso_pkey; Type: CONSTRAINT; Schema: public; Owner: gestion; Tablespace: 
--

ALTER TABLE ONLY permiso
    ADD CONSTRAINT permiso_pkey PRIMARY KEY (id);


--
-- TOC entry 2074 (class 2606 OID 207003)
-- Dependencies: 189 189 2170
-- Name: pregunta_pkey; Type: CONSTRAINT; Schema: public; Owner: gestion; Tablespace: 
--

ALTER TABLE ONLY pregunta
    ADD CONSTRAINT pregunta_pkey PRIMARY KEY (id);


--
-- TOC entry 2076 (class 2606 OID 207005)
-- Dependencies: 191 191 2170
-- Name: proceso_pkey; Type: CONSTRAINT; Schema: public; Owner: gestion; Tablespace: 
--

ALTER TABLE ONLY proceso
    ADD CONSTRAINT proceso_pkey PRIMARY KEY (id);


--
-- TOC entry 2080 (class 2606 OID 207007)
-- Dependencies: 195 195 2170
-- Name: rol_permiso_pkey; Type: CONSTRAINT; Schema: public; Owner: gestion; Tablespace: 
--

ALTER TABLE ONLY rol_permiso
    ADD CONSTRAINT rol_permiso_pkey PRIMARY KEY (id);


--
-- TOC entry 2078 (class 2606 OID 207009)
-- Dependencies: 193 193 2170
-- Name: rol_pkey; Type: CONSTRAINT; Schema: public; Owner: gestion; Tablespace: 
--

ALTER TABLE ONLY rol
    ADD CONSTRAINT rol_pkey PRIMARY KEY (id);


--
-- TOC entry 2082 (class 2606 OID 207011)
-- Dependencies: 197 197 2170
-- Name: tipo_alarma_pkey; Type: CONSTRAINT; Schema: public; Owner: gestion; Tablespace: 
--

ALTER TABLE ONLY tipo_alarma
    ADD CONSTRAINT tipo_alarma_pkey PRIMARY KEY (id);


--
-- TOC entry 2086 (class 2606 OID 207013)
-- Dependencies: 201 201 2170
-- Name: usuario_password_pkey; Type: CONSTRAINT; Schema: public; Owner: gestion; Tablespace: 
--

ALTER TABLE ONLY usuario_password
    ADD CONSTRAINT usuario_password_pkey PRIMARY KEY (id);


--
-- TOC entry 2084 (class 2606 OID 207015)
-- Dependencies: 199 199 2170
-- Name: usuario_pkey; Type: CONSTRAINT; Schema: public; Owner: gestion; Tablespace: 
--

ALTER TABLE ONLY usuario
    ADD CONSTRAINT usuario_pkey PRIMARY KEY (id);


--
-- TOC entry 2088 (class 2606 OID 207017)
-- Dependencies: 203 203 2170
-- Name: usuario_rol_permiso_pkey; Type: CONSTRAINT; Schema: public; Owner: gestion; Tablespace: 
--

ALTER TABLE ONLY usuario_rol_permiso
    ADD CONSTRAINT usuario_rol_permiso_pkey PRIMARY KEY (id);


--
-- TOC entry 2089 (class 2606 OID 207018)
-- Dependencies: 161 161 2045 2170
-- Name: actividad_actividad_anterior_fkey; Type: FK CONSTRAINT; Schema: public; Owner: gestion
--

ALTER TABLE ONLY actividad
    ADD CONSTRAINT actividad_actividad_anterior_fkey FOREIGN KEY (actividad_anterior) REFERENCES actividad(id) ON UPDATE RESTRICT ON DELETE RESTRICT;


--
-- TOC entry 2090 (class 2606 OID 207023)
-- Dependencies: 161 197 2081 2170
-- Name: actividad_alarma_fkey; Type: FK CONSTRAINT; Schema: public; Owner: gestion
--

ALTER TABLE ONLY actividad
    ADD CONSTRAINT actividad_alarma_fkey FOREIGN KEY (alarma) REFERENCES tipo_alarma(id) ON UPDATE RESTRICT ON DELETE RESTRICT;


--
-- TOC entry 2091 (class 2606 OID 207028)
-- Dependencies: 2081 161 197 2170
-- Name: actividad_alerta_fkey; Type: FK CONSTRAINT; Schema: public; Owner: gestion
--

ALTER TABLE ONLY actividad
    ADD CONSTRAINT actividad_alerta_fkey FOREIGN KEY (alerta) REFERENCES tipo_alarma(id) ON UPDATE RESTRICT ON DELETE RESTRICT;


--
-- TOC entry 2092 (class 2606 OID 207033)
-- Dependencies: 2061 176 161 2170
-- Name: actividad_cronograma_detalle_fkey; Type: FK CONSTRAINT; Schema: public; Owner: gestion
--

ALTER TABLE ONLY actividad
    ADD CONSTRAINT actividad_cronograma_detalle_fkey FOREIGN KEY (cronograma_detalle) REFERENCES cronograma_detalle(id) ON UPDATE RESTRICT ON DELETE RESTRICT;


--
-- TOC entry 2093 (class 2606 OID 207038)
-- Dependencies: 191 2075 161 2170
-- Name: actividad_proceso_fkey; Type: FK CONSTRAINT; Schema: public; Owner: gestion
--

ALTER TABLE ONLY actividad
    ADD CONSTRAINT actividad_proceso_fkey FOREIGN KEY (proceso) REFERENCES proceso(id) ON UPDATE RESTRICT ON DELETE RESTRICT;


--
-- TOC entry 2094 (class 2606 OID 207043)
-- Dependencies: 161 199 2083 2170
-- Name: actividad_responsable_fkey; Type: FK CONSTRAINT; Schema: public; Owner: gestion
--

ALTER TABLE ONLY actividad
    ADD CONSTRAINT actividad_responsable_fkey FOREIGN KEY (responsable) REFERENCES usuario(id) ON UPDATE RESTRICT ON DELETE RESTRICT;


--
-- TOC entry 2095 (class 2606 OID 207048)
-- Dependencies: 161 161 2045 2170
-- Name: actividad_super_tarea_fkey; Type: FK CONSTRAINT; Schema: public; Owner: gestion
--

ALTER TABLE ONLY actividad
    ADD CONSTRAINT actividad_super_tarea_fkey FOREIGN KEY (super_tarea) REFERENCES actividad(id) ON UPDATE RESTRICT ON DELETE RESTRICT;


--
-- TOC entry 2096 (class 2606 OID 207053)
-- Dependencies: 2051 167 165 2170
-- Name: aud_log_aud_tabla_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: gestion
--

ALTER TABLE ONLY aud_log
    ADD CONSTRAINT aud_log_aud_tabla_id_fkey FOREIGN KEY (aud_tabla_id) REFERENCES aud_tabla(id) ON UPDATE RESTRICT ON DELETE RESTRICT;


--
-- TOC entry 2097 (class 2606 OID 207058)
-- Dependencies: 165 2083 199 2170
-- Name: aud_log_usuario_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: gestion
--

ALTER TABLE ONLY aud_log
    ADD CONSTRAINT aud_log_usuario_id_fkey FOREIGN KEY (usuario_id) REFERENCES usuario(id) ON UPDATE RESTRICT ON DELETE RESTRICT;


--
-- TOC entry 2098 (class 2606 OID 207063)
-- Dependencies: 170 2053 169 2170
-- Name: checklist_detalle_checklist_fkey; Type: FK CONSTRAINT; Schema: public; Owner: gestion
--

ALTER TABLE ONLY checklist_detalle
    ADD CONSTRAINT checklist_detalle_checklist_fkey FOREIGN KEY (checklist) REFERENCES checklist(id) ON UPDATE RESTRICT ON DELETE RESTRICT;


--
-- TOC entry 2099 (class 2606 OID 207068)
-- Dependencies: 176 2081 197 2170
-- Name: cronograma_detalle_alarma_fkey; Type: FK CONSTRAINT; Schema: public; Owner: gestion
--

ALTER TABLE ONLY cronograma_detalle
    ADD CONSTRAINT cronograma_detalle_alarma_fkey FOREIGN KEY (alarma) REFERENCES tipo_alarma(id) ON UPDATE RESTRICT ON DELETE RESTRICT;


--
-- TOC entry 2100 (class 2606 OID 207073)
-- Dependencies: 176 2081 197 2170
-- Name: cronograma_detalle_alerta_fkey; Type: FK CONSTRAINT; Schema: public; Owner: gestion
--

ALTER TABLE ONLY cronograma_detalle
    ADD CONSTRAINT cronograma_detalle_alerta_fkey FOREIGN KEY (alerta) REFERENCES tipo_alarma(id) ON UPDATE RESTRICT ON DELETE RESTRICT;


--
-- TOC entry 2101 (class 2606 OID 207078)
-- Dependencies: 176 2053 169 2170
-- Name: cronograma_detalle_checklist_fkey; Type: FK CONSTRAINT; Schema: public; Owner: gestion
--

ALTER TABLE ONLY cronograma_detalle
    ADD CONSTRAINT cronograma_detalle_checklist_fkey FOREIGN KEY (checklist) REFERENCES checklist(id) ON UPDATE RESTRICT ON DELETE RESTRICT;


--
-- TOC entry 2102 (class 2606 OID 207083)
-- Dependencies: 176 2059 175 2170
-- Name: cronograma_detalle_cronograma_fkey; Type: FK CONSTRAINT; Schema: public; Owner: gestion
--

ALTER TABLE ONLY cronograma_detalle
    ADD CONSTRAINT cronograma_detalle_cronograma_fkey FOREIGN KEY (cronograma) REFERENCES cronograma(id) ON UPDATE RESTRICT ON DELETE RESTRICT;


--
-- TOC entry 2103 (class 2606 OID 207088)
-- Dependencies: 2073 189 176 2170
-- Name: cronograma_detalle_pregunta_fkey; Type: FK CONSTRAINT; Schema: public; Owner: gestion
--

ALTER TABLE ONLY cronograma_detalle
    ADD CONSTRAINT cronograma_detalle_pregunta_fkey FOREIGN KEY (pregunta) REFERENCES pregunta(id) ON UPDATE RESTRICT ON DELETE RESTRICT;


--
-- TOC entry 2104 (class 2606 OID 207093)
-- Dependencies: 176 193 2077 2170
-- Name: cronograma_detalle_rol_fkey; Type: FK CONSTRAINT; Schema: public; Owner: gestion
--

ALTER TABLE ONLY cronograma_detalle
    ADD CONSTRAINT cronograma_detalle_rol_fkey FOREIGN KEY (rol) REFERENCES rol(id) ON UPDATE RESTRICT ON DELETE RESTRICT;


--
-- TOC entry 2105 (class 2606 OID 207098)
-- Dependencies: 176 2061 176 2170
-- Name: cronograma_detalle_tarea_no_fkey; Type: FK CONSTRAINT; Schema: public; Owner: gestion
--

ALTER TABLE ONLY cronograma_detalle
    ADD CONSTRAINT cronograma_detalle_tarea_no_fkey FOREIGN KEY (tarea_no) REFERENCES cronograma_detalle(id) ON UPDATE RESTRICT ON DELETE RESTRICT;


--
-- TOC entry 2106 (class 2606 OID 207103)
-- Dependencies: 176 176 2061 2170
-- Name: cronograma_detalle_tarea_si_fkey; Type: FK CONSTRAINT; Schema: public; Owner: gestion
--

ALTER TABLE ONLY cronograma_detalle
    ADD CONSTRAINT cronograma_detalle_tarea_si_fkey FOREIGN KEY (tarea_si) REFERENCES cronograma_detalle(id) ON UPDATE RESTRICT ON DELETE RESTRICT;


--
-- TOC entry 2107 (class 2606 OID 207108)
-- Dependencies: 176 2061 176 2170
-- Name: cronograma_detalle_tarea_siguiente_fkey; Type: FK CONSTRAINT; Schema: public; Owner: gestion
--

ALTER TABLE ONLY cronograma_detalle
    ADD CONSTRAINT cronograma_detalle_tarea_siguiente_fkey FOREIGN KEY (tarea_siguiente) REFERENCES cronograma_detalle(id) ON UPDATE RESTRICT ON DELETE RESTRICT;


--
-- TOC entry 2108 (class 2606 OID 207113)
-- Dependencies: 179 2083 199 2170
-- Name: documento_usuario_bloqueo_fkey; Type: FK CONSTRAINT; Schema: public; Owner: gestion
--

ALTER TABLE ONLY documento
    ADD CONSTRAINT documento_usuario_bloqueo_fkey FOREIGN KEY (usuario_bloqueo) REFERENCES usuario(id) ON UPDATE RESTRICT ON DELETE RESTRICT;


--
-- TOC entry 2109 (class 2606 OID 207118)
-- Dependencies: 2083 179 199 2170
-- Name: documento_usuario_desbloqueo_fkey; Type: FK CONSTRAINT; Schema: public; Owner: gestion
--

ALTER TABLE ONLY documento
    ADD CONSTRAINT documento_usuario_desbloqueo_fkey FOREIGN KEY (usuario_desbloqueo) REFERENCES usuario(id) ON UPDATE RESTRICT ON DELETE RESTRICT;


--
-- TOC entry 2110 (class 2606 OID 207123)
-- Dependencies: 181 199 2083 2170
-- Name: log_sesion_usuario_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: gestion
--

ALTER TABLE ONLY log_sesion
    ADD CONSTRAINT log_sesion_usuario_id_fkey FOREIGN KEY (usuario_id) REFERENCES usuario(id) ON UPDATE RESTRICT ON DELETE RESTRICT;


--
-- TOC entry 2111 (class 2606 OID 207128)
-- Dependencies: 183 2083 199 2170
-- Name: notificaciones_responsable_fkey; Type: FK CONSTRAINT; Schema: public; Owner: gestion
--

ALTER TABLE ONLY notificaciones
    ADD CONSTRAINT notificaciones_responsable_fkey FOREIGN KEY (responsable) REFERENCES usuario(id) ON UPDATE RESTRICT ON DELETE RESTRICT;


--
-- TOC entry 2112 (class 2606 OID 207133)
-- Dependencies: 183 2083 199 2170
-- Name: notificaciones_usuario_fkey; Type: FK CONSTRAINT; Schema: public; Owner: gestion
--

ALTER TABLE ONLY notificaciones
    ADD CONSTRAINT notificaciones_usuario_fkey FOREIGN KEY (usuario) REFERENCES usuario(id) ON UPDATE RESTRICT ON DELETE RESTRICT;


--
-- TOC entry 2113 (class 2606 OID 207138)
-- Dependencies: 185 2083 199 2170
-- Name: observaciones_usuario_fkey; Type: FK CONSTRAINT; Schema: public; Owner: gestion
--

ALTER TABLE ONLY observacion
    ADD CONSTRAINT observaciones_usuario_fkey FOREIGN KEY (usuario) REFERENCES usuario(id) ON UPDATE RESTRICT ON DELETE RESTRICT;


--
-- TOC entry 2114 (class 2606 OID 207143)
-- Dependencies: 173 191 2057 2170
-- Name: proceso_cliente_fkey; Type: FK CONSTRAINT; Schema: public; Owner: gestion
--

ALTER TABLE ONLY proceso
    ADD CONSTRAINT proceso_cliente_fkey FOREIGN KEY (cliente) REFERENCES cliente(id) ON UPDATE RESTRICT ON DELETE RESTRICT;


--
-- TOC entry 2115 (class 2606 OID 207148)
-- Dependencies: 191 2059 175 2170
-- Name: proceso_cronograma_fkey; Type: FK CONSTRAINT; Schema: public; Owner: gestion
--

ALTER TABLE ONLY proceso
    ADD CONSTRAINT proceso_cronograma_fkey FOREIGN KEY (cronograma) REFERENCES cronograma(id) ON UPDATE RESTRICT ON DELETE RESTRICT;


--
-- TOC entry 2116 (class 2606 OID 207153)
-- Dependencies: 191 2083 199 2170
-- Name: proceso_responsable_fkey; Type: FK CONSTRAINT; Schema: public; Owner: gestion
--

ALTER TABLE ONLY proceso
    ADD CONSTRAINT proceso_responsable_fkey FOREIGN KEY (responsable) REFERENCES usuario(id) ON UPDATE RESTRICT ON DELETE RESTRICT;


--
-- TOC entry 2117 (class 2606 OID 207158)
-- Dependencies: 2071 195 187 2170
-- Name: rol_permiso_permiso_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: gestion
--

ALTER TABLE ONLY rol_permiso
    ADD CONSTRAINT rol_permiso_permiso_id_fkey FOREIGN KEY (permiso_id) REFERENCES permiso(id) ON UPDATE RESTRICT ON DELETE RESTRICT;


--
-- TOC entry 2118 (class 2606 OID 207163)
-- Dependencies: 2077 195 193 2170
-- Name: rol_permiso_rol_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: gestion
--

ALTER TABLE ONLY rol_permiso
    ADD CONSTRAINT rol_permiso_rol_id_fkey FOREIGN KEY (rol_id) REFERENCES rol(id) ON UPDATE RESTRICT ON DELETE RESTRICT;


--
-- TOC entry 2119 (class 2606 OID 207168)
-- Dependencies: 197 2083 199 2170
-- Name: tipo_alarma_responsable1_fkey; Type: FK CONSTRAINT; Schema: public; Owner: gestion
--

ALTER TABLE ONLY tipo_alarma
    ADD CONSTRAINT tipo_alarma_responsable1_fkey FOREIGN KEY (responsable1) REFERENCES usuario(id) ON UPDATE RESTRICT ON DELETE RESTRICT;


--
-- TOC entry 2120 (class 2606 OID 207173)
-- Dependencies: 197 199 2083 2170
-- Name: tipo_alarma_responsable2_fkey; Type: FK CONSTRAINT; Schema: public; Owner: gestion
--

ALTER TABLE ONLY tipo_alarma
    ADD CONSTRAINT tipo_alarma_responsable2_fkey FOREIGN KEY (responsable2) REFERENCES usuario(id) ON UPDATE RESTRICT ON DELETE RESTRICT;


--
-- TOC entry 2121 (class 2606 OID 207178)
-- Dependencies: 2083 199 201 2170
-- Name: usuario_password_usuario_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: gestion
--

ALTER TABLE ONLY usuario_password
    ADD CONSTRAINT usuario_password_usuario_id_fkey FOREIGN KEY (usuario_id) REFERENCES usuario(id) ON UPDATE RESTRICT ON DELETE RESTRICT;


--
-- TOC entry 2122 (class 2606 OID 207183)
-- Dependencies: 203 187 2071 2170
-- Name: usuario_rol_permiso_permiso_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: gestion
--

ALTER TABLE ONLY usuario_rol_permiso
    ADD CONSTRAINT usuario_rol_permiso_permiso_id_fkey FOREIGN KEY (permiso_id) REFERENCES permiso(id) ON UPDATE RESTRICT ON DELETE RESTRICT;


--
-- TOC entry 2123 (class 2606 OID 207188)
-- Dependencies: 2077 203 193 2170
-- Name: usuario_rol_permiso_rol_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: gestion
--

ALTER TABLE ONLY usuario_rol_permiso
    ADD CONSTRAINT usuario_rol_permiso_rol_id_fkey FOREIGN KEY (rol_id) REFERENCES rol(id) ON UPDATE RESTRICT ON DELETE RESTRICT;


--
-- TOC entry 2124 (class 2606 OID 207193)
-- Dependencies: 2083 203 199 2170
-- Name: usuario_rol_permiso_usuario_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: gestion
--

ALTER TABLE ONLY usuario_rol_permiso
    ADD CONSTRAINT usuario_rol_permiso_usuario_id_fkey FOREIGN KEY (usuario_id) REFERENCES usuario(id) ON UPDATE RESTRICT ON DELETE RESTRICT;


--
-- TOC entry 2175 (class 0 OID 0)
-- Dependencies: 6
-- Name: public; Type: ACL; Schema: -; Owner: postgres
--

REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM postgres;
GRANT ALL ON SCHEMA public TO postgres;
GRANT ALL ON SCHEMA public TO PUBLIC;


-- Completed on 2013-09-08 22:12:39 PYT

--
-- PostgreSQL database dump complete
--

