package logsys.dream.com.mx.helpers;

import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import dream.logsys.com.logsysdream.R;
import logsys.dream.com.mx.activities.LoginActivity;
import logsys.dream.com.mx.contracts.viaje.to.viajeTO;
import logsys.dream.com.mx.db.ViajesDB;
import logsys.dream.com.mx.fragments.ViajeListFragment;
import logsys.dream.com.mx.repos.RepViajes;
import logsys.dream.com.mx.startup.FrescoApplication;
import logsys.dream.com.mx.ws.Repos.Viajes_Ws;

public class Viajes{

    int no;
    String origen;
    String destino;
    ViajesDB db = new ViajesDB(LoginActivity.getAppContext());
    viajeTO viaje = db.obtenerActual();
    Viajes_Ws ws = new Viajes_Ws();
    viajeTO[] viajes = ws.obtenerViajesUsuario();

    public void prueba() {
        viajeTO todos;
        for(int i=0;i<60;i++) {

            todos = db.obtenerViaje(i);

        }

    }

    public int getSolicitud(){

        int solicitud;
        no = viaje.getNumeroSolicitud();
        origen = viaje.getOrigen();
        String hola = viaje.getCitaCarga();
        destino = viaje.getDestino();
        List<viajeTO> list = new ArrayList<viajeTO>();
        /*
        for (int i=0;i<viajes.length;i++) {
            System.out.println(viajes[i]);

        }
        */
        for (viajeTO d : viajes) {
            list.add(d);
        }

        if(origen.equals("")){

            solicitud = list.get(0).getNumeroSolicitud();

        } else {

            solicitud = no;
        }

        return solicitud;

    }

    public String getOrigenn() {

        String origin = null;
        origen = viaje.getOrigen();
        destino = viaje.getDestino();

        List<viajeTO> list = new ArrayList<viajeTO>();

        for (viajeTO d : viajes) {
            list.add(d);
        }

        if (getSolicitud() == list.get(0).getNumeroSolicitud()){
            origin = origen;
        } else {

            origin = destino;
        }

        return origin;

    }

    public String getDestinoo() {

        String destinity = null;
        origen = viaje.getOrigen();
        destino = viaje.getDestino();
        List<viajeTO> list = new ArrayList<viajeTO>();

        for (viajeTO d : viajes) {
            list.add(d);
        }

        if (getSolicitud() == list.get(0).getNumeroSolicitud()){

            destinity = destino;

        } else {

            destinity = "TERMINAL";

        }

        return destinity;

    }

}
