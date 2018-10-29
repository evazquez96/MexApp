package logsys.dream.com.mx.models;
import java.util.LinkedList;
import java.util.List;

public class Registro{

    private LinkedList<Dream> dreams;
    private double inactivo;
    private double activo ;

    public Registro(){
        /**
         * Constructo por defecto
         */
    }
    @Override
    public String toString(){
        return "Inactivo: "+inactivo+"\n"+
                "Activo:"+activo;
    }

    public LinkedList<Dream> getDreams() {
        return dreams;
    }

    public void setDreams(LinkedList<Dream> dreams) {
        this.dreams = dreams;
    }

    public double getInactivo() {
        return inactivo;
    }

    public void setInactivo(double inactivo) {
        this.inactivo = inactivo;
    }

    public double getActivo() {
        return activo;
    }

    public void setActivo(double activo) {
        this.activo = activo;
    }
}
