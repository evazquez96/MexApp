package logsys.dream.com.mx.contracts;

import dream.logsys.com.logsysdream.R;

/**
 * Created by paqti on 06/03/2017.
 */

public class dream_indicador {

    private long milisegundos;

    private String tiempo;

    private long rojo;

    private long amarillo;

    public float obtenerCalificacion()
    {
        int color = get_Color();
        if(color==R.color.rojo)
            return 0;
        else if(color ==R.color.amarillo)
            return 3;
        return  5;
    }

public dream_indicador()
{

}

    public long getMilisegundos() {
        return milisegundos;
    }

    public void setMilisegundos(long milisegundos) {
        this.milisegundos = milisegundos;
    }

    public String getTiempo() {
        return tiempo;
    }

    public void setTiempo(String tiempo) {
        this.tiempo = tiempo;
    }

    public long getRojo() {
        return rojo;
    }

    public void setRojo(int rojo) {
        this.rojo = rojo;
    }

    public long getAmarillo() {
        return amarillo;
    }

    public void setAmarillo(int amarillo) {
        this.amarillo = amarillo;
    }

    private int color;

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public int get_Color()
    {
        return color;
    }

    public int obtenerImagen()
    {
        int color = get_Color();
        if(color==R.color.rojo)
            return R.drawable.carita_sueno;
        else if(color == R.color.amarillo)
            return R.drawable.carita_cansado;
        return  R.drawable.carita_feliz;
    }


}
