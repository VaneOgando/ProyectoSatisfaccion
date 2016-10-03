
ALTER TABLE ENC_PRE	ADD CONSTRAINT ep_fkEncuesta	FOREIGN KEY (idEncuesta)		REFERENCES ENCUESTA (idEncuesta);
ALTER TABLE ENC_PRE	ADD CONSTRAINT ep_fkPregunta	FOREIGN KEY (idPregunta)		REFERENCES PREGUNTA (idPregunta);


ALTER TABLE ENCUESTA ADD CONSTRAINT enc_tipoEncuesta_check
  CHECK (tipoEncuesta IN ('N', 'E'));
ALTER TABLE ENCUESTA ADD CONSTRAINT enc_estado_check
  CHECK (estado IN ('A', 'I'));


ALTER TABLE ENVIO 	ADD fkEncuesta 		INT NOT NULL;
ALTER TABLE ENVIO	ADD fkProyecto		INT; 

ALTER TABLE ENVIO ADD CONSTRAINT env_fkEncuesta 	FOREIGN KEY (fkEncuesta)		REFERENCES ENCUESTA (idEncuesta);
ALTER TABLE ENVIO ADD CONSTRAINT env_fkProyecto		FOREIGN KEY (fkProyecto)	REFERENCES PROYECTO (idProyecto);
ALTER TABLE ENVIO ADD CONSTRAINT env_estado_check
  CHECK (estado IN ('P', 'R'));

  
ALTER TABLE OPCION  ADD fkPregunta 	INT NOT NULL;

ALTER TABLE OPCION	ADD CONSTRAINT opc_fkPregunta	FOREIGN KEY (fkPregunta)		REFERENCES PREGUNTA (idPregunta);


ALTER TABLE PREGUNTA ADD CONSTRAINT pre_estado_check
  CHECK (estado IN ('A', 'I'));
ALTER TABLE PREGUNTA ADD CONSTRAINT pre_tipoPregunta_check
  CHECK (tipoPregunta IN ('abierta', 'simple', 'ranking'));
ALTER TABLE PREGUNTA ADD CONSTRAINT pre_tipoEncuesta_check
  CHECK (tipoEncuesta IN ('N', 'E'));


ALTER TABLE RESPUESTA 	ADD fkPregunta	INT NOT NULL;
ALTER TABLE RESPUESTA	ADD fkOpcion	INT; 
ALTER TABLE RESPUESTA	ADD fkEnvio		INT NOT NULL; 

ALTER TABLE RESPUESTA ADD CONSTRAINT res_fkPregunta		FOREIGN KEY (fkPregunta)		REFERENCES PREGUNTA (idPregunta);
ALTER TABLE RESPUESTA ADD CONSTRAINT res_fkOpcion		FOREIGN KEY (fkOpcion)	REFERENCES OPCION (idOpcion);
ALTER TABLE RESPUESTA ADD CONSTRAINT res_fkEnvio		FOREIGN KEY (fkEnvio)	REFERENCES ENVIO (idEnvio);
