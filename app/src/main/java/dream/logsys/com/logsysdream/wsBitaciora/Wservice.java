package dream.logsys.com.logsysdream.wsBitaciora;

import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.text.SimpleDateFormat;
import java.util.LinkedList;

import dream.logsys.com.logsysdream.R;
import dream.logsys.com.logsysdream.bitacora2;
import logsys.dream.com.mx.activities.LoginActivity;
import logsys.dream.com.mx.contracts.Usuario;
import logsys.dream.com.mx.helpers.Viajes;
import logsys.dream.com.mx.models.ServiceData;
import logsys.dream.com.mx.startup.FrescoApplication;
import utils.GsonHelper;

public class Wservice {

    protected FrescoApplication globalVariable = (FrescoApplication) LoginActivity.getAppContext();


    // Metodo que queremos ejecutar en el servicio web
    private static final String Metodo = "getRegistroBitacora";
    // Namespace definido en el servicio web
    private static final String namespace = "http://app.mexamerik.com";
    // namespace + metodo
    private static final String accionSoap = "http://app.mexamerik.com/getRegistroBitacora";
    // Fichero de definicion del servcio web
    private static final String url = "http://tms.logsys.com.mx/bitacoradream/Service.asmx";

    public SoapPrimitive resultado;
    private String fecha;
    int pru;
    String alia;
    LinkedList<ServiceData> dato = new LinkedList<ServiceData>();
    ServiceData serv = new ServiceData();

    public void getoperador(){
        pru = globalVariable.getUsuario().getId();
    }

    public void getunidad(){
        alia = globalVariable.getUsuario().getUnidad();
    }

    public String consumirWS() {
        String resul = null;
        try {

            // Modelo el request
            getoperador();
            getunidad();
            Viajes v = new Viajes();
            SoapObject request = new SoapObject(namespace, Metodo);
            request.addProperty("user_id", pru);
            //request.addProperty("user_id", 100107);
            String s = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new java.util.Date());
            //String fechaPrueba="2018/07/31 23:56:56";
            //Log.e("fecha",fechaPrueba);
            request.addProperty("alias_unidad", alia);

            request.addProperty("solicitud", v.getSolicitud());

            request.addProperty("date", s);
            //request.addProperty("date", fechaPrueba);
            //this.fecha=fechaPrueba; //A la variable de clase fecha se le asigna la fecha en la que se consumio el servicio.
            this.fecha = s;
            //request.addProperty("user_id", 2); // Paso parametros al WS

            // Modelo el Sobre
            SoapSerializationEnvelope sobre = new SoapSerializationEnvelope(SoapEnvelope.VER11);

            sobre.dotNet = true;

            sobre.setOutputSoapObject(request);

            // Modelo el transporte
            HttpTransportSE transporte = new HttpTransportSE(url);

            // Llamada
            transporte.call(accionSoap, sobre);

            // Resultado
            resultado = (SoapPrimitive) sobre.getResponse();

            resul = resultado.toString();

            return resul;
        } catch (Exception e) {
            Log.e("ERROR", e.getMessage());
            resul = e.toString();
        } finally {
            try {
                Thread.sleep(3000);
            } catch (Exception e) {

            }
            return resul;
            /*
             * El finally siempre se va a ejecutar, sin importar que se lanze
             * una exepction
             */
        }

    }
}

