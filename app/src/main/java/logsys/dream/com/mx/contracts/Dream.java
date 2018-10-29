package logsys.dream.com.mx.contracts;

/**
 * Created by JUAHERNA on 2/15/2017.
 */

public class Dream {

    public Dream()
    {
        this.enviado=0;
    }

    public String getComentarios() {
        return comentarios;
    }

    public long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(long usuarioId) {
        this.usuarioId = usuarioId;
    }

    public long usuarioId;

    public void setComentarios(String comentarios) {
        this.comentarios = comentarios;
    }

    public java.util.Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(java.util.Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public java.util.Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(java.util.Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private String comentarios;
/*
    public Fecha getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Fecha fechaFin) {
        this.fechaFin = fechaFin;
    }

    public Fecha getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Fecha fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    private Fecha fechaFin;

    private  Fecha fechaInicio;
*/
    //private java.util.Date test;

    private java.util.Date fechaFin;

    private java.util.Date fechaInicio;

    private long enviado;

    private  int id;

    public long getSql_id() {
        return sql_id;
    }

    public void setSql_id(long sql_id) {
        this.sql_id = sql_id;
    }

    private  long sql_id;

    private long tipoActividadId;

    public long getEnviado() {
        return enviado;
    }

    public void setEnviado(long enviado) {
        this.enviado = enviado;
    }

    public long getTipoActividadId() {
        return tipoActividadId;
    }

    public void setTipoActividadId(long tipoActividadId) {
        this.tipoActividadId = tipoActividadId;
    }
/*
    public java.util.Date getTest() {
        return test;
    }

    public void setTest(java.util.Date test) {
        this.test = test;
    }
    */


    @Override
    public String toString() {
        return "Sueño: tostring:" + this.id + " " + this.fechaInicio + " " + this.fechaFin + " " + this.enviado + " sqlId:" + this.sql_id ;
    }
}
