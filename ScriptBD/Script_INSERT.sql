/*INSERT DE ENCUESTAS*/

/*PREGUNTA*/

INSERT INTO PREGUNTA VALUES (pregunta_seq.nextval, '¿Cómo califica la calidad en la ENTREGA?', 'Por favor sea completamente objetivo', 'ranking', 'N', 'A', 'vanessa', '15/05/2016');
INSERT INTO PREGUNTA VALUES (pregunta_seq.nextval, '¿Cómo califica la CALIDAD PERCIBIDA del producto?', 'Por favor sea completamente objetivo', 'simple', 'N', 'A', 'vanessa', '15/05/2016');
INSERT INTO PREGUNTA VALUES (pregunta_seq.nextval, '¿Cómo califica el Nivel de Servicio para el Producto?', 'Por favor seleccione aquella opcion que posea mayor valor para usted', 'simple', 'N', 'A', 'vanessa', '18/05/2016');
INSERT INTO PREGUNTA VALUES (pregunta_seq.nextval, 'Ayudenos a mejorar, dejenos sus comentarios o sugerencias', null, 'abierta', 'N', 'A', 'vanessa', '18/05/2016');
INSERT INTO PREGUNTA VALUES (pregunta_seq.nextval, 'Como evalua el comportamiento de nuestros miembros del equipo en sus instalaiones', 'Seleccione la accion que mejor englobe su posicion', 'simple', 'N', 'I', 'maria', '28/01/2016');
INSERT INTO PREGUNTA VALUES (pregunta_seq.nextval, 'Describa el comportamiento de nuestros consultores', null, 'abierta', 'N', 'A', 'maria', '28/01/2016');


/*OPCION*/

INSERT INTO OPCION VALUES (opcion_seq.nextval, 'Es de exelente calidad', null, 2 );
INSERT INTO OPCION VALUES (opcion_seq.nextval, 'Supera las expectativas', null, 2 );
INSERT INTO OPCION VALUES (opcion_seq.nextval, 'Se puede recomendar', null, 2 );

INSERT INTO OPCION VALUES (opcion_seq.nextval, 'El producto satisface las necesidades', null, 3 );
INSERT INTO OPCION VALUES (opcion_seq.nextval, 'El servicio al cliente es inmediato', null, 3 );
INSERT INTO OPCION VALUES (opcion_seq.nextval, 'El producto tiene garantia por cualquier falla', null, 3 );

INSERT INTO OPCION VALUES (opcion_seq.nextval, 'Excelente, sin comentarios', null, 5 );
INSERT INTO OPCION VALUES (opcion_seq.nextval, 'Bien, alboroto a algunas horas del dia', null, 5 );
INSERT INTO OPCION VALUES (opcion_seq.nextval, 'Deficiente, incumplimiento de horarios, escandalos y desorden', null, 5 );


/*ENCUESTA*/

INSERT INTO ENCUESTA VALUES (encuesta_seq.nextval, 'calidad', 'Calidad de los servicios ofrecidos por TCS', 'A continuacion se realizan una serie de premisas, por favor seleccione la opcion que mejor se acerque a su opinion', 'N', 'Vivian', '22/05/2016' );
INSERT INTO ENCUESTA VALUES (encuesta_seq.nextval, 'servicio ofrecido', 'Encuesta satisfaccion de servicios ', null, 'N', 'Pablo', '04/06/2016' );
INSERT INTO ENCUESTA VALUES (encuesta_seq.nextval, 'comportamiento', 'Encuesta del comportamiento de equipos de TCS en sus clientes', null, 'N', 'Maria', '01/06/2016' );


/*ENC_PRE*/

INSERT INTO ENC_PRE VALUES (enc_pre_seq.nextval, 1, 1 );
INSERT INTO ENC_PRE VALUES (enc_pre_seq.nextval, 1, 2 );

INSERT INTO ENC_PRE VALUES (enc_pre_seq.nextval, 2, 3 );
INSERT INTO ENC_PRE VALUES (enc_pre_seq.nextval, 2, 4 );

INSERT INTO ENC_PRE VALUES (enc_pre_seq.nextval, 3, 5 );
INSERT INTO ENC_PRE VALUES (enc_pre_seq.nextval, 3, 6 );


/*PROYECTO*/

INSERT INTO PROYECTO VALUES (proyecto_seq.nextval, 'Movistar');
INSERT INTO PROYECTO VALUES (proyecto_seq.nextval, 'BOD Cobranza');
INSERT INTO PROYECTO VALUES (proyecto_seq.nextval, 'Banesco');


/*ENVIO*/

INSERT INTO ENVIO VALUES (envio_seq.nextval, '01/06/2016', 'pedro.jose@hotmail.com', 'R', 1, 1);
INSERT INTO ENVIO VALUES (envio_seq.nextval, '06/06/2016', 'maria.delgado@hotmail.com', 'R', 1, 1);
INSERT INTO ENVIO VALUES (envio_seq.nextval, '01/07/2016', 'alberto.figueroa@hotmail.com', 'R', 1, 3);
INSERT INTO ENVIO VALUES (envio_seq.nextval, '01/07/2016', 'pedro.jose@hotmail.com', 'R', 1, 1);
INSERT INTO ENVIO VALUES (envio_seq.nextval, '03/07/2016', 'maria.delgado@hotmail.com', 'P', 1, 3);

INSERT INTO ENVIO VALUES (envio_seq.nextval, '10/07/2016', 'genesis.machado@gmail.com', 'P', 2, 3);
INSERT INTO ENVIO VALUES (envio_seq.nextval, '10/07/2016', 'carolina.perez@gmail.com', 'R', 2, 3);
INSERT INTO ENVIO VALUES (envio_seq.nextval, '25/07/2016', 'julia.serrano@gmail.com', 'R', 2, 2);
INSERT INTO ENVIO VALUES (envio_seq.nextval, '28/07/2016', 'maria.delgado@hotmail.com', 'R', 2, 1);
INSERT INTO ENVIO VALUES (envio_seq.nextval, '01/08/2016', 'pedro.jose@hotmail.com', 'R', 2, 1);

INSERT INTO ENVIO VALUES (envio_seq.nextval, '03/02/2016', 'julio.chacon@hotmail.com', 'R', 3, 1);
INSERT INTO ENVIO VALUES (envio_seq.nextval, '03/02/2016', 'miranda.palacios@hotmail.com', 'R', 3, 2);
INSERT INTO ENVIO VALUES (envio_seq.nextval, '03/02/2016', 'victoria.knight@hotmail.com', 'R', 3, 3);


/*RESPUESTA*/

INSERT INTO RESPUESTA VALUES (respuesta_seq.nextval, null, 3, null, 1, null, 1);
INSERT INTO RESPUESTA VALUES (respuesta_seq.nextval, null, null, null, 2, 2, 1);

INSERT INTO RESPUESTA VALUES (respuesta_seq.nextval, null, 3.5, null, 1, null, 2);
INSERT INTO RESPUESTA VALUES (respuesta_seq.nextval, null, null, null, 2, 2, 2);

INSERT INTO RESPUESTA VALUES (respuesta_seq.nextval, null, 4, null, 1, null, 3);
INSERT INTO RESPUESTA VALUES (respuesta_seq.nextval, null, null, null, 2, 2, 3);

INSERT INTO RESPUESTA VALUES (respuesta_seq.nextval, null, 4, null, 1, null, 4);
INSERT INTO RESPUESTA VALUES (respuesta_seq.nextval, null, null, null, 2, 3, 4);

INSERT INTO RESPUESTA VALUES (respuesta_seq.nextval, null, null, null, 3, 4, 7);
INSERT INTO RESPUESTA VALUES (respuesta_seq.nextval, 'Satisfecho con el trabajo realizado, responsable y oportuno', null, null, 4, null, 7);

INSERT INTO RESPUESTA VALUES (respuesta_seq.nextval, null, null, null, 3, 4, 8);
INSERT INTO RESPUESTA VALUES (respuesta_seq.nextval, 'Ningun comentario al respecto, excelente atencion y calidad', null, null, 4, null, 8);

INSERT INTO RESPUESTA VALUES (respuesta_seq.nextval, null, null, null, 3, 6, 9);
INSERT INTO RESPUESTA VALUES (respuesta_seq.nextval, 'Deben mejorar los tiempos de entrega y cumplir con lo prometido. Los retrasos son de hasta 2 semanas', null, null, 4, null, 9);

INSERT INTO RESPUESTA VALUES (respuesta_seq.nextval, null, null, null, 3, 6, 10);
INSERT INTO RESPUESTA VALUES (respuesta_seq.nextval, 'Mejor disponibilidad para el proyecto, se encuentra en fase final y se siente minimo el compromiso', null, null, 4, null, 10);

INSERT INTO RESPUESTA VALUES (respuesta_seq.nextval, null, null, null, 5, 9, 11);
INSERT INTO RESPUESTA VALUES (respuesta_seq.nextval, 'Incumplimiento en horarios, tanto en horas de entrada como en exceso de eventos por parte de TCS donde se pierte 1 dia a la semana', null, null, 6, null, 11);

INSERT INTO RESPUESTA VALUES (respuesta_seq.nextval, null, null, null, 5, 7, 12);
INSERT INTO RESPUESTA VALUES (respuesta_seq.nextval, 'Sin comentarios, exelente comportamiento', null, null, 6, null, 12);

INSERT INTO RESPUESTA VALUES (respuesta_seq.nextval, null, null, null, 5, 7, 13);
INSERT INTO RESPUESTA VALUES (respuesta_seq.nextval, 'Muchas felicidades por sus consultores, exelente comportamiento. Amable con el personal y dispuestos a ayudar en todo momento', null, null, 6, null, 13);




/*INSERT DE EVALUACIONES*/

/*PREGUNTA*/

INSERT INTO PREGUNTA VALUES (pregunta_seq.nextval, '¿Cómo califica la PUNTUALIDAD del recurso?', 'Apoyarse en los registros de entrada y salida del PM', 'simple', 'E', 'A', 'Ricardo', '01/01/2016');
INSERT INTO PREGUNTA VALUES (pregunta_seq.nextval, '¿Cómo califica el desempeño del recurso?', 'Tome en cuenta todos los aspectos valorados por la empresa', 'ranking', 'E', 'A', 'Ricardo', '01/01/2016');


/*OPCION*/

INSERT INTO OPCION VALUES (opcion_seq.nextval, 'Puntual', 5, 7 );
INSERT INTO OPCION VALUES (opcion_seq.nextval, 'Hay excepciones', 4, 7 );
INSERT INTO OPCION VALUES (opcion_seq.nextval, 'No se encuentra en las instalaciones', 3, 7 );
INSERT INTO OPCION VALUES (opcion_seq.nextval, 'Deficiente', 1, 7 );


/*ENCUESTA*/

INSERT INTO ENCUESTA VALUES (encuesta_seq.nextval, 'evaluacion TCS', 'Evaluacion mensual a miembros del equipo de TCS', null, 'E', 'Ricardo', '01/01/2016' );


/*ENC_PRE*/

INSERT INTO ENC_PRE VALUES (enc_pre_seq.nextval, 4, 7 );
INSERT INTO ENC_PRE VALUES (enc_pre_seq.nextval, 4, 8 );


/*ENVIO*/

INSERT INTO ENVIO VALUES (envio_seq.nextval, '01/01/2016', 'lideres@hotmail.com', 'R', 4, null);
INSERT INTO ENVIO VALUES (envio_seq.nextval, '01/07/2016', 'lideres@hotmail.com', 'R', 4, null);


/*RESPUESTA*/

INSERT INTO RESPUESTA VALUES (respuesta_seq.nextval, null, null, 'estefania.fernandez', 7, 11, 14);
INSERT INTO RESPUESTA VALUES (respuesta_seq.nextval, null, 4, 'estefania.fernandez', 8, null, 14);

INSERT INTO RESPUESTA VALUES (respuesta_seq.nextval, null, null, 'estefania.fernandez', 7, 13, 15);
INSERT INTO RESPUESTA VALUES (respuesta_seq.nextval, null, 5, 'estefania.fernandez', 8, null, 15);
