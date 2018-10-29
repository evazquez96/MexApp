package logsys.dream.com.mx.ws.Repos;


import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import logsys.dream.com.mx.contracts.Dream;
import logsys.dream.com.mx.ws.Ws_Utils;
import utils.GsonHelper;

/**
 * Created by JUAHERNA on 2/21/2017.
 */

public class Dashboard_Ws extends Ws_Utils {


    public String enviarSueno(Dream sueno)
    {
        JSONObject o = new JSONObject();
        try {
            o.put("comentarios",sueno.getComentarios());
            o.put("usuarioId",sueno.getUsuarioId());
            o.put("tipoActividadId",1);
            o.put("sql_id",sueno.getSql_id());

            if(sueno.getFechaInicio()!=null)
                o.put("fechaInicio","/Date("+ sueno.getFechaInicio().getTime() + ")/");

            if(sueno.getId()!=0)
                o.put("id",sueno.getId());
            try {
                if(sueno.getFechaFin()!=null)
                    o.put("fechaFin", "/Date(" + sueno.getFechaFin().getTime() + ")/");
            }catch (Exception ex)
            {

            }
        } catch (JSONException e1) {
            e1.printStackTrace();
        }
        return Post("Sueno/Sueno.svc/guardar",o);
    }

    public Dream[] obTenerSuenosUsuario() {
        JSONObject o = new JSONObject();
        String result = null;
        Gson gson = null;
        try {
            o.put("usuario_id", globalVariable.getUsuario().getId());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        result = Post("Sueno/Sueno.svc/consultar", o);

        gson = new GsonHelper().getGson();

        return gson.fromJson(result.toString(), Dream[].class);
    }

}
