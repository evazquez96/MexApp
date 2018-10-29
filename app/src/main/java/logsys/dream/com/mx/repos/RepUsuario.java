package logsys.dream.com.mx.repos;

import com.google.gson.Gson;

import org.json.JSONObject;

import logsys.dream.com.mx.activities.LoginActivity;
import logsys.dream.com.mx.contracts.Usuario;
import logsys.dream.com.mx.db.UsuariosEntity;
import logsys.dream.com.mx.ws.Ws_Utils;
import utils.GsonHelper;

/**
 * Created by paqti on 11/03/2017.
 */

public class RepUsuario extends RepBase {

    UsuariosEntity bdU;

    public RepUsuario()
    {
        super();
        bdU = new UsuariosEntity(LoginActivity.getAppContext());
    }

    public void sincronizarUsuarios()
    {
        try {
            Ws_Utils ws = new Ws_Utils();
            JSONObject o = new JSONObject();

            String result = ws.Post("Catalogos/Usuario.svc/consultar", o);
            Gson gson = new GsonHelper().getGson();

            Usuario[] data = gson.fromJson(result.toString(), Usuario[].class);

            bdU.depurarRegistros();
            long errores = 0;
            for (Usuario d : data) {
                if (bdU.insertarUsuario(d) == -1)
                    errores++;
            }
        }catch (Exception ex)
        {

        }
    }

}