package logsys.dream.com.mx.repos;

import com.google.gson.Gson;

import org.json.JSONObject;

import dream.logsys.com.logsysdream.SecondPlain;
import logsys.dream.com.mx.activities.LoginActivity;
import logsys.dream.com.mx.contracts.Usuario;
import logsys.dream.com.mx.db.ServiceUsuarios;
import logsys.dream.com.mx.db.UsuariosEntity;
import logsys.dream.com.mx.models.ServiceData;
import logsys.dream.com.mx.ws.Ws_Utils;
import utils.GsonHelper;

public class RepUsers extends SecondPlain {

    ServiceUsuarios bdU;

    public RepUsers()
    {
        super();
        bdU = new ServiceUsuarios(LoginActivity.getAppContext());
    }

    public void sincronizarUsuarioss()
    {
        try {
            SecondPlain ws = new SecondPlain();
            JSONObject o = new JSONObject();

            String result = ws.resultado.toString();
            Gson gson = new GsonHelper().getGson();

            ServiceData[] data = gson.fromJson(result.toString(), ServiceData[].class);

            bdU.depurarRegistros();
            long errores = 0;
            for (ServiceData d : data) {
                if (bdU.insertarUsuarios(d) == -1)
                    errores++;
            }
        }catch (Exception ex)
        {

        }
    }

}
