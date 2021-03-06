package com.satisfaccion.util.comun;

import javax.faces.bean.ManagedBean;
import java.util.ArrayList;
import java.util.Arrays;

@ManagedBean
public class Constantes {

    /*LISTAS COMUNES*/

    public static final ArrayList<String> tipoPreguntas = new ArrayList<String>(Arrays.asList(
            new String("simple"),
            new String("ranking"),
            new String("abierta")));


    /*CONSTANTES*/

    public static final String URL_ENCUESTA = "http://localhost:8000/encuesta/";

    public static final String ENVIO_ASUNTO_EVALUACION = "Invitacion a realizar evaluacion de TCS";
    public static final String ENVIO_ASUNTO_ENCUESTA = "Invitacion a realizar encuesta de TCS";

    public static final String KEY_ENCRIPT = "TechnoConsulSoluti78";
    public static final String CORREO_FROM = "encuestas@tcs.com.ve";
    public static final String NOMBRE_CORREO_FROM = "Encuestas TCS";


    public static final String MENSAJE_TIPO_EXITO  = "Éxito!";
    public static final String MENSAJE_TIPO_ERROR  = "Error!";

    public static final int CANT_MINIMA_OPCIONES = 2;

    public static final String NOMBRE_ENCUESTA_COPIA = "Copia de ";

    public static final String FORMATO_EMAIL = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
            + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";


    /*ERRORES*/
    public static final String ERR_LOGIN_INVALIDO = "Usuario y/o contraseña invalida";
    public static final String ERR_INESPERADO = "Ha ocurrido un error inesperado, intentelo más tarde";

    public static final String ERR_ELIMINAR_DEFINITIVO = "La pregunta/encuesta no pudo ser eliminada, intentelo más tarde";
    public static final String ERR_ELIMINAR_DESACTIVAR = "La pregunta/encuesta no pudo ser desactivada, intentelo más tarde";

    public static final String ERR_CREAR_PREGUNTA = "La pregunta no pudo ser creada, intentelo más tarde";
    public static final String ERR_MODIFICAR_PREGUNTA = "La pregunta no pudo ser modificada, intentelo más tarde";

    public static final String ERR_PREGUNTA_INACTIVA = "Acción no permitida. Esta pregunta se encuentra inactiva.";
    public static final String ERR_PREGUNTA_RESPONDIDA = "Acción no permitida. Esta pregunta ya posee respuestas almacenadas.";

    public static final String ERR_CREAR_ENCUESTA = "La encuesta no pudo ser creada, intentelo más tarde";
    public static final String ERR_MODIFICAR_ENCUESTA = "La encuesta no pudo ser modificada, intentelo más tarde";

    public static final String ERR_ENCUESTA_INACTIVA = "Acción no permitida. Esta encuesta se encuentra inactiva.";
    public static final String ERR_ENCUESTA_RESPONDIDA = "Acción no permitida. Esta encuesta ya posee respuestas almacenadas.";

    public static final String ERR_CANT_MINIMA_OPCIONES = "La pregunta debe tener un mínimo de opciones.";
    public static final String ERR_CANT_MINIMA_PREGUNTAS = "La encuesta debe tener al menos una pregunta asociada.";

    public static final String ERR_OPCION_VACIA = "Por favor ingrese una descripcion para cada una de las opciones.";
    public static final String ERR_NOMBRE_ENCUESTA = "Ya existe una encuesta con este nombre, por favor ingrese otro.";
    public static final String ERR_SIN_PREGUNTAS = "No existen preguntas activas para seleccionar.";
    public static final String ERR_ENCUESTA_ENVIO_INVALIDO = "Esta encuesta no puede ser vista/enviada ya que no posee preguntas activas.";

    public static final String ERR_PREGUNTAS_VACIAS = "Por favor no deje preguntas sin responder.";
    public static final String ERR_USUARIO_EVALUADO = "Por favor seleccione un usuario a evaluar.";

    public static final String ERR_CREAR_RESPUESTA = "Ha ocurrido un error al almacenar sus respuestas, por favor intentelo más tarde";

    public static final String ERR_FECHA_INVALIDA = "Por favor seleccione una fecha valida";


    /*EXITO*/
    public static final String EX_ELIMINAR_DEFINITIVO = "La pregunta/encuesta fue eliminada definitivamente exitosamente.";
    public static final String EX_ELIMINAR_DESACTIVAR = "La pregunta/encuesta fue desactivada exitosamente.";

    public static final String EX_CREAR_PREGUNTA = "La pregunta fue creada exitosamente.";
    public static final String EX_MODIFICAR_PREGUNTA = "La pregunta fue modificada exitosamente.";

    public static final String EX_CREAR_ENCUESTA = "La encuesta fue creada exitosamente.";
    public static final String EX_MODIFICAR_ENCUESTA = "La encuesta fue modificada exitosamente.";


    public static final String NO_REGISTROS = "No se encontraron registros";


}
