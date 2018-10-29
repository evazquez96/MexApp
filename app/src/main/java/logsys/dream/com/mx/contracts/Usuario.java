package logsys.dream.com.mx.contracts;

/**
 * Created by JUAHERNA on 2/15/2017.
 */

public class Usuario {


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario){
        this.nombreUsuario = nombreUsuario;
    }

    private int id;

    private String nombreUsuario;

    private  String respuesta;

    public String getUnidad() {
        return unidad;
    }

    public void setUnidad(String unidad) {
        this.unidad = unidad;
    }

    private  String unidad;

    private  int codigoRespuesta;

    private String password;

    private String nombre;

    private String imei;


    @Override
    public String toString()
    {
        return  nombreUsuario + ":" +password + ":" + codigoRespuesta + ":" + nombre + ":" + unidad;
    }

    public String getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(String respuesta) {
        this.respuesta = respuesta;
    }

    public int getCodigRespuesta() {
        return codigoRespuesta;
    }

    public void setCodigRespuesta(int codigRespuesta) {
        this.codigoRespuesta = codigRespuesta;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }
}
