package logsys.dream.com.mx.contracts;

/**
 * Created by JUAHERNA on 2/9/2017.
 */

public class Dream_Record {

    private int id;

    private String tiempo;

    private  int horas;

    private  int minutos;

    public int getNum_dia() {
        return num_dia;
    }

    public void setNum_dia(int num_dia) {
        this.num_dia = num_dia;
    }

    private int num_dia;

    public  Dream_Record(int id,int num_dia,String tiempo,int horas,int minutos,String comentarios)
    {
        this.num_dia = num_dia;
        this.id= id;
        this.tiempo = tiempo;
        this.horas = horas;
        this.minutos = minutos;
        this.comentarios = comentarios;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTiempo() {
        return String.format(" %02d:%02d  [" + tiempo + "] " + comentarios,horas,minutos);
    }

    public int getHoras() {
        return horas;
    }

    public void setHoras(int horas) {
        this.horas = horas;
    }

    public int getMinutos() {
        return minutos;
    }

    public void setMinutos(int minutos) {
        this.minutos = minutos;
    }

    public String getComentarios() {
        return comentarios;
    }

    public void setComentarios(String comentarios) {
        this.comentarios = comentarios;
    }

    private  String comentarios;
}
