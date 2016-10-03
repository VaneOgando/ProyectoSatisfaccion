/*CREAR TABLAS*/

CREATE TABLE ENC_PRE(

	idEncuesta			INT			NOT NULL,
	idPregunta			INT			NOT NULL,
	
	CONSTRAINT enc_pre_pk PRIMARY KEY (idEncuesta, idPregunta)
);

CREATE TABLE ENCUESTA(

	idEncuesta 			INT 		NOT NULL,
	encuesta			VARCHAR(50) NOT NULL,
	titulo				VARCHAR(250),
	descripcion			VARCHAR(500),
	tipoEncuesta		CHAR(1)		NOT NULL,
	estado				CHAR(1)		NOT NULL,
	usuarioCreador		VARCHAR(50) NOT NULL,
	fechaCreacion		DATE		NOT NULL,
	
	CONSTRAINT encuesta_pk PRIMARY KEY (idEncuesta)
);

CREATE TABLE ENVIO(

	idEnvio 			INT 		NOT NULL,
	fechaEnvio			DATE 		NOT NULL,
	usuario				VARCHAR(50) NOT NULL,
	estado				CHAR(1)		NOT NULL,
	
	CONSTRAINT envio_pk PRIMARY KEY (idEnvio)
);

CREATE TABLE OPCION(

	idOpcion 			INT 		 NOT NULL,
	opcion				VARCHAR(250) NOT NULL,
	valor				FLOAT,
	
	CONSTRAINT opcion_pk PRIMARY KEY (idOpcion)
);

CREATE TABLE PREGUNTA(

	idPregunta 			INT 		 NOT NULL,
	titulo				VARCHAR(250) NOT NULL,
	ayuda				VARCHAR(250),
	tipoPregunta		VARCHAR(50)  NOT NULL,
	tipoEncuesta		CHAR(1)		 NOT NULL,
	estado				CHAR(1) 	 NOT NULL,
	escalaValoracion	FLOAT,
	usuarioCreador		VARCHAR(50)  NOT NULL,
	fechaCreacion		DATE		 NOT NULL,
	
	CONSTRAINT pregunta_pk PRIMARY KEY (idPregunta)
);

CREATE TABLE PROYECTO(

	idProyecto 			INT 		 NOT NULL,
	proyecto			VARCHAR(250) NOT NULL,
		
	CONSTRAINT proyecto_pk PRIMARY KEY (idProyecto)
);

CREATE TABLE RESPUESTA(

	idRespuesta			INT 		 NOT NULL,
	observacion			VARCHAR(500),
	valoracion			FLOAT,
	usuarioEvaluado 	VARCHAR(50),
			
	CONSTRAINT respuesta_pk PRIMARY KEY (idRespuesta)
);



/*CREAR SECUENCIAS*/

CREATE SEQUENCE enc_pre_seq   	INCREMENT BY 1 START WITH 1 NOCYCLE;
CREATE SEQUENCE encuesta_seq  	INCREMENT BY 1 START WITH 1 NOCYCLE;
CREATE SEQUENCE envio_seq     	INCREMENT BY 1 START WITH 1 NOCYCLE;
CREATE SEQUENCE opcion_seq   	INCREMENT BY 1 START WITH 1 NOCYCLE;
CREATE SEQUENCE pregunta_seq    INCREMENT BY 1 START WITH 1 NOCYCLE;
CREATE SEQUENCE proyecto_seq    INCREMENT BY 1 START WITH 1 NOCYCLE;
CREATE SEQUENCE respuesta_seq	INCREMENT BY 1 START WITH 1 NOCYCLE;

