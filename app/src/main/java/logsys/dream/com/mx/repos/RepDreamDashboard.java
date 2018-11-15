package logsys.dream.com.mx.repos;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

import logsys.dream.com.mx.activities.LoginActivity;
import logsys.dream.com.mx.contracts.Dream;
import logsys.dream.com.mx.contracts.Dream_Dashboard;
import logsys.dream.com.mx.db.DreamDB;
import logsys.dream.com.mx.ws.Repos.Dashboard_Ws;

/**
 * Created by paqti on 09/03/2017.
 */

public class RepDreamDashboard extends RepBase {

    DreamDB bdTe;

    public RepDreamDashboard()
    {
        super();
        bdTe = new DreamDB(LoginActivity.getAppContext());
    }

    public Dream_Dashboard obtenerDashBoard()
    {
        Dream_Dashboard dash = null;
        this.inicializarRegistros();

        dash = bdTe.obtenerDashboard(globalVariable.getUsuario().getId());
        dash.setNombre(globalVariable.getUsuario().getNombre());

        //Paco: Se debe encapsular para que el método obtener Dashboard llene todo
        //dash.setP_seno_sem((long) 2.5);
        dash.setP_sueno_24(dash.getI_ultimas_24().getMilisegundos());
        dash.setP_sueno_48(dash.getI_ultimas_48().getMilisegundos());
        dash.setP_seno_sem(dash.getI_semanal().getMilisegundos());

        dash.setUltimas_horas(bdTe.obtenerUltimSuenoDuracion(globalVariable.getUsuario().getId()));
        dash.setUltimo_sueno(bdTe.obtenerFechaUltimSueno(globalVariable.getUsuario().getId()));

        return  dash;
    }


    public void inicializarRegistros()
    {
        try {
            Dashboard_Ws ws = new Dashboard_Ws();
            Dream[] data = ws.obTenerSuenosUsuario();
            //Paco hardcodeado
            //bdTe.depurarRegistros(usuario.getId());
            bdTe.depurarRegistros(globalVariable.getUsuario().getId());

            Calendar cal = Calendar.getInstance(); // creates calendar
            java.util.Date dd;
            for (Dream d : data) {

                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
                formatter.setTimeZone(TimeZone.getTimeZone("Indian/Chagos"));

                Calendar cal1 = Calendar.getInstance();
                cal1.setTimeZone(TimeZone.getTimeZone("Universal"));

                cal1.setTime(d.getFechaInicio());

                String timestamp = formatter.format(cal1.getTime());
                //System.out.println("----Formateada:::::" + timestamp);
                //System.out.println("Formateada1: " + cal1.getTime());
                cal.setTime(d.getFechaInicio()); // sets calendar time/date

                dd = new java.util.Date(cal.getTime().getTime());

                d.setFechaInicio(dd);

                if(d.getFechaFin()!=null) {
                    cal.setTime(d.getFechaFin()); // sets calendar time/date

                    //revisar xq esta agregando una hora......... no borrar
                    //cal.add(Calendar.HOUR_OF_DAY, 10); // adds one hour
                    dd = new java.util.Date(cal.getTime().getTime());
                    d.setFechaFin(dd);
                }
                d.setEnviado(1);
                bdTe.insertarSueno(d);
            }
            bdTe.close();
        }catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

}
