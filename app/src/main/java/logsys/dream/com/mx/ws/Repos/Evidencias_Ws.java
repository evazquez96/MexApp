package logsys.dream.com.mx.ws.Repos;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.Collection;

import logsys.dream.com.mx.contracts.Dream;
import logsys.dream.com.mx.contracts.EvidenciasContract;
import logsys.dream.com.mx.ws.Ws_Utils;
import utils.GsonHelper;

/**
 * Created by ADGARCIA on 14/05/2018.
 */

public class Evidencias_Ws extends Ws_Utils {
    public Collection<EvidenciasContract> obTenerSolicitudes() {
        //JSONObject o = new JSONObject();
        String result = null;
        Gson gson = null;
        //EvidenciasContract ec = new EvidenciasContract();


        Log.d("TESTING", "Entrando a obtener solicitudessssssssssssssssss.SSSSSsssssssS");
        /*try {
            o.put("usuario_id", globalVariable.getUsuario().getId());
        } catch (JSONException e) {
            e.printStackTrace();
        }*/
        result = Get("Erp/Evidencias.svc/obtenerSolicitudes");






        gson = new GsonHelper().getGson();
        Type collectionType = new TypeToken<Collection<EvidenciasContract>>(){}.getType();
        Collection<EvidenciasContract> enums = gson.fromJson(result.toString(), collectionType);
        //ec = gson.fromJson(result.toString(), EvidenciasContract.class);

        Log.d("TESTING", "EVIDENCIAS WS LOG................." + enums.iterator().next().getNoSolicitud());

        return enums;
    }

}
