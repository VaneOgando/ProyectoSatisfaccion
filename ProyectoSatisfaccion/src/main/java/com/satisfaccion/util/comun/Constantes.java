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

    public static final String GROWL_EXITO = "Exito_container";
    public static final String GROWL_ERROR = "Error_container";

    public static final String MENSAJE_TIPO_EXITO  = "Éxito";
    public static final String MENSAJE_TIPO_ERROR  = "Error";

    public static final int CANT_MINIMA_OPCIONES = 2;


    public static final String REPORTE_ASIGNACION = "cartaAsignacionRecurso.jasper";
    public static final String REPORTE_DEVOLUCION = "cartaDevolucionRecurso.jasper";
    public static final String REPORTE_RESUMEN_RECURSOS = "resumenRecursos.jasper";

    public static final String URL_LOGO = "resources/img/logo.png";

    public static final String NOMBRE_ARCHIVO_ENTREGA = "CartaEntrega_";
    public static final String NOMBRE_ARCHIVO_DEVOLUCION = "CartaDevolucion_";


    /*ERRORES*/
    public static final String ERR_LOGIN_INVALIDO = "Usuario y/o contraseña invalida";

    public static final String ERR_ELIMINAR_DEFINITIVO = "";
    public static final String ERR_ELIMINAR_DESACTIVAR = "";
    public static final String ERR_PREGUNTA_INACTIVA = "Acción no permitida. Esta pregunta se encuentra inactiva.";

    public static final String ERR_CANT_MINIMA_OPCIONES = "La pregunta debe tener al menos ? opciones";

    public static final String ERR_CAMBIO_ESTADO = "ERROR! El estado no se pudo cambiar";
    public static final String ERR_SELECCION_ITEM = "ERROR! Seleccione un item";
    public static final String ERR_SELECCION_RECURSO = "ERROR! Por favor seleccione un recurso a gestionar";
    public static final String ERR_GESTION = "ERROR! No se pudo gestionar el/los recurso(s)";
    public static final String ERR_MODIFICAR = "ERROR! No se pudo modificar el recurso";


    /*EXITO*/
    public static final String EX_ELIMINAR_DEFINITIVO = "La pregunta fue eliminada definitivamente exitosamente.";
    public static final String EX_ELIMINAR_DESACTIVAR = "La pregunta fue desactivada exitosamente, la misma no podra ser agregada ni respondida en ninguna encuesta.";



    public static final String EX_GESTION = "EXITO! El/los recurso(s) se gestionaron satisfactoriamente";
    public static final String EX_MODIFICAR = "EXITO! El recurso se modifico satisfactoriamente";

    public static final String EX_GESTION_ERR_REPORTE = "El/los recurso(s) se gestionaron satisfactoriamente, mas no se genero el reporte PDF. Por favor realizarlo manual";


}
