package logsys.dream.com.mx.contracts.viaje.to;

/**
 * Created by JUAHERNA on 2/26/2018.
 */

public class viajeTO {

    private int id;

    private String cliente;

    private String convenio;

    private int numeroSolicitud;

    private String shipment;

    private String cp;

    private String origen;

    private String direccionCarga;

    private String citaCarga;

    private String destino;

    private String direccionDescarga;

    private String citaDescarga;

    public String getoLongitud() {
        return oLongitud;
    }

    public void setoLongitud(String oLongitud) {
        this.oLongitud = oLongitud;
    }

    public String getoLatitud() {
        return oLatitud;
    }

    public void setoLatitud(String oLatitud) {
        this.oLatitud = oLatitud;
    }

    public String getdLongitud() {
        return dLongitud;
    }

    public void setdLongitud(String dLongitud) {
        this.dLongitud = dLongitud;
    }

    public String getdLatitud() {
        return dLatitud;
    }

    public void setdLatitud(String dLatitud) {
        this.dLatitud = dLatitud;
    }

    public String getIntermedios() {
        return intermedios;
    }

    public void setIntermedios(String intermedios) {
        this.intermedios = intermedios;
    }

    private String oLongitud;

    private String oLatitud;

    private String dLongitud;

    private String dLatitud;

    private String intermedios;


    public String getDoLatitud() {
        return doLatitud;
    }

    public void setDoLatitud(String doLatitud) {
        this.doLatitud = doLatitud;
    }

    public String getDoLongitud() {
        return doLongitud;
    }

    public void setDoLongitud(String doLongitud) {
        this.doLongitud = doLongitud;
    }

    public String getDdLongitud() {
        return ddLongitud;
    }

    public void setDdLongitud(String ddLongitud) {
        this.ddLongitud = ddLongitud;
    }

    public String getDdLatitud() {
        return ddLatitud;
    }

    public void setDdLatitud(String ddLatitud) {
        this.ddLatitud = ddLatitud;
    }

    public String getDintermedios() {
        return dintermedios;
    }

    public void setDintermedios(String dintermedios) {
        this.dintermedios = dintermedios;
    }

    private String doLatitud;

    private String doLongitud;

    private String ddLongitud;

    private String ddLatitud;

    private String dintermedios;


    public String getEstatus() {
        return estatus;
    }

    public void setEstatus(String estatus) {
        this.estatus = estatus;
    }

    private String estatus;

    public  viajeTO()
    {

    }

    public viajeTO(int id, String cp, String citaCarga, String citaDescarga, String cliente, String convenio, String destino, String direccionCarga, String direccionDescarga, int numeroSolicitud, String origen, String shipment,String estatus) {
        this.id = id;
        this.cp = cp;
        this.citaCarga = citaCarga;
        this.citaDescarga = citaDescarga;
        this.cliente = cliente;
        this.convenio = convenio;
        this.destino = destino;
        this.direccionCarga = direccionCarga;
        this.direccionDescarga = direccionDescarga;
        this.numeroSolicitud = numeroSolicitud;
        this.origen = origen;
        this.shipment = shipment;
        this.estatus = estatus;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCp() {
        return cp;
    }

    public void setCp(String cp) {
        this.cp = cp;
    }

    public String getCitaCarga() {
        return citaCarga;
    }

    public void setCitaCarga(String citaCarga) {
        this.citaCarga = citaCarga;
    }

    public String getCitaDescarga() {
        return citaDescarga;
    }

    public void setCitaDescarga(String citaDescarga) {
        this.citaDescarga = citaDescarga;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public String getConvenio() {
        return convenio;
    }

    public void setConvenio(String convenio) {
        this.convenio = convenio;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public String getDireccionCarga() {
        return direccionCarga;
    }

    public void setDireccionEntrega(String direccionEntrega) {
        this.direccionCarga = direccionEntrega;
    }

    public String getDireccionDescarga() {
        return direccionDescarga;
    }

    public void setDireccionDescarga(String direccionDescarga) {
        this.direccionDescarga = direccionDescarga;
    }

    public int getNumeroSolicitud() {
        return numeroSolicitud;
    }

    public void setNumeroSolicitud(int numeroSolicitud) {
        this.numeroSolicitud = numeroSolicitud;
    }

    public String getOrigen() {
        return origen;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }

    public String getShipment() {
        return shipment;
    }

    public void setShipment(String shipment) {
        this.shipment = shipment;
    }

    @Override
    public String toString() {
        return id + "\n" + citaCarga;

                /*
                this.id = id;
        this.cp = cp;
        this.citaCarga = citaCarga;
        this.citaDescarga = citaDescarga;
        this.cliente = cliente;
        this.convenio = convenio;
        this.destino = destino;
        this.direccionEntrega = direccionEntrega;
        this.direccionDescarga = direccionDescarga;
        this.numeroSolicitud = numeroSolicitud;
        this.origen = origen;
        this.shipment = shipment;
        this.estatus = estatus;
                 */
    }
}
