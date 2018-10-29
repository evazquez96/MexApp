package logsys.dream.com.mx.contracts;

/**
 * Created by ADGARCIA on 14/05/2018.
 */

public class EvidenciasContract {

    private String cliente;

    private String destino;

    private int noSolicitud;

    private int ordenVenta;

    private String origen;


    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public int getNoSolicitud() {
        return noSolicitud;
    }

    public void setNoSolicitud(int noSolicitud) {
        this.noSolicitud = noSolicitud;
    }

    public int getOrdenVenta() {
        return ordenVenta;
    }

    public void setOrdenVenta(int ordenVenta) {
        this.ordenVenta = ordenVenta;
    }

    public String getOrigen() {
        return origen;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }
}
