package dream.logsys.com.logsysdream;


import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.se.omapi.Session;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.os.Handler;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.itextpdf.text.DocumentException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;

import dream.logsys.com.logsysdream.wsBitaciora.Wservice;
import logsys.dream.com.mx.activities.LoginActivity;
import logsys.dream.com.mx.activities.MainActivity;
import logsys.dream.com.mx.contracts.Usuario;
import logsys.dream.com.mx.helpers.Viajes;
import logsys.dream.com.mx.models.Bitacora;
import logsys.dream.com.mx.models.Dream;
import logsys.dream.com.mx.models.ServiceData;
import logsys.dream.com.mx.startup.FrescoApplication;
import utils.GsonHelper;


public class documentos extends Fragment {

    protected FrescoApplication globalVariable = (FrescoApplication) LoginActivity.getAppContext();


    // Metodo que queremos ejecutar en el servicio web
    private static final String Metodo = "getInfoGeneral";
    // Namespace definido en el servicio web
    private static final String namespace = "http://tempuri.org";
    // namespace + metodo
    private static final String accionSoap = "http://tempuri.org/getInfoGeneral";
    // Fichero de definicion del servcio web
    private static final String url = "http://tms.logsys.com.mx/mexapp/MexAppws.asmx";
    public  SoapPrimitive resultado;
    Context context = null;
    ProgressDialog pdialog = null;
    int pru;
    String alia, nombre,no,ap,am,imgemp,tag,tipe,seguro,segurophone,sepolice,grupo,telcontac,Srfc,Scurp,Snolic,Svic,dates,imgliv,nimss,imgsua,nocont,antuguedad;
    TextView userName;
    TextView curpUser;
    TextView userUnit;
    TextView userPlaca;
    TextView userLicencia;
    TextView userVigencia;
    TextView noss,sAs,poliza;


    public boolean consumirWS(){
        Boolean bandera=true;
        try {

            SoapObject request = new SoapObject(namespace, Metodo);
            request.addProperty("id_tms", pru);
            request.addProperty("aliasUnidad", alia);
            SoapSerializationEnvelope sobre = new SoapSerializationEnvelope(SoapEnvelope.VER11);

            sobre.dotNet = true;

            sobre.setOutputSoapObject(request);

            // Modelo el transporte
            HttpTransportSE transporte = new HttpTransportSE(url);

            // Llamada
            transporte.call(accionSoap, sobre);

            // Resultado
            resultado= (SoapPrimitive) sobre.getResponse();


        } catch (Exception e) {
            Log.e("ERROR", e.getMessage());
            bandera=false;
        }finally {

            return bandera;

        }

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_documentos, container, false);
        context = view.getContext();
         mandar();
        noss=view.findViewById(R.id.NSS);
        userName = view.findViewById(R.id.nameUser);
        curpUser = view.findViewById(R.id.userCurp);
        userUnit = view.findViewById(R.id.unitUser);
        userLicencia = view.findViewById(R.id.licenciaUser);
        userPlaca = view.findViewById(R.id.placaUser);
        userVigencia = view.findViewById(R.id.vigenciaUser);
        sAs=view.findViewById(R.id.sua);
        poliza=view.findViewById(R.id.POLIZASEGURO);
        userName.setText(nombre);
        userUnit.setText(alia);

       getdates();
        userLicencia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });
        noss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });
        sAs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        poliza.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });


        return view;
    }
    public  void getdates(){
        pdialog = ProgressDialog.show(context, "", "Espere un momento", true);

        RetreiveFeedTask task = new RetreiveFeedTask();
        task.execute();

        nombre=no+" "+ap+""+am;
    }
    public  void mandar(){
        pru = globalVariable.getUsuario().getId();
        alia= globalVariable.getUsuario().getUnidad();

    }

    class RetreiveFeedTask extends AsyncTask<String,String,String> {
        @Override
        protected void onPreExecute(){
            super.onPreExecute();
        }
        @Override
        protected String doInBackground(String... params) {
            if(consumirWS()){
                return "ok";
            }else
                return "error";

        }
        private void getDriver(JSONObject dri){
            try {
                no =dri.getString("nombre");
                ap =dri.getString("apPat");
                am= dri.getString("apMat");


            } catch (JSONException e) {
                e.printStackTrace();
                Log.e("Driver_error",e.getMessage());
            }

        }
        private void getveiculo(JSONObject dri){
            try {
               tag =dri.getString("tag");



            } catch (JSONException e) {
                e.printStackTrace();
                Log.e("Driver_error",e.getMessage());
            }
        }

        @Override
        protected void onPostExecute(String result) {
            if(result.equals("ok")){
            try {
                JSONObject o=new JSONObject(resultado.toString());
                JSONObject driver=o.getJSONObject("vehicle");
                getveiculo(driver);
                pdialog.dismiss();

            }catch(Exception e){

            Log.e("JSONArrayError",e.getMessage());
        }
            }else{
                Log.e("ERROR", "Error al consumir el webService");
            }

            pdialog.dismiss();

        }

    }


}
