package dream.logsys.com.logsysdream.wsBitaciora;

import android.app.IntentService;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;

import logsys.dream.com.mx.activities.LoginActivity;
import logsys.dream.com.mx.startup.FrescoApplication;

public class SPcartaporte extends IntentService {
    public SPcartaporte() {
        super("HelloIntentService");
    }
    protected void onHandleIntent(Intent intent) {

        try {
            Thread.sleep(60000);
            asyncdocuments doc = new asyncdocuments();
            doc.execute();
        } catch (InterruptedException e) {

            Thread.currentThread().interrupt();
        }
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(this, "service starting", Toast.LENGTH_SHORT).show();
        asyncdocuments doc = new asyncdocuments();
        doc.execute();
        return super.onStartCommand(intent,flags,startId);
    }


    protected FrescoApplication globalVariable = (FrescoApplication) LoginActivity.getAppContext();

    // Metodo que queremos ejecutar en el servicio web
    private static final String Metodo = "GetViajesActual";
    // Namespace definido en el servicio web
    private static final String namespace = "http://tempuri.org/";
    // namespace + metodo
    private static final String accionSoap = "http://tempuri.org/GetViajesActual";
    // Fichero de definicion del servcio web
    private static final String url = "https://app.mexamerik.com/Mexapp_viajes/Viajes.asmx?";

    public SoapPrimitive resultado;
    int pru;
    String fecha,ssolic;
    private File pdf;
    public boolean consumirWS(){
     pru=globalVariable.getUsuario().getId();

        Boolean bandera=true;
        try {
            String s=new SimpleDateFormat("yyyy/MM/dd").format(new java.util.Date());
            fecha=s;
            SoapObject request = new SoapObject(namespace, Metodo);
            request.addProperty("fecha", fecha);
            request.addProperty("ID_Usuario", pru);
            SoapSerializationEnvelope sobre = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            sobre.dotNet = true;
            sobre.setOutputSoapObject(request);
            HttpTransportSE transporte = new HttpTransportSE(url);
            transporte.call(accionSoap, sobre);
            resultado= (SoapPrimitive) sobre.getResponse();

        } catch (Exception e) {
            Log.e("ERROR", e.getMessage());
            bandera=false;
        }finally {

            return bandera;
        }}
    private class asyncdocuments extends AsyncTask<String,String,String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {

            if (consumirWS()) {
                return "ok";
            } else
                return "error";
        }


        private void getCar(JSONObject car){


            try {
                ssolic=car.getString("Id");


            } catch (JSONException e) {
                e.printStackTrace();
                Log.e("Car_error",e.getMessage());
            }

        }
        protected void onPostExecute(String result){
            if(result.equals("ok")){
                String res,res2;
                try {
                    int sum=2+2;
                    res=resultado.toString().replace("[","");
                    res2=res.replace("]","");
                    JSONObject o=new JSONObject(res2);
                    getCar(o);
                    int sum12=2+2;
                    asyncCp cp =new asyncCp();
                    cp.execute();
                }
                catch(Exception e){

                    Log.e("JSONArrayError",e.getMessage());
                }
            }
            else{
                Log.e("ERROR", "Error al consumir el webService");
            }
        }




    }
    public boolean consumirWScp() {
        Boolean bandera=true;
        HttpClient httpClient = new DefaultHttpClient();

        String id = ssolic;

        HttpGet del = new HttpGet("http://tms.logsys.com.mx/tms/api/v2.0/cartaporte/" + id+"/app");
        del.setHeader("Authorization", "Basic YWRtaW46bTN4NG0zcjFr");
        del.setHeader("Accept", "*/*");

        try {
            String ruta,ruta2;
            HttpResponse resp = httpClient.execute(del);
            byte[]  respStr = EntityUtils.toByteArray(resp.getEntity());
            File folder= new File(Environment.getExternalStorageDirectory().toString(),"Carta porte");
            folder.mkdirs();

            pdf=new File(folder,"Respaldo.pdf");

            ruta2=pdf.getPath();

            respaldo(respStr,ruta2);


        } catch (Exception ex) {
            Log.e("ServicioRest", "Error!", ex);
        }
        return bandera;
    }

    private class asyncCp extends AsyncTask<String,String,String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {

            if (consumirWScp()) {
                return "ok";
            } else
                return "error";
        }

        protected void onPostExecute(String result){

            if(result.equals("ok")){


            }
            else{

                Log.e("ERROR", "Error al consumir el webService");
            }

        }


    }

    public static boolean respaldo(byte[] fileBytes, String archivoDestino){
        boolean correcto = false;
        try {
            OutputStream out = new FileOutputStream(archivoDestino);
            out.write(fileBytes);
            out.close();
            correcto = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return correcto;

    }

}
