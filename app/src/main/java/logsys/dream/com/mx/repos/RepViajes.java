package logsys.dream.com.mx.repos;

import logsys.dream.com.mx.activities.LoginActivity;
import logsys.dream.com.mx.contracts.viaje.to.viajeTO;
import logsys.dream.com.mx.db.ViajesDB;
import logsys.dream.com.mx.ws.Repos.Viajes_Ws;

public class RepViajes extends RepBase{

    ViajesDB bdVe;

    public RepViajes()
    {
        super();
        bdVe = new ViajesDB(LoginActivity.getAppContext());
    }

    public void inicializarRegistros()
    {
        try {
            Viajes_Ws ws = new Viajes_Ws();
            viajeTO[] data = ws.obtenerViajesUsuario();

            bdVe.depurarRegistros();


            for (viajeTO d : data) {
                bdVe.guardarViaje(d);
                }
            bdVe.close();
            }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

}
