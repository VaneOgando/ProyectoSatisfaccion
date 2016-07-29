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

    public static final String D_DESC_HISTORIAL_CREACION_EQ  = "Creación del equipo ";
    public static final String D_DESC_HISTORIAL_CREACION_ACC = "Creación del accesorio ";

    public static final String REPORTE_ASIGNACION = "cartaAsignacionRecurso.jasper";
    public static final String REPORTE_DEVOLUCION = "cartaDevolucionRecurso.jasper";
    public static final String REPORTE_RESUMEN_RECURSOS = "resumenRecursos.jasper";

    public static final String URL_LOGO = "resources/img/logo.png";

    public static final String NOMBRE_ARCHIVO_ENTREGA = "CartaEntrega_";
    public static final String NOMBRE_ARCHIVO_DEVOLUCION = "CartaDevolucion_";


    /*ERRORES*/
    public static final String ERR_LOGIN_INVALIDO = "Usuario y/o contraseña invalida";
    public static final String ERR_CREAR = "ERROR! No se pudo ingresar el recurso";
    public static final String ERR_RECURSO_ELIMINADO = "ERROR! Este recurso se encuentra fuera del satisfaccion";
    public static final String ERR_RECURSO_ASIGNADO = "ERROR! Este recurso se encuentra asignado, por favor realizar su respectiva devolución";
    public static final String ERR_CAMBIO_ESTADO = "ERROR! El estado no se pudo cambiar";
    public static final String ERR_SELECCION_ITEM = "ERROR! Seleccione un item";
    public static final String ERR_SELECCION_RECURSO = "ERROR! Por favor seleccione un recurso a gestionar";
    public static final String ERR_GESTION = "ERROR! No se pudo gestionar el/los recurso(s)";
    public static final String ERR_MODIFICAR = "ERROR! No se pudo modificar el recurso";


    /*EXITO*/
    public static final String EX_CREAR = "EXITO! El recurso se ingreso satisfactoriamente";
    public static final String EX_CAMBIO_ESTADO = "EXITO! El estado se cambio satisfactoriamente";
    public static final String EX_GESTION = "EXITO! El/los recurso(s) se gestionaron satisfactoriamente";
    public static final String EX_MODIFICAR = "EXITO! El recurso se modifico satisfactoriamente";

    public static final String EX_GESTION_ERR_REPORTE = "El/los recurso(s) se gestionaron satisfactoriamente, mas no se genero el reporte PDF. Por favor realizarlo manual";


}
