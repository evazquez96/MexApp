package logsys.dream.com.mx.contracts.notifications;

/**
 * Created by JUAHERNA on 2/22/2018.
 */

public class Notification_Tracking {

    private int id;

    private String comentario;

    private String usuario;

    private String fecha;

    public Notification_Tracking(int id, String comentario, String usuario, String fecha) {
        this.id = id;
        this.comentario = comentario;
        this.usuario = usuario;
        this.fecha = fecha;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
}
