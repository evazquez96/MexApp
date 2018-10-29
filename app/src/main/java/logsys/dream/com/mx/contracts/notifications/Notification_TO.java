package logsys.dream.com.mx.contracts.notifications;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JUAHERNA on 2/22/2018.
 */

public class Notification_TO {

    private int id;

    private int tipoNotificacion_id;

    private String tipoNotificacion;

    private String fecha;

    private String comentarios;

    private List<Notification_Tracking> seguimiento;

    public  Notification_TO()
    {
        this.seguimiento= new ArrayList<Notification_Tracking>(0);
    }

    public Notification_TO(int id,int tipoNotificacion_id,String tipoNotificacion,String fecha,String comentarios,List<Notification_Tracking> seguimiento)
    {
        this.id = id;
        this.tipoNotificacion = tipoNotificacion;
        this.fecha = fecha;
        this.comentarios = comentarios;
        this.seguimiento = seguimiento;
    }

    public  int get_Total()
    {
        return  seguimiento.size();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTipoNotificacion_id() {
        return tipoNotificacion_id;
    }

    public void setTipoNotificacion_id(int tipoNotificacion_id) {
        this.tipoNotificacion_id = tipoNotificacion_id;
    }

    public String getTipoNotificacion() {
        return tipoNotificacion;
    }

    public void setTipoNotificacion(String tipoNotificacion) {
        this.tipoNotificacion = tipoNotificacion;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getComentarios() {
        return comentarios;
    }

    public void setComentarios(String comentarios) {
        this.comentarios = comentarios;
    }

    public List<Notification_Tracking> getSeguimiento() {
        return seguimiento;
    }

    public void setSeguimiento(List<Notification_Tracking> seguimiento) {
        this.seguimiento = seguimiento;
    }

    @Override
    public String toString() {
        return "{Id: " + id + ", comentarios: " + comentarios + " , Fecha " + fecha + ", Tipo Notificacion " + tipoNotificacion + "}";
    }

    //imagen de acuerdo a prioridad
}
