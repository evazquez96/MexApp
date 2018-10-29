package logsys.dream.com.mx.ws.Repos;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.gson.Gson;

import logsys.dream.com.mx.contracts.Usuario;
import logsys.dream.com.mx.ws.Ws_Utils;

/**
 * Created by JUAHERNA on 2/14/2017.
 */

public class LoginRepo_Ws extends Ws_Utils {

    public static boolean s_iniciado = false;

    public int islogued(long usuario_id,long sql_id,String numero_telefono,String version)
    {
        try {
            String result = Get("Sueno/Sueno.svc/isloggued?sqlLite_Id=" + sql_id + "&usuario_id=" + usuario_id + "&numero_telefono="+ numero_telefono + "&version=" + version);
            return Integer.parseInt(result.substring(0,1));
        }catch (Exception ex)
        {

            ex.printStackTrace();
            return 1;
        }
    }

    public int puedeIniciarSueno(long usuario_id,String imei)
    {
        try {
            String result = Get("Sueno/Sueno.svc/puedeIniciarSueno?usuario_id=" + usuario_id + "&imei=" + imei);

            return Integer.parseInt(result.substring(0,1));
        }catch (Exception ex)
        {

            ex.printStackTrace();
            return -1;
        }
    }

    public Usuario logueoAutomatico(String numeroTelefono)
    {
        try {
            String result = "";
            Usuario usuario = null;
            String refreshedToken = FirebaseInstanceId.getInstance().getToken();

            Log.d("logueoAutomatico", "Refreshed token Firebase: " + refreshedToken);

            if(!(numeroTelefono != null && !numeroTelefono.isEmpty()))
            {
                Log.d("logueoAutomatico","::::::::::#Teefónico vacio o null");
                return null;
            }
            result = Get("Catalogos/Usuario.svc/logueoAutomatico?imei=" + numeroTelefono + "&token="+ refreshedToken);
            usuario = new Gson().fromJson(result, Usuario.class);

            return usuario;

        }catch (Exception ex)
        {
            Log.d("Exception",ex.getMessage());
            ex.printStackTrace();
            return  null;
        }
    }



}
