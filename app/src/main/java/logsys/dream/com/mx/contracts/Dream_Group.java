package logsys.dream.com.mx.contracts;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JUAHERNA on 2/9/2017.
 */

public class Dream_Group {

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public List<Dream_Record> getDreams() {
        return Dreams;
    }

    public void setDreams(List<Dream_Record> dreams) {
        this.Dreams = dreams;
    }

    public int getHoras() {
        int horas = 0;
        for (Dream_Record d:
                Dreams) {
            horas+= d.getHoras();
        }
        return  horas + ((int)getMins()/60);
    }

    public int getMinutos() {
        return  getMins()%60;
    }

    private int getMins()
    {
        int minutos = 0;
        for (Dream_Record d:
                Dreams) {
            minutos+= d.getMinutos();
        }
        return  minutos;
    }

    public int getRegistros() {
        return Dreams.size();
    }

    private String day;

    private String fecha;

    private List<Dream_Record> Dreams;

    public int getNum_day() {
        return num_day;
    }

    public void setNum_day(int num_day) {
        this.num_day = num_day;
    }

    private int num_day;

    public  Dream_Group(String day,int num_day,String fecha)
    {
        this.day = day;
        this.num_day = num_day;
        this.fecha = fecha;
        this.Dreams = new ArrayList<Dream_Record>();
    }

    public  Dream_Group(String day,int num_day,String fecha,List<Dream_Record> Dreams)
    {
        this(day,num_day,fecha);
        this.Dreams = Dreams;
    }

    public void  add_Dream(Dream_Record dream)
    {
        this.Dreams.add(dream);
    }

    public String get_strTime()
    {
        return String.format(this.day + "," + fecha +   " %02d:%02d (" + this.getRegistros() + ")", getHoras(),getMinutos());
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
}