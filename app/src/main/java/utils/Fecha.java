package utils;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by JUAHERNA on 2/15/2017.
 */

public class Fecha {
    @Override
    public String toString()
    {
        return anio + ":" + mes + ":" + dia + "-" + hora + ":" + minuto;
    }

    public  Fecha()
    {

    }

    public JSONObject get() throws JSONException {
        JSONObject o = new JSONObject();
        o.put("dia",dia);
        o.put("mes",mes);
        o.put("anio",anio);
        o.put("hora",hora);
        o.put("minuto",minuto);
        o.put("segundo",segundo);
        return  o;
    }

    public Fecha(int dia,int mes,int anio,int hora,int minuto,int segundo)
    {
        this.dia = dia;
        this.mes = mes;
        this.anio = anio;
        this.hora = hora;
        this.minuto = minuto;
        this.segundo = segundo;
    }

    public int getDia() {
        return dia;
    }

    public void setDia(int dia) {
        this.dia = dia;
    }

    public int getMes() {
        return mes;
    }

    public void setMes(int mes) {
        this.mes = mes;
    }

    public int getAnio() {
        return anio;
    }

    public void setAnio(int anio) {
        this.anio = anio;
    }

    public int getHora() {
        return hora;
    }

    public void setHora(int hora) {
        this.hora = hora;
    }

    public int getMinuto() {
        return minuto;
    }

    public void setMinuto(int minuto) {
        this.minuto = minuto;
    }

    public int getSegundo() {
        return segundo;
    }

    public void setSegundo(int segundo) {
        this.segundo = segundo;
    }

    private int dia;
    private int mes;
    private  int anio;
    private  int hora;
    private int minuto;
    private  int segundo;


}
