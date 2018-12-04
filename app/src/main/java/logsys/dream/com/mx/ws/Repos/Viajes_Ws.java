package logsys.dream.com.mx.ws.Repos;

import com.google.gson.Gson;

import org.json.JSONObject;

import logsys.dream.com.mx.contracts.viaje.to.viajeTO;
import logsys.dream.com.mx.ws.Ws_Utils;
import utils.GsonHelper;

public class Viajes_Ws extends Ws_Utils {

    public viajeTO[] obtenerViajesUsuario() {
        JSONObject o = new JSONObject();
        String result = null;
        Gson gson = null;
        result = Get("Notificaciones/Notificaciones.svc/obtenerViajesUnidad?imei=" + globalVariable.getUsuario().getImei());
        gson = new GsonHelper().getGson();
        return gson.fromJson(result.toString(), viajeTO[].class);

    }
}