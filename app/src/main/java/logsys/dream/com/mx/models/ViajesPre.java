package logsys.dream.com.mx.models;

public class ViajesPre {

    public ViajesPre(){

    }

    private int Solicitud;
    private int Shipment;
    private String Cp;
    private String Cliente;
    private String Origen;
    private String direccionOrigen;
    private String citaCarga;
    private String Destino;
    private String direccionDestino;
    private String citaDescarga;

    public int getSolicitud() {
        return Solicitud;
    }
    public void setSolicitud(int solicitud) {
        this.Solicitud = solicitud;
    }

    public int getShipment() {
        return Shipment;
    }

    public void setShipment(int shipment) {
        this.Shipment = shipment;
    }

    public String getCp() {
        return Cp;
    }
    public void setCp(String cp) {
        this.Cp = cp;
    }

    public String getCliente() {
        return Cliente;
    }
    public void setCliente(String cliente) {
        this.Cliente = cliente;
    }

    public String getOrigen() {
        return Origen;
    }
    public void setOrigen(String origen) {
        this.Origen = origen;
    }

    public String getDireccionOrigen() {
        return direccionOrigen;
    }
    public void setDireccionOrigen(String direccionOrigen) {
        this.direccionOrigen = direccionOrigen; }

    public String getCitaCarga() {
        return citaCarga;
    }
    public void setCitaCarga(String citaCarga) {
        this.citaCarga = citaCarga;
    }

    public String getDestino() {
        return Destino;
    }
    public void setDestino(String destino) {
        this.Destino = destino; }

    public String getDireccionDestino() {
        return direccionDestino;
    }
    public void setDireccionDestino(String direccionDestino) {
        this.direccionDestino = direccionDestino; }

    public String getCitaDescarga() {
        return citaDescarga;
    }
    public void setCitaDescarga(String citaDescarga) {
        this.citaDescarga = citaDescarga;
    }

}
