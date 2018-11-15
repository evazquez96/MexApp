package logsys.dream.com.mx.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.text.SimpleDateFormat;
import java.util.TimeZone;

import dream.logsys.com.logsysdream.R;
import logsys.dream.com.mx.contracts.Dream;
import logsys.dream.com.mx.contracts.Dream_Dashboard;
import logsys.dream.com.mx.contracts.dream_indicador;

/**
 * Created by JUAHERNA on 2/2/2017.
 */

public class DreamDB extends DBHelper {

    public static final String TABLE_NAME = "time_Table";
    public DreamDB(Context context) {
        super(context);
    }

    public void onCreate(SQLiteDatabase db) {
        super.onCreate(db);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        super.onUpgrade(db,oldVersion,newVersion);
    }

    public  void  actualizarSueno(Dream sueno)
    {
        ContentValues contentValues = new ContentValues();
        if(sueno.getFechaInicio()!=null)
            contentValues.put("start_date", sueno.getFechaInicio().getTime());
        if(sueno.getFechaFin()!=null)
            contentValues.put("end_date", sueno.getFechaFin().getTime());
        contentValues.put("value", sueno.getComentarios());
        contentValues.put("user_Id", sueno.getUsuarioId());
        contentValues.put("enviado", sueno.getEnviado());
        if(sueno.getId()!=0)
            contentValues.put("id", sueno.getId());

        updateQuery(TABLE_NAME,contentValues,"sql_id=?",new String[] {String.valueOf(sueno.getSql_id())});
    }

    public  long insertarSueno(Dream sueno) {
        ContentValues contentValues = new ContentValues();
        if(sueno.getFechaInicio()!=null)
            contentValues.put("start_date", sueno.getFechaInicio().getTime());

        if(sueno.getFechaFin()!=null)
            contentValues.put("end_date", sueno.getFechaFin().getTime());
        contentValues.put("value", sueno.getComentarios());
        contentValues.put("user_Id", sueno.getUsuarioId());
        contentValues.put("enviado", sueno.getEnviado());
        contentValues.put("tipo_actividad_id", sueno.getTipoActividadId());

        if(sueno.getId()!=0)
            contentValues.put("id", sueno.getId());
        if(sueno.getSql_id()!=0)
            contentValues.put("sql_id", sueno.getSql_id());
        return this.insertar(TABLE_NAME,contentValues);
    }

    public Dream_Dashboard obtenerDashboard(long usuarioId)
    {
        Dream_Dashboard dashboard = new Dream_Dashboard();

        dashboard.setI_ultimas_24(obtenerIndicador24(usuarioId));
        dashboard.setI_ultimas_48(obtenerIndicador48(usuarioId));
        dashboard.setI_semanal(obtenerIndicadorSemana(usuarioId));
        return  dashboard;
    }

    public dream_indicador obtenerIndicador24(long usuarioId)
    {
        dream_indicador indicador = new dream_indicador();
        float total1 = 0, total2 = 0;
        try
        {
            String query = "select " +
            " id, " +
            " strftime('%H:%M', " +
            " sum( " +
            " julianday(datetime( end_date/1000,'unixepoch','localtime'))-case " +
            " when datetime(start_date/1000,'unixepoch','localtime') <= datetime(strftime('%s', 'now','-37 hours'),'unixepoch','localtime') " +
            " then julianday(datetime(strftime('%s', 'now','-37 hours'),'unixepoch','localtime')) " +
            " else julianday(datetime(start_date/1000,'unixepoch','localtime')) " +
            " end) " +
            " ,'12:00') as tiempo, " +
            " sum( " +
            " julianday(datetime( end_date/1000,'unixepoch','localtime'))-case " +
            " when datetime(start_date/1000,'unixepoch','localtime') <= datetime(strftime('%s', 'now','-37 hours'),'unixepoch','localtime') " +
            " then julianday(datetime(strftime('%s', 'now','-37 hours'),'unixepoch','localtime')) " +
            " else julianday(datetime(start_date/1000,'unixepoch','localtime')) " +
            " end) *86400000 as milis " +
            " from time_Table " +
            " where " +
            " 1=1  and " +
            " datetime(end_date/1000,'unixepoch','localtime') >= datetime(strftime('%s', 'now','-37 hours'),'unixepoch','localtime')  and " +
            " tipo_actividad_id=1 " +
            " and user_id=" + usuarioId;
//86400000
            /*Select Cast ((
                JulianDay(ToDate) - JulianDay(FromDate)
        ) * 24 * 60 * 60 As Integer)
                */
            Cursor res = this.getDataQuery(query);
            res.moveToFirst();


int contador = 1;
            while(res.isAfterLast() == false){
                //System.out.println("::::::::::::::::::::::::indicador 24:" + res.getLong(res.getColumnIndex("milis")));
                //indicador.setMilisegundos(res.getLong(res.getColumnIndex("milis")));
                total1 = res.getFloat(res.getColumnIndex("milis"));
                //indicador.setAmarillo(18000000);
                //indicador.setRojo(7200000);
                if(res.getString(res.getColumnIndex("tiempo"))!=null)
                    indicador.setTiempo("[" + res.getString(res.getColumnIndex("tiempo")) + "]");
                else
                    indicador.setTiempo("[N/A]");
                //System.out.println("::::::::::::::::::::::::indicador 24t:" + indicador.getTiempo());
                res.moveToNext();
            }


            query = "select " +
                    " sum( " +
                    " julianday(datetime( end_date/1000,'unixepoch','localtime'))-case " +
                    " when datetime(start_date/1000,'unixepoch','localtime') <= datetime(strftime('%s', 'now','-37 hours'),'unixepoch','localtime') " +
                    " then julianday(datetime(strftime('%s', 'now','-37 hours'),'unixepoch','localtime')) " +
                    " else julianday(datetime(start_date/1000,'unixepoch','localtime')) " +
                    " end) *86400000 as milis " +
                    " from time_Table " +
                    " where " +
                    " 1=1  and " +
                    " datetime(end_date/1000,'unixepoch','localtime') >= datetime(strftime('%s', 'now','-37 hours'),'unixepoch','localtime')" +
                    " and tipo_actividad_id in (3) and end_date is not null " +
                    " and user_id=" + usuarioId;

            res = this.getDataQuery(query);
            res.moveToFirst();


            while(res.isAfterLast() == false){
                //System.out.println("::::::::::::::::::::::::indicador 24:" + res.getLong(res.getColumnIndex("milis")));

                //indicador.setTiempo(indicador.getTiempo() +  "[" + Math.abs(res.getFloat(res.getColumnIndex("total"))) + "]");

                total2 = ((86400000f) ) - res.getFloat(res.getColumnIndex("milis"));
                //System.out.println("::::::::::::::::::::::::indicador 24t:" + indicador.getTiempo());
                res.moveToNext();
            }
res.close();

total1 = total1 / (total2);
total1 = total1 * 100;
            //indicador.setTiempo(indicador.getTiempo() + total1);

            //if(total1>37.5)
            if(total1>25)
                indicador.setColor(R.color.verde);
            //else if(total1 <=37.5 && total1 >25)
            else if(total1 <=25 && total1 >20.83)
                indicador.setColor(R.color.amarillo);
            else
                indicador.setColor(R.color.rojo);

            /*
            indicador.setAmarillo(18000000);
            indicador.setRojo(7200000);
            */


            /*
            LOS PORCENTAJES SON LOS SIGUIENTES: ARRIBA DE 29.17% VERDE; MENOR A 29.17% Y MAYOR O IGUAL A 20.83% AMARILLO, MENOR A 20.83% ROJO
            */



            this.close();
        }catch(Exception ex)
        {
            ex.printStackTrace();
        }
        return  indicador;

    }

    public dream_indicador obtenerIndicador48(long usuarioId)
    {
        dream_indicador indicador = new dream_indicador();
        float total1 = 0, total2 = 0;
        try
        {
            //tenia 64
            String query = "select " +
                    " sum( " +
                    " julianday(datetime( end_date/1000,'unixepoch','localtime'))-case " +
                    " when datetime(start_date/1000,'unixepoch','localtime') <= datetime(strftime('%s', 'now','-66 hours'),'unixepoch','localtime') " +
                    " then julianday(datetime(strftime('%s', 'now','-66 hours hours'),'unixepoch','localtime')) " +
                    " else julianday(datetime(start_date/1000,'unixepoch','localtime')) " +
                    " end) *86400000 as milis " +
                    " from time_Table " +
                    " where " +
                    " 1=1  and " +
                    " datetime(end_date/1000,'unixepoch','localtime') >= datetime(strftime('%s', 'now','-66 hours'),'unixepoch','localtime')  and " +
                    " tipo_actividad_id=1 " +
                    " and user_id=" + usuarioId;

            Cursor res = this.getDataQuery(query);
            res.moveToFirst();

            long horas = 0 , minutos = 0, milis = 0;

            while(res.isAfterLast() == false){
                //System.out.println("::::::::::::::::::::::::indicador 7 days:" + res.getLong(res.getColumnIndex("milis")));
                indicador.setMilisegundos(res.getLong(res.getColumnIndex("milis")));

                //milis = res.getLong(res.getColumnIndex("milis"))/7;
                total1 = res.getFloat(res.getColumnIndex("milis"));



                //minutos = (int) (((total1) / (1000*60)) % 60)/7;
                //horas  = (int) (((total1) / (1000*60*60)) % 24)/7;

                //horas  = (int) (((total1) / 60) % 24)/7;

                milis = res.getLong(res.getColumnIndex("milis"))/2;

                minutos = (int) (((milis) / (1000*60)) % 60);
                horas  = (int) (((milis) / (1000*60*60)) % 24);

                /*
                horas  = (int) (((total1) / 60))/7;
                minutos = (int)((((((total1) / 60))/7)%1)*60) +((int) (total1) /7)%60  ;//+  (int) (((((total1) / 60))/7)%1)*60  ;

                if(minutos > 60)
                {
                    horas = horas + 1;
                    minutos = minutos %60;
                }
*/


                indicador.setAmarillo(201600000);
                indicador.setRojo(151200000);
                indicador.setTiempo("[" + String.format("%02d",horas) + ":" + String.format("%02d",minutos) + "]");
                //indicador.setTiempo("[" + String.format("%02d",res.getLong(res.getColumnIndex("horas"))) + ":" + String.format("%02d",res.getLong(res.getColumnIndex("minutos"))) + "]");
                //System.out.println("::::::::::::::::::::::::indicador 24t:" + indicador.getTiempo());
                res.moveToNext();
            }


            //tenia 48
            query = "select " +
                    " sum( " +
                    " julianday(datetime( end_date/1000,'unixepoch','localtime'))-case " +
                    " when datetime(start_date/1000,'unixepoch','localtime') <= datetime(strftime('%s', 'now','-50 hours'),'unixepoch','localtime') " +
                    " then julianday(datetime(strftime('%s', 'now','-50 hours'),'unixepoch','localtime')) " +
                    " else julianday(datetime(start_date/1000,'unixepoch','localtime')) " +
                    " end) *86400000 as milis " +
                    " from time_Table " +
                    " where " +
                    " 1=1  and " +
                    " datetime(end_date/1000,'unixepoch','localtime') >= datetime(strftime('%s', 'now','-50 hours'),'unixepoch','localtime')" +
                    " and tipo_actividad_id in (3) and end_date is not null " +
                    " and user_id=" + usuarioId;

            res = this.getDataQuery(query);
            res.moveToFirst();


            while(res.isAfterLast() == false){
                //System.out.println("::::::::::::::::::::::::::::::::::contador:" + contador++);
                //System.out.println("::::::::::::::::::::::::indicador 24:" + res.getLong(res.getColumnIndex("milis")));

                //indicador.setTiempo(indicador.getTiempo() +  "[" + Math.abs(res.getFloat(res.getColumnIndex("total"))) + "]");
                total2 = ((86400000*2f) ) - res.getFloat(res.getColumnIndex("milis"));

                //System.out.println("::::::::::::::::::::::::indicador 24t:" + indicador.getTiempo());
                res.moveToNext();
            }
            res.close();

            total1 = total1 / total2;
            total1 = total1 * 100;

            //indicador.setTiempo(indicador.getTiempo() + total1);

            if(total1>25)
                indicador.setColor(R.color.verde);
                //else if(total1 <=37.5 && total1 >25)
            else if(total1 <=25 && total1 >20.83)
                indicador.setColor(R.color.amarillo);
            else
                indicador.setColor(R.color.rojo);




            this.close();
        }catch(Exception ex)
        {
            ex.printStackTrace();
        }
        return  indicador;
    }

    public dream_indicador obtenerIndicadorSemana(long usuarioId)
    {
        dream_indicador indicador = new dream_indicador();
        float total1 = 0, total2 = 0;
        try
        {
            //tenia 180
            String query = "select " +
                    " sum( " +
                    " julianday(datetime( end_date/1000,'unixepoch','localtime'))-case " +
                    " when datetime(start_date/1000,'unixepoch','localtime') <= datetime(strftime('%s', 'now','-187 hours'),'unixepoch','localtime') " +
                    " then julianday(datetime(strftime('%s', 'now','-187 hours hours'),'unixepoch','localtime')) " +
                    " else julianday(datetime(start_date/1000,'unixepoch','localtime')) " +
                    " end) *86400000 as milis " +
                    " from time_Table " +
                    " where " +
                    " 1=1  and " +
                    " datetime(end_date/1000,'unixepoch','localtime') >= datetime(strftime('%s', 'now','-187 hours'),'unixepoch','localtime')  and " +
                    " tipo_actividad_id=1 " +
                    " and user_id=" + usuarioId;

            Cursor res = this.getDataQuery(query);
            res.moveToFirst();

            long horas = 0 , minutos = 0, milis = 0;

            while(res.isAfterLast() == false){
                //System.out.println("::::::::::::::::::::::::indicador 7 days:" + res.getLong(res.getColumnIndex("milis")));
                indicador.setMilisegundos(res.getLong(res.getColumnIndex("milis")));

                //milis = res.getLong(res.getColumnIndex("milis"))/7;
                total1 = res.getFloat(res.getColumnIndex("milis"));



                //minutos = (int) (((total1) / (1000*60)) % 60)/7;
                //horas  = (int) (((total1) / (1000*60*60)) % 24)/7;

                //horas  = (int) (((total1) / 60) % 24)/7;

                milis = res.getLong(res.getColumnIndex("milis"))/7;

                minutos = (int) (((milis) / (1000*60)) % 60);
                horas  = (int) (((milis) / (1000*60*60)) % 24);

                /*
                horas  = (int) (((total1) / 60))/7;
                minutos = (int)((((((total1) / 60))/7)%1)*60) +((int) (total1) /7)%60  ;//+  (int) (((((total1) / 60))/7)%1)*60  ;

                if(minutos > 60)
                {
                    horas = horas + 1;
                    minutos = minutos %60;
                }
*/


                indicador.setAmarillo(201600000);
                indicador.setRojo(151200000);
                indicador.setTiempo("[" + String.format("%02d",horas) + ":" + String.format("%02d",minutos) + "]");
                //indicador.setTiempo("[" + String.format("%02d",res.getLong(res.getColumnIndex("horas"))) + ":" + String.format("%02d",res.getLong(res.getColumnIndex("minutos"))) + "]");
                //System.out.println("::::::::::::::::::::::::indicador 24t:" + indicador.getTiempo());
                res.moveToNext();
            }

            query = "select " +
                    " sum( " +
                    " julianday(datetime( end_date/1000,'unixepoch','localtime'))-case " +
                    " when datetime(start_date/1000,'unixepoch','localtime') <= datetime(strftime('%s', 'now','-187 hours'),'unixepoch','localtime') " +
                    " then julianday(datetime(strftime('%s', 'now','-187 hours'),'unixepoch','localtime')) " +
                    " else julianday(datetime(start_date/1000,'unixepoch','localtime')) " +
                    " end) *86400000 as milis " +
                    " from time_Table " +
                    " where " +
                    " 1=1  and " +
                    " datetime(end_date/1000,'unixepoch','localtime') >= datetime(strftime('%s', 'now','-187 hours'),'unixepoch','localtime')" +
                    " and tipo_actividad_id in (3) and end_date is not null " +
                    " and user_id=" + usuarioId;

            res = this.getDataQuery(query);
            res.moveToFirst();


            while(res.isAfterLast() == false){
                //System.out.println("::::::::::::::::::::::::::::::::::contador:" + contador++);
                //System.out.println("::::::::::::::::::::::::indicador 24:" + res.getLong(res.getColumnIndex("milis")));

                //indicador.setTiempo(indicador.getTiempo() +  "[" + Math.abs(res.getFloat(res.getColumnIndex("total"))) + "]");
                total2 = ((86400000*7f) ) - res.getFloat(res.getColumnIndex("milis"));

                //System.out.println("::::::::::::::::::::::::indicador 24t:" + indicador.getTiempo());
                res.moveToNext();
            }
res.close();

            total1 = total1 / total2;
            total1 = total1 * 100;

            //indicador.setTiempo(indicador.getTiempo() + total1);

            if(total1>25)
                indicador.setColor(R.color.verde);
                //else if(total1 <=37.5 && total1 >25)
            else if(total1 <=25 && total1 >20.83)
                indicador.setColor(R.color.amarillo);
            else
                indicador.setColor(R.color.rojo);





            this.close();
        }catch(Exception ex)
        {
            ex.printStackTrace();
        }
        return  indicador;
    }

    public  String obtenerFechaUltimSueno(long usuarioId)
    {
        //SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        //sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        sdf.setTimeZone(TimeZone.getTimeZone("Indian/Chagos"));


        String resultado = "N/A";
        try
        {
            /*
           String query = "select strftime('%d/%m/%Y - %H:%M',datetime(max(end_date)/1000,'unixepoch','localtime')) AS Inicio ," +
                    "((end_date-start_date)/(1000*60)%60) as minutos,((end_date-start_date)/(1000*60*60)%24) as horas " +
                    " from Time_Table where end_date is not null and user_id= " + usuarioId;
             */


             String query = "select strftime('%d/%m/%Y - %H:%M',datetime(max(end_date)/1000,'unixepoch','utc')) AS Inicio ," +
                    "((end_date-start_date)/(1000*60)%60) as minutos,((end_date-start_date)/(1000*60*60)%24) as horas , max(datetime(end_date/1000,'unixepoch','localtime')) AS Inicio1" +
                    " from Time_Table where end_date is not null and user_id= " + usuarioId + " and tipo_actividad_id=1 order by start_date asc";

/*
            String query = "select strftime('%d/%m/%Y - %H:%M',datetime(max(end_date)/1000,'unixepoch','utc')) AS Inicio ," +
                    "((end_date-start_date)/(1000*60)%60) as minutos,((end_date-start_date)/(1000*60*60)%24) as horas , max(datetime(end_date/1000,'unixepoch','localtime')) AS Inicio1" +
                    " from Time_Table where end_date is not null and user_id= " + usuarioId + "and tipo_actividad_id=1 order by start_date asc";
*/
/*
String query = "select id,((end_date-start_date)/(1000*60)%60) as minutos,((end_date-start_date)/(1000*60*60)%24) as horas " +
                    " from Time_Table where user_id= " + usuarioId + " and tipo_actividad_id=1 order by start_date asc";
 */

            Cursor res = this.getDataQuery(query);
            res.moveToFirst();


            //String.format("%03d", num)
            while(res.isAfterLast() == false){
                //resultado = res.getString(res.getColumnIndex("Inicio")) + "[" + String.format("%02d",res.getString(res.getColumnIndex("horas"))) + ":" + String.format("%02d",res.getString(res.getColumnIndex("minutos"))) + "]";
                //resultado = res.getString(res.getColumnIndex("Inicio")) + "[" + String.format("%02d",res.getLong(res.getColumnIndex("horas"))) + ":" + String.format("%02d",res.getLong(res.getColumnIndex("minutos"))) + "]";
                resultado = res.getString(res.getColumnIndex("Inicio"));


                //System.out.println("2-----------" + sdf.parse(res.getString(res.getColumnIndex("Inicio1"))));

                res.moveToNext();
            }
            res.close();
            this.close();
        }catch(Exception ex)
        {
            System.out.println("*************Test");
            ex.printStackTrace();
        }
        return  resultado== null? "N/A" : resultado;
    }

    public  long obtenerIdUltimSueno(long usuarioId)
    {
        long resultado = -1;
        try
        {
            /*
            String query = "select date(max(end_date)/1000,'unixepoch','localtime') AS Inicio ," +
                    "((end_date-start_date)/(1000*60)%60) as minutos,((end_date-start_date)/(1000*60*60)%24) as horas " +
                    " from Time_Table where user_id= " + usuarioId;
             */
/*
            String query = "select max(sql_id) as Id" +
                    " from Time_Table where value='Sueño Mex App' and user_id= " + usuarioId + " order by end_date asc";
*/
            String query = "select max(sql_id) as Id" +
                    " from Time_Table where value='Sueño Mex App' and user_id= " + usuarioId + " order by start_date asc";


            Cursor res = this.getDataQuery(query);
            res.moveToFirst();

            //String.format("%03d", num)
            while(res.isAfterLast() == false){
                //resultado = res.getString(res.getColumnIndex("Inicio")) + "[" + String.format("%02d",res.getString(res.getColumnIndex("horas"))) + ":" + String.format("%02d",res.getString(res.getColumnIndex("minutos"))) + "]";
                //resultado = res.getString(res.getColumnIndex("Inicio")) + "[" + String.format("%02d",res.getLong(res.getColumnIndex("horas"))) + ":" + String.format("%02d",res.getLong(res.getColumnIndex("minutos"))) + "]";
                resultado = res.getLong(res.getColumnIndex("Id"));
                System.out.println("*******************************print" + res.getColumnIndex("Id"));
                res.moveToNext();
            }
            res.close();



            this.close();
        }catch(Exception ex)
        {
            ex.printStackTrace();
        }


        return  resultado;
    }

    public  String obtenerUltimSuenoDuracion(long usuarioId)
    {
        String resultado = "N/A";
        try
        {
            String query = "select id,((end_date-start_date)/(1000*60)%60) as minutos,((end_date-start_date)/(1000*60*60)%24) as horas " +
                    " from Time_Table where user_id= " + usuarioId + " and tipo_actividad_id=1 order by start_date asc";
            /*
            String query = "select ((end_date-start_date)/(1000*60)%60) as minutos,((end_date-start_date)/(1000*60*60)%24) as horas " +
                    " from Time_Table where user_id= " + usuarioId + " and value='Sueño Mex App' ";
*/
            Cursor res = this.getDataQuery(query);
            res.moveToFirst();

            //String.format("%03d", num)
            while(res.isAfterLast() == false){
                //resultado = res.getString(res.getColumnIndex("Inicio")) + "[" + String.format("%02d",res.getString(res.getColumnIndex("horas"))) + ":" + String.format("%02d",res.getString(res.getColumnIndex("minutos"))) + "]";
                resultado = String.format("%02d",res.getLong(res.getColumnIndex("horas"))) + ":" + String.format("%02d",res.getLong(res.getColumnIndex("minutos")));
                res.moveToNext();
            }
            res.close();


            this.close();
        }catch(Exception ex)
        {
            ex.printStackTrace();
        }
        return  resultado== null? "N/A" : resultado;
    }

    public  Dream obtenerSueno(long id)
    {
        Dream sueno = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        //sdf.setTimeZone(TimeZone.getTimeZone("America/Mexico_City"));
        try
        {
            /*
            String query = "select datetime(max(start_date)/1000,'unixepoch','localtime') AS Inicio," +
                    " datetime(max(end_date)/1000,'unixepoch','localtime') AS Fin," +
                    " value as comentarios,user_id,id,enviado,sql_id" +
                    " from Time_Table where sql_id= " + id;
*/
            String query = "select datetime(start_date/1000,'unixepoch') AS Inicio," +
                    " datetime(end_date/1000,'unixepoch') AS Fin," +
                    " value as comentarios,user_id,id,enviado,sql_id" +
                    " from Time_Table where sql_id= " + id;
            Cursor res = this.getDataQuery(query);
            res.moveToFirst();

            while(res.isAfterLast() == false){
                sueno = new Dream();
                sueno.setComentarios(res.getString(res.getColumnIndex("comentarios")));
                sueno.setUsuarioId(res.getInt(res.getColumnIndex("user_id")));
                sueno.setId(res.getInt(res.getColumnIndex("id")));
                sueno.setEnviado(res.getLong(res.getColumnIndex("enviado")));

                try {
                    System.out.println("::::::::::::::::obtenerSueno****:" + sdf.parse(res.getString(res.getColumnIndex("id"))));
                } catch (Exception ex){}

                    if(res.getString(res.getColumnIndex("Inicio"))!=null)
                        sueno.setFechaInicio(sdf.parse(res.getString(res.getColumnIndex("Inicio"))));
                    //System.out.println("::::::::::::::::obtenerSueno:" + sdf.parse(res.getString(res.getColumnIndex("Inicio"))));
                    if(res.getString(res.getColumnIndex("Fin"))!=null)
                        sueno.setFechaFin(sdf.parse(res.getString(res.getColumnIndex("Fin"))));
                    if(res.getString(res.getColumnIndex("sql_id"))!=null)
                        sueno.setSql_id(res.getLong(res.getColumnIndex("sql_id")));
                    //System.out.println("::::::::::::::::obtenerSueno Fin:" + sdf.parse(res.getString(res.getColumnIndex("Fin"))));

                res.moveToNext();
            }


            res.close();


            this.close();
        }catch(Exception ex)
        {
            ex.printStackTrace();
        }
        return  sueno;
    }

    public  Dream obtenerSuenoSQL(long id)
    {
        Dream sueno = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        //sdf.setTimeZone(TimeZone.getTimeZone("America/Mexico_City"));
        try
        {
            /*
            String query = "select datetime(max(start_date)/1000,'unixepoch','localtime') AS Inicio," +
                    " datetime(max(end_date)/1000,'unixepoch','localtime') AS Fin," +
                    " value as comentarios,user_id,id,enviado,sql_id" +
                    " from Time_Table where sql_id= " + id;
*/
            String query = "select datetime(start_date/1000,'unixepoch') AS Inicio," +
                    " datetime(end_date/1000,'unixepoch') AS Fin," +
                    " value as comentarios,user_id,id,enviado,sql_id" +
                    " from Time_Table where id= " + id;
            Cursor res = this.getDataQuery(query);
            res.moveToFirst();

            while(res.isAfterLast() == false){
                sueno = new Dream();
                sueno.setComentarios(res.getString(res.getColumnIndex("comentarios")));
                sueno.setUsuarioId(res.getInt(res.getColumnIndex("user_id")));
                sueno.setId(res.getInt(res.getColumnIndex("id")));
                sueno.setEnviado(res.getLong(res.getColumnIndex("enviado")));

                try {
                    System.out.println("::::::::::::::::obtenerSueno****:" + sdf.parse(res.getString(res.getColumnIndex("id"))));
                } catch (Exception ex){}

                if(res.getString(res.getColumnIndex("Inicio"))!=null)
                    sueno.setFechaInicio(sdf.parse(res.getString(res.getColumnIndex("Inicio"))));
                //System.out.println("::::::::::::::::obtenerSueno:" + sdf.parse(res.getString(res.getColumnIndex("Inicio"))));
                if(res.getString(res.getColumnIndex("Fin"))!=null)
                    sueno.setFechaFin(sdf.parse(res.getString(res.getColumnIndex("Fin"))));
                if(res.getString(res.getColumnIndex("sql_id"))!=null)
                    sueno.setSql_id(res.getLong(res.getColumnIndex("sql_id")));
                //System.out.println("::::::::::::::::obtenerSueno Fin:" + sdf.parse(res.getString(res.getColumnIndex("Fin"))));

                res.moveToNext();
            }


            res.close();


            this.close();
        }catch(Exception ex)
        {
            ex.printStackTrace();
        }
        return  sueno;
    }

    public void depurarRegistros(long usuarioId)
    {
        this.deleteQuery(this.TABLE_NAME,"user_id=?",new String[] {String.valueOf(usuarioId)});
        this.close();
    }
}
