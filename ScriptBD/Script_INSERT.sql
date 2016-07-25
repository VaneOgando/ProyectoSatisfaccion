/*PREGUNTA*/

INSERT INTO PREGUNTA VALUES (pregunta_seq.nextval, '¿Cómo califica la calidad en la ENTREGA?', 'Por favor sea completamente objetivo', 'ranking', 'A', 'vanessa', '15/05/2016');
INSERT INTO PREGUNTA VALUES (pregunta_seq.nextval, '¿Cómo califica la CALIDAD PERCIBIDA del producto?', 'Por favor sea completamente objetivo', 'simple', 'A', 'vanessa', '15/05/2016');
INSERT INTO PREGUNTA VALUES (pregunta_seq.nextval, '¿Cómo califica el Nivel de Servicio para el Producto?', 'Por favor seleccione aquella opcion que posea mayor valor para usted', 'simple', 'A', 'vanessa', '18/05/2016');
INSERT INTO PREGUNTA VALUES (pregunta_seq.nextval, 'Ayudenos a mejorar, dejenos sus comentarios o sugerencias', null, 'abierta', 'A', 'vanessa', '18/05/2016');


/*OPCION*/
INSERT INTO OPCION VALUES (opcion_seq.nextval, 'Es de exelente calidad',(SELECT idPregunta FROM PREGUNTA WHERE titulo='¿Cómo califica la CALIDAD PERCIBIDA del producto?') );
INSERT INTO OPCION VALUES (opcion_seq.nextval, 'Supera las expectativas', (SELECT idPregunta FROM PREGUNTA WHERE titulo='¿Cómo califica la CALIDAD PERCIBIDA del producto?') );
INSERT INTO OPCION VALUES (opcion_seq.nextval, 'Se puede recomendar', (SELECT idPregunta FROM PREGUNTA WHERE titulo='¿Cómo califica la CALIDAD PERCIBIDA del producto?') );

INSERT INTO OPCION VALUES (opcion_seq.nextval, 'El producto satisface las necesidades', (SELECT idPregunta FROM PREGUNTA WHERE titulo='¿Cómo califica el Nivel de Servicio para el Producto?') );
INSERT INTO OPCION VALUES (opcion_seq.nextval, 'El servicio al cliente es inmediato', (SELECT idPregunta FROM PREGUNTA WHERE titulo='¿Cómo califica el Nivel de Servicio para el Producto?') );
INSERT INTO OPCION VALUES (opcion_seq.nextval, 'El producto tiene garantia por cualquier falla', (SELECT idPregunta FROM PREGUNTA WHERE titulo='¿Cómo califica el Nivel de Servicio para el Producto?') );


/*ENCUESTA*/
INSERT INTO ENCUESTA VALUES (encuesta_seq.nextval, 'calidad', 'Calidad de los servicios ofrecidos por TCS', 'A continuacion se realizan una serie de premisas, por favor seleccione la opcion que mejor se acerque a su opinion', 'Vivian', '22/05/2016' );
INSERT INTO ENCUESTA VALUES (encuesta_seq.nextval, 'servicio ofrecido', 'Encuesta satisfaccion de servicios ', null, 'Pablo', '04/06/2016' );

/*ENC_PRE*/
INSERT INTO ENC_PRE VALUES (enc_pre_seq.nextval, (SELECT idEncuesta FROM ENCUESTA WHERE encuesta='calidad'), (SELECT idPregunta FROM PREGUNTA WHERE titula='¿Cómo califica la calidad en la ENTREGA?') );
INSERT INTO ENC_PRE VALUES (enc_pre_seq.nextval, (SELECT idEncuesta FROM ENCUESTA WHERE encuesta='calidad'), (SELECT idPregunta FROM PREGUNTA WHERE titula='¿Cómo califica la CALIDAD PERCIBIDA del producto?') );

INSERT INTO ENC_PRE VALUES (enc_pre_seq.nextval, (SELECT idEncuesta FROM ENCUESTA WHERE encuesta='servicio ofrecido'), (SELECT idPregunta FROM PREGUNTA WHERE titula='¿Cómo califica el Nivel de Servicio para el Producto?') );
INSERT INTO ENC_PRE VALUES (enc_pre_seq.nextval, (SELECT idEncuesta FROM ENCUESTA WHERE encuesta='servicio ofrecido'), (SELECT idPregunta FROM PREGUNTA WHERE titula='Ayudenos a mejorar, dejenos sus comentarios o sugerencias') );


/*PROYECTO*/
INSERT INTO PROYECTO VALUES (proyecto.nextval, 'Movistar');
INSERT INTO PROYECTO VALUES (proyecto.nextval, 'BOD Cobranza');
INSERT INTO PROYECTO VALUES (proyecto.nextval, 'Banesco');


/*ENVIO*/
INSERT INTO ENVIO VALUES (envio.nextval, '01/06/2016', 'pedro.jose@hotmail.com', 'R', (SELECT idEncuesta FROM ENCUESTA WHERE encuesta='calidad'), (SELECT idProyecto FROM PROYECTO WHERE proyecto='Movistar');
INSERT INTO ENVIO VALUES (envio.nextval, '01/06/2016', 'maria.delgado@hotmail.com', 'R', (SELECT idEncuesta FROM ENCUESTA WHERE encuesta='calidad'), (SELECT idProyecto FROM PROYECTO WHERE proyecto='Movistar');
INSERT INTO ENVIO VALUES (envio.nextval, '01/07/2016', 'alberto.figueroa@hotmail.com', 'R', (SELECT idEncuesta FROM ENCUESTA WHERE encuesta='calidad'), (SELECT idProyecto FROM PROYECTO WHERE proyecto='Movistar');
INSERT INTO ENVIO VALUES (envio.nextval, '01/07/2016', 'pedro.jose@hotmail.com', 'R', (SELECT idEncuesta FROM ENCUESTA WHERE encuesta='calidad'), (SELECT idProyecto FROM PROYECTO WHERE proyecto='Movistar');
INSERT INTO ENVIO VALUES (envio.nextval, '01/07/2016', 'maria.delgado@hotmail.com', 'P', (SELECT idEncuesta FROM ENCUESTA WHERE encuesta='calidad'), (SELECT idProyecto FROM PROYECTO WHERE proyecto='Movistar');



/*RESPUESTA*/


